package vova.example.spring.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import vova.example.domain.entity.UserWebPath
import vova.example.domain.exception.UserAlreadyExistsException
import vova.example.spring.model.UserWeb
import vova.example.spring.model.UserWeb.Companion.toUserWeb
import vova.example.usecase.CreateUser
import vova.example.usecase.FindUser
import org.springframework.scheduling.annotation.Async
import java.util.concurrent.CompletableFuture
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.future.asCompletableFuture

const val USER_ID = "userId"

//TODO use https://github.com/konrad-kaminski/spring-kotlin-coroutine
// instead of converting to CompletableFuture

@RestController
@RequestMapping(UserWebPath.USERS)
open class UserController @Autowired
constructor(private val createUser: CreateUser, private val findUser: FindUser) {

    @Async
    @PostMapping
    open fun createUser(@RequestBody userWeb: UserWeb): CompletableFuture<UserWeb> {
        return GlobalScope.async {
            UserWeb.toUserWeb(createUser.create(userWeb.toUser()))
        }.asCompletableFuture()
    }

    @Async
    @GetMapping("/{$USER_ID}")
    open fun getUser(@PathVariable(USER_ID) userId: String): CompletableFuture<UserWeb> {
        return GlobalScope.async {
            UserWeb.toUserWeb(findUser.findById(userId).orElseThrow { UserNotFoundException("User with user id:$userId not found") })
        }.asCompletableFuture()
    }

    @Async
    @GetMapping
    open fun allUsers(): CompletableFuture<List<UserWeb>> {
        return GlobalScope.async {
            findUser.findAllUsers().map { toUserWeb(it) }
        }.asCompletableFuture()
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistsException::class)
    fun handleException(error: UserAlreadyExistsException): Map<String, String> {
        return mapOf("error" to "User already exists:" + error.message)
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException::class)
    fun handleException(error: UserNotFoundException): Map<String, String> {
        return mapOf("error" to error.errorMessage)
    }

}

class UserNotFoundException(val errorMessage: String) : RuntimeException(errorMessage)


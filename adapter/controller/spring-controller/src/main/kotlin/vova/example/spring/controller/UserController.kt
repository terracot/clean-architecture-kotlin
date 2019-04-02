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

const val USER_ID = "userId"

@RestController
@RequestMapping(UserWebPath.USERS)
class UserController @Autowired
constructor(private val createUser: CreateUser, private val findUser: FindUser) {

    @PostMapping
    fun createUser(@RequestBody userWeb: UserWeb): UserWeb {
        return userWeb//UserWeb.toUserWeb(createUser.create(userWeb.toUser()))
    }

    @GetMapping("/{$USER_ID}")
    fun getUser(@PathVariable(USER_ID) userId: String): UserWeb {
        return UserWeb(email="",firstName = "",lastName = "")
        //return UserWeb.toUserWeb(findUser.findById(userId).orElseThrow { UserNotFoundException("User with user id:$userId not found") })
    }

    @GetMapping
    fun allUsers(): List<UserWeb> {
        return listOf()
        //return findUser.findAllUsers().map { toUserWeb(it) }
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


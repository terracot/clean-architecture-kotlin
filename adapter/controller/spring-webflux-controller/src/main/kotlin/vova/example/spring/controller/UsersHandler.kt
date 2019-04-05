package vova.example.spring.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.*
import vova.example.domain.entity.UserWebPath
import vova.example.domain.exception.UserAlreadyExistsException
import vova.example.spring.model.UserWeb
import vova.example.usecase.CreateUser
import vova.example.usecase.FindUser
import vova.example.usecase.LoginUser


const val USER_ID = "userId"


@Configuration
open class UsersRouter {

    @Bean
    open fun route(usersHandler: UsersHandler): RouterFunction<ServerResponse> = coRouter {
        accept(APPLICATION_JSON).nest {
            "/users".nest {
                GET("", usersHandler::getAllUsers)
                GET("/{id}", usersHandler::getUser)
                POST("", usersHandler::createUser)
            }
            "/login".nest {
                GET("", usersHandler::loginUser)
            }
        }
    }
}

@Component
class UsersHandler @Autowired constructor(private val createUser: CreateUser, private val findUser: FindUser, private val loginUser:LoginUser) {

    suspend fun getAllUsers(request: ServerRequest): ServerResponse =
        ServerResponse.ok().bodyAndAwait(findUser.findAllUsers().map { user -> UserWeb.toUserWeb(user) })

    suspend fun getUser(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("id")
        val user = findUser.findById(id)
        return if(user.isPresent) {
            ok().bodyAndAwait(UserWeb.toUserWeb(user.get()))
        } else {
            status(HttpStatus.NOT_FOUND).bodyAndAwait(mapOf("error" to "User $id not found"))
        }
    }

    suspend fun createUser(request: ServerRequest): ServerResponse =
        try {
            status(HttpStatus.CREATED).bodyAndAwait(UserWeb.toUserWeb(createUser.create(request.awaitBody<UserWeb>().toUser())))
        } catch (e: UserAlreadyExistsException) {
            status(HttpStatus.CONFLICT).bodyAndAwait(mapOf("error" to "User already exists:${e.message}"))
        }

    suspend fun loginUser(request: ServerRequest): ServerResponse {
        val email = request.queryParam(UserWebPath.LOGIN_EMAIL)
        val password = request.queryParam(UserWebPath.LOGIN_PASSWORD)
        return if (email.isEmpty || password.isEmpty) {
            badRequest().bodyAndAwait("email or password is missing")
        } else { val user = loginUser.login(email.get(), password.get())
            ok().bodyAndAwait(UserWeb.toUserWeb(user))
        }
    }

}

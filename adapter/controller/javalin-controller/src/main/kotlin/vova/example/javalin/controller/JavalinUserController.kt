package vova.example.javalin.controller

import io.javalin.Context
import io.javalin.NotFoundResponse
import io.javalin.apibuilder.ApiBuilder
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.future.asCompletableFuture
import vova.example.domain.entity.User
import vova.example.domain.entity.UserWebPath
import vova.example.domain.exception.UserAlreadyExistsException
import vova.example.javalin.model.UserWeb
import vova.example.usecase.CreateUser
import vova.example.usecase.FindUser
import vova.example.usecase.LoginUser
import java.util.concurrent.CompletableFuture

class JavalinUserController(
    private val createUser: CreateUser,
    private val findUser: FindUser,
    private val loginUser: LoginUser
) {
    private val userIdParam = "user-id"

    fun routes() {
        ApiBuilder.post(UserWebPath.USERS, this::createUser)
        ApiBuilder.get(UserWebPath.USERS, this::getAllUsers)
        ApiBuilder.get("${UserWebPath.USERS}/:$userIdParam", this::getUser)
        ApiBuilder.get(UserWebPath.LOGIN, this::userLogin)
    }

    fun getAllUsers(ctx: Context) {
        ctx.json(findAllFuture())
    }

    private fun findAllFuture(): CompletableFuture<List<UserWeb>> {
        return GlobalScope.async { findUser.findAllUsers() }.asCompletableFuture()
            .thenApply { users -> users.map { user -> UserWeb.toUserWeb(user) } }
    }


    fun createUser(ctx: Context) {
        val userWeb = ctx.body<UserWeb>()
        val handler = CoroutineExceptionHandler { _, exception ->
            println("Caught $exception")
        }
        val asyncResult = GlobalScope.async(handler) { createUser.create(userWeb.toUser()) }
            .asCompletableFuture()
            .handleAsync { u, e -> mapConflictError(ctx, u, e) }
            .thenApplyAsync<Any> { user -> if (user != null) UserWeb.toUserWeb(user) else mapOf("error" to "User already exists:${userWeb.email}") }
        ctx.json(asyncResult)
    }

    fun getUser(ctx: Context) {
        val userId = ctx.pathParam(userIdParam)
        val asyncResult: CompletableFuture<Any> = GlobalScope.async { findUser.findById(userId) }
            .asCompletableFuture()
            .thenApplyAsync { userOpt ->
                if (userOpt.isPresent) {
                    UserWeb.toUserWeb(userOpt.get())
                } else {
                    sendError(ctx,404)
                    mapOf("error" to "User $userId not found")
                }
            }
        ctx.json(asyncResult)
    }

    fun userLogin(ctx: Context) {
        val email = ctx.queryParam(UserWebPath.LOGIN_EMAIL)
        val password = ctx.queryParam(UserWebPath.LOGIN_PASSWORD)
        if (email == null || password == null) {
            sendError(ctx, 400)
            ctx.json(mapOf("error" to "email or password is missing"))
        } else {
            val asyncResult = GlobalScope.async { loginUser.login(email, password) }
                .asCompletableFuture()
            ctx.json(asyncResult)
        }
    }

    private fun mapConflictError(ctx: Context, user: User?, e: Throwable?): User? {
        if (e != null && e is UserAlreadyExistsException) sendError(ctx, 409)
        return user
    }

    private fun sendError(ctx: Context, code: Int) {
        ctx.status(code)
    }
}

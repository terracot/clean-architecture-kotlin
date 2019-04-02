package vova.example.javalin.controller

import io.javalin.Context
import io.javalin.HttpResponseException
import io.javalin.NotFoundResponse
import io.javalin.apibuilder.ApiBuilder
import kotlinx.coroutines.*
import kotlinx.coroutines.future.asCompletableFuture
import org.eclipse.jetty.http.HttpStatus
import vova.example.domain.entity.User
import vova.example.domain.entity.UserWebPath
import vova.example.domain.exception.UserAlreadyExistsException
import vova.example.javalin.model.UserWeb
import vova.example.usecase.CreateUser
import vova.example.usecase.FindUser
import vova.example.usecase.LoginUser
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionException
import java.util.function.BiFunction

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

//    var getAllUsers = { ctx ->
//        val model = ViewUtil.baseModel(ctx)
//        model.put("books", bookDao.getAllBooks())
//        ctx.render(Path.Template.BOOKS_ALL, model)
//    }

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
        try {
            val asyncResult = GlobalScope.async(handler) { createUser.create(userWeb.toUser()) }
                .asCompletableFuture()
                .thenApplyAsync{user -> UserWeb.toUserWeb(user)}
            ctx.json(asyncResult)
            //TODO Async catch
        } catch (e: CompletionException) {
            sendError(ctx, 409, "User already exists:${e.cause?.message}")
        }
    }

    fun getUser(ctx: Context) {
        val userId = ctx.pathParam(userIdParam)
        val asyncResult : CompletableFuture<UserWeb> = GlobalScope.async {findUser.findById(userId)}
            .asCompletableFuture()
            .thenApplyAsync { userOpt ->
                if(userOpt.isPresent) {
                    UserWeb.toUserWeb(userOpt.get())
                } else {
                    //throw HttpResponseException(HttpStatus.NOT_FOUND_404, details = mapOf("error" to "Not found"), msg = "test")
                    throw NotFoundResponse("User with id:$userId Not found")
                    //mapOf("error" to "Not found")
                }
            }
        ctx.json(asyncResult)
    }

    fun userLogin(ctx: Context) {
        val email = ctx.queryParam(UserWebPath.LOGIN_EMAIL)
        val password = ctx.queryParam(UserWebPath.LOGIN_PASSWORD)
        if (email == null || password == null) {
            sendError(ctx, 400, "email or password is missing")
        } else {
            val asyncResult = GlobalScope.async {loginUser.login(email, password)}
                .asCompletableFuture()
            ctx.json(asyncResult)
        }
    }

    private fun sendError(ctx: Context, code: Int, message: String) {
        ctx.status(code)
        ctx.json(mapOf("error" to message))
    }
//    fun createUser(routingContext: RoutingContext) {
//        val response = routingContext.response()
//        val body = routingContext.body
//        if (isNull(body)) {
//            sendError(400, response)
//        } else {
//            val userWeb = body.toJsonObject().mapTo(UserWeb::class.java)
//            var user: User?
//            try {
//                user = createUser.create(userWeb.toUser())
//                val result = JsonObject.mapFrom(UserWeb.toUserWeb(user))
//                sendSuccess(result, response)
//            } catch (e: UserAlreadyExistsException) {
//                val error = JsonObject.mapFrom(mapOf("error" to "User already exists:" + e.message))
//                sendError(409, response, error)
//            }
//
//        }
//    }
//
//    fun login(routingContext: RoutingContext) {
//        val response = routingContext.response()
//        val email = routingContext.request().getParam("email")
//        val password = routingContext.request().getParam("password")
//        if (email == null || password == null) {
//            sendError(400, response)
//        } else {
//            val user = loginUser.login(email, password)
//            val result = JsonObject.mapFrom(UserWeb.toUserWeb(user))
//            sendSuccess(result, response)
//        }
//    }
//
//    fun findUser(routingContext: RoutingContext) {
//        val response = routingContext.response()
//        val userId = routingContext.request().getParam("userId")
//        if (userId == null) {
//            sendError(400, response)
//        } else {
//            val user = findUser.findById(userId)
//            if (user.isPresent) {
//                val result = JsonObject.mapFrom(UserWeb.toUserWeb(user.get()))
//                sendSuccess(result, response)
//            } else {
//                sendError(404, response)
//            }
//        }
//    }
//
//    fun findAllUser(routingContext: RoutingContext) {
//        val response = routingContext.response()
//        val users = findUser.findAllUsers()
//        val result = JsonArray(users.map { user -> JsonObject.mapFrom(UserWeb.toUserWeb(user)) })
////            .collect<JsonArray, List<JsonObject>>(JsonCollectors.toJsonArray())
//        response
//            .putHeader("content-type", "application/json")
//            .end(result.encodePrettily())
//    }
//
//
//    private fun isNull(buffer: Buffer?): Boolean {
//        return buffer == null || "" == buffer.toString()
//    }
//
//    private fun sendError(statusCode: Int, response: HttpServerResponse, body: JsonObject) {
//        response
//            .putHeader("content-type", "application/json")
//            .setStatusCode(statusCode)
//            .end(body.encodePrettily())
//    }
//
//    private fun sendError(statusCode: Int, response: HttpServerResponse) {
//        response
//            .putHeader("content-type", "application/json")
//            .setStatusCode(statusCode)
//            .end()
//    }
//
//    private fun sendSuccess(body: JsonObject, response: HttpServerResponse) {
//        response
//            .putHeader("content-type", "application/json")
//            .end(body.encodePrettily())
//    }

}

package vova.example.vertx.controller

import io.vertx.core.buffer.Buffer
import io.vertx.core.http.HttpMethod
import io.vertx.core.http.HttpServerResponse
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext
import vova.example.domain.entity.User
import vova.example.domain.entity.UserWebPath
import vova.example.domain.exception.UserAlreadyExistsException
import vova.example.usecase.CreateUser
import vova.example.usecase.FindUser
import vova.example.usecase.LoginUser
import vova.example.vertx.model.UserWeb

class VertxUserController(
    private val createUser: CreateUser,
    private val findUser: FindUser,
    private val loginUser: LoginUser
) {

    private val userIdPathParam = "userId"

    suspend fun createUser(routingContext: RoutingContext) {
        val response = routingContext.response()
        val body = routingContext.body
        if (isNull(body)) {
            sendError(400, response, "user object is missing")
        } else {
            val userWeb = body.toJsonObject().mapTo(UserWeb::class.java)
            var user: User?
            try {
                user = createUser.create(userWeb.toUser())
                val result = JsonObject.mapFrom(UserWeb.toUserWeb(user))
                sendSuccess(result, response)
            } catch (e: UserAlreadyExistsException) {
                sendError(409, response, "User already exists:${e.message}")
            }

        }
    }

    suspend fun login(routingContext: RoutingContext) {
        val response = routingContext.response()
        val email = routingContext.request().getParam(UserWebPath.LOGIN_EMAIL)
        val password = routingContext.request().getParam(UserWebPath.LOGIN_PASSWORD)
        if (email == null || password == null) {
            sendError(400, response, "email or password is missing")
        } else {
            val user = loginUser.login(email, password)
            val result = JsonObject.mapFrom(UserWeb.toUserWeb(user))
            sendSuccess(result, response)
        }
    }

    suspend fun findUser(routingContext: RoutingContext) {
        val response = routingContext.response()
        val userId = routingContext.request().getParam(userIdPathParam)
        if (userId == null) {
            sendError(400, response, "user id is missing")
        } else {
            val user = findUser.findById(userId)
            if (user.isPresent) {
                val result = JsonObject.mapFrom(UserWeb.toUserWeb(user.get()))
                sendSuccess(result, response)
            } else {
                sendError(404, response, "User $userId not found")
            }
        }
    }

    suspend fun findAllUser(routingContext: RoutingContext) {
        val response = routingContext.response()
        val users = findUser.findAllUsers()
        val result = JsonArray(users.map { user -> JsonObject.mapFrom(UserWeb.toUserWeb(user)) })
//            .collect<JsonArray, List<JsonObject>>(JsonCollectors.toJsonArray())
        response
            .putHeader("content-type", "application/json")
            .end(result.encodePrettily())
    }


    private fun isNull(buffer: Buffer?): Boolean {
        return buffer == null || "" == buffer.toString()
    }

    private fun sendError(statusCode: Int, response: HttpServerResponse, message: String) {
        response
            .putHeader("content-type", "application/json")
            .setStatusCode(statusCode)
            .end(JsonObject(mapOf("error" to message)).encodePrettily())
    }

    private fun sendSuccess(body: JsonObject, response: HttpServerResponse) {
        response
            .putHeader("content-type", "application/json")
            .end(body.encodePrettily())
    }

    fun getRoutes(): Set<RouteDef> {
        return setOf(
            RouteDef(HttpMethod.GET, UserWebPath.USERS, ::findAllUser),
            RouteDef(HttpMethod.GET, "${UserWebPath.USERS}/:$userIdPathParam", ::findUser),
            RouteDef(HttpMethod.POST, UserWebPath.USERS, ::createUser),
            RouteDef(HttpMethod.GET, UserWebPath.LOGIN, ::login)
        )
    }

    class RouteDef(val method: HttpMethod, val path: String, val handler: suspend (RoutingContext) -> Unit)

}


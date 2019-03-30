package vova.example.javalin.controller

import io.javalin.Context
import io.javalin.apibuilder.ApiBuilder
import vova.example.domain.entity.UserWebPath
import vova.example.domain.exception.UserAlreadyExistsException
import vova.example.javalin.model.UserWeb
import vova.example.usecase.CreateUser
import vova.example.usecase.FindUser
import vova.example.usecase.LoginUser

class JavalinUserController(
    private val createUser: CreateUser,
    private val findUser: FindUser,
    private val loginUser: LoginUser
) {
    private val userId = "user-id"

    fun routes() {
        ApiBuilder.post(UserWebPath.USERS, this::createUser)
        ApiBuilder.get(UserWebPath.USERS, this::getAllUsers)
        ApiBuilder.get("${UserWebPath.USERS}/:$userId", this::getUser)
        ApiBuilder.get(UserWebPath.LOGIN, this::userLogin)
    }

//    var getAllUsers = { ctx ->
//        val model = ViewUtil.baseModel(ctx)
//        model.put("books", bookDao.getAllBooks())
//        ctx.render(Path.Template.BOOKS_ALL, model)
//    }

    fun getAllUsers(ctx: Context) {
        ctx.json(findUser.findAllUsers().map{user -> UserWeb.toUserWeb(user)})
    }

    fun createUser(ctx: Context) {
        val userWeb = ctx.body<UserWeb>()
        try {
            val user = createUser.create(userWeb.toUser())
            ctx.json(UserWeb.toUserWeb(user))
        } catch (e: UserAlreadyExistsException) {
            sendError(ctx,409, "User already exists:${e.message}")
        }
    }

    fun getUser(ctx: Context) {
        val userId = ctx.pathParam(userId)
        val user = findUser.findById(userId)
        if (user.isPresent) {
            ctx.json(UserWeb.toUserWeb(user.get()))
        } else {
            sendError(ctx, 404, "Not found")
        }
    }

    fun userLogin(ctx: Context) {
        val email = ctx.queryParam(UserWebPath.LOGIN_EMAIL)
        val password = ctx.queryParam(UserWebPath.LOGIN_PASSWORD)
        if (email == null || password == null) {
            sendError(ctx,400, "email or password is missing")
        } else {
            val user = loginUser.login(email, password)
            ctx.json(user)
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

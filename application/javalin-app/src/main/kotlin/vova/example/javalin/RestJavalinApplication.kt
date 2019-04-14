package vova.example.javalin

import io.javalin.Javalin
import io.javalin.NotFoundResponse
import vova.example.config.JavalinConfig


fun main() {

    val userController = JavalinConfig().userController

    val app = Javalin.create().apply {
        exception(Exception::class.java) { e, _ -> e.printStackTrace() }
//        error(404) { ctx -> ctx.json("not found") }
    }.start(8080)

    app.routes(userController::routes)
//    app.exception(NotFoundResponse.class, (e,ctx)->{
//
//    })
//    app.exception(NotFoundResponse::class.java) { e, ctx ->
//        ctx.json(mapOf("error" to e.message))
//    }
}

package vova.example.vertx

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.vertx.core.AbstractVerticle
import io.vertx.core.Launcher
import io.vertx.core.json.Json
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import vova.example.config.VertxConfig

class RestVertxApplication : AbstractVerticle() {

    private val userController = VertxConfig().vertxUserController

    override fun start() {
        Json.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        Json.mapper.registerModule(KotlinModule())
        val router = Router.router(vertx)
        router.route().handler(BodyHandler.create())
        userController.createRoutes(router)

        vertx.createHttpServer().requestHandler { router.accept(it) }.listen(8080)
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            Launcher.executeCommand("run", RestVertxApplication::class.java.name)
        }
    }
}

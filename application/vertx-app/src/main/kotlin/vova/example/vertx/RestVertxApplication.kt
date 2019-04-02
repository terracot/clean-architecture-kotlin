package vova.example.vertx

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.vertx.core.Launcher
import io.vertx.core.json.Json
import io.vertx.ext.web.Route
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.launch
import vova.example.config.VertxConfig
import io.vertx.kotlin.core.http.listenAwait

class RestVertxApplication : CoroutineVerticle() {

    private val userController = VertxConfig().vertxUserController

    override suspend fun start() {
        Json.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        Json.mapper.registerModule(KotlinModule())
        val router = Router.router(vertx)
        router.route().handler(BodyHandler.create())

        userController.getRoutes()
            .forEach { routeDef -> router.route(routeDef.method, routeDef.path).coroutineHandler(routeDef.handler) }
        vertx.createHttpServer()
            .requestHandler(router)
            .listenAwait(8080)
    }

    fun Route.coroutineHandler(fn: suspend (RoutingContext) -> Unit) {
        handler { ctx ->
            launch(ctx.vertx().dispatcher()) {
                try {
                    fn(ctx)
                } catch (e: Exception) {
                    ctx.fail(e)
                }
            }
        }
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            Launcher.executeCommand("run", RestVertxApplication::class.java.name)
        }
    }

}

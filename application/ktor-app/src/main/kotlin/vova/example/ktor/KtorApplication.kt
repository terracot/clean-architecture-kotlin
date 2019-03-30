package vova.example.ktor

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import vova.example.config.KtorConfig
import io.ktor.jackson.jackson
import io.ktor.routing.Routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import vova.example.ktor.controller.user


fun Application.module() {
//    install(DefaultHeaders)
//    install(CallLogging)
//    install(WebSockets)

    install(ContentNegotiation) {
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
        }
    }

//    DatabaseFactory.init()

    val config = KtorConfig()
    config.initDb()
    val userController = config.userController

    install(Routing) {
        user(userController)
    }

}

fun main() {
    embeddedServer(Netty, 8080, module = Application::module).start()
}

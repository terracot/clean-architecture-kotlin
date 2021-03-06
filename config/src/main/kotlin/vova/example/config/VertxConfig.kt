package vova.example.config


import vova.example.db.InMemoryUserRepository
import vova.example.encoder.Sha256PasswordEncoder
import vova.example.jug.JugIdGenerator
import vova.example.usecase.CreateUser
import vova.example.usecase.FindUser
import vova.example.usecase.LoginUser
import vova.example.vertx.controller.VertxUserController

class VertxConfig {

    private val userRepository = InMemoryUserRepository()
    private val idGenerator = JugIdGenerator()
    private val passwordEncoder = Sha256PasswordEncoder()
    private val createUser = CreateUser(userRepository, passwordEncoder, idGenerator)
    private val findUser = FindUser(userRepository)
    private val loginUser = LoginUser(userRepository, passwordEncoder)
    val vertxUserController = VertxUserController(createUser, findUser, loginUser)
}

package vova.example.config


import vova.example.db.exposed.DatabaseFactory
import vova.example.db.exposed.ExposedRepository
import vova.example.encoder.Sha256PasswordEncoder
import vova.example.jug.JugIdGenerator
import vova.example.ktor.controller.KtorUserController
import vova.example.usecase.CreateUser
import vova.example.usecase.FindUser
import vova.example.usecase.LoginUser

class KtorConfig {
    fun initDb() {
        DatabaseFactory.init()
    }

    private val userRepository = ExposedRepository()
    private val idGenerator = JugIdGenerator()
    private val passwordEncoder = Sha256PasswordEncoder()
    private val createUser = CreateUser(userRepository, passwordEncoder, idGenerator)
    private val findUser = FindUser(userRepository)
    private val loginUser = LoginUser(userRepository, passwordEncoder)
    val userController = KtorUserController(createUser, findUser, loginUser)
}

package vova.example.config


import vova.example.db.InMemoryUserRepository
import vova.example.encoder.Sha256PasswordEncoder
import vova.example.jug.JugIdGenerator
import vova.example.usecase.CreateUser
import vova.example.usecase.FindUser
import vova.example.usecase.LoginUser

class ManualConfig {
    private val userRepository = InMemoryUserRepository()
    private val idGenerator = JugIdGenerator()
    private val passwordEncoder = Sha256PasswordEncoder()

    fun createUser(): CreateUser {
        return CreateUser(userRepository, passwordEncoder, idGenerator)
    }

    fun findUser(): FindUser {
        return FindUser(userRepository)
    }

    fun loginUser(): LoginUser {
        return LoginUser(userRepository, passwordEncoder)
    }
}

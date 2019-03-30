package vova.example.config


import vova.example.db.hazelcast.HazelcastUserRepository
import vova.example.encoder.Sha256PasswordEncoder
import vova.example.usecase.CreateUser
import vova.example.usecase.FindUser
import vova.example.usecase.LoginUser
import vova.example.uuid.UuidGenerator

class SpringConfig {

    private val userRepository = HazelcastUserRepository()
    private val passwordEncoder = Sha256PasswordEncoder()

    fun createUser(): CreateUser {
        return CreateUser(userRepository, passwordEncoder, UuidGenerator())
    }

    fun findUser(): FindUser {
        return FindUser(userRepository)
    }

    fun loginUser(): LoginUser {
        return LoginUser(userRepository, passwordEncoder)
    }
}

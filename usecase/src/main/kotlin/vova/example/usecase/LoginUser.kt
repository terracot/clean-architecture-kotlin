package vova.example.usecase


import vova.example.domain.entity.User
import vova.example.domain.exception.NotAllowedException
import vova.example.domain.port.PasswordEncoder
import vova.example.domain.port.UserRepository

public class LoginUser(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) {

    suspend fun login(email: String, password: String): User {
        val user = userRepository.findByEmail(email).orElseThrow { NotAllowedException("Not allowed") }
        val hashedPassword = passwordEncoder.encode(email + password)
        if (user.password != hashedPassword) throw NotAllowedException("Not allowed")
        return user
    }
}

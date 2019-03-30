package vova.example.domain.port

import vova.example.domain.entity.User
import java.util.Optional

interface UserRepository {

    suspend fun create(user: User): User

    suspend fun findById(id: String): Optional<User>

    suspend fun findByEmail(email: String): Optional<User>

    suspend fun findAllUsers(): List<User>
}

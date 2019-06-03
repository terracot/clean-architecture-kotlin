package vova.example.domain.port

import kotlinx.coroutines.flow.Flow
import vova.example.domain.entity.User
import java.util.Optional

interface UserRepository {

    suspend fun create(user: User): User

    suspend fun findById(id: String): Optional<User>

    suspend fun findByEmail(email: String): Optional<User>

    fun findAllUsers(): Flow<User>
}

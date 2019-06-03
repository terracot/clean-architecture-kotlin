package vova.example.usecase


import kotlinx.coroutines.flow.Flow
import vova.example.domain.entity.User
import vova.example.domain.port.UserRepository
import java.util.Optional

class FindUser(private val repository: UserRepository) {

    suspend fun findById(id: String): Optional<User> {
        return repository.findById(id)
    }

    fun findAllUsers(): Flow<User> {
        return repository.findAllUsers()
    }
}

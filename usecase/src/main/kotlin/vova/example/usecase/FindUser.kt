package vova.example.usecase


import vova.example.domain.entity.User
import vova.example.domain.port.UserRepository
import java.util.Optional

public class FindUser(private val repository: UserRepository) {

    suspend fun findById(id: String): Optional<User> {
        return repository.findById(id)
    }

    suspend fun findAllUsers(): List<User> {
        return repository.findAllUsers()
    }
}

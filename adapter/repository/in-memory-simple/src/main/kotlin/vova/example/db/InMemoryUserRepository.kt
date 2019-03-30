package vova.example.db


import vova.example.domain.entity.User
import vova.example.domain.port.UserRepository

import java.util.*

class InMemoryUserRepository : UserRepository {

    private val inMemoryDb = HashMap<String, User>()

    override suspend fun create(user: User): User {
        inMemoryDb[user.id!!] = user
        return user
    }

    override suspend fun findById(id: String): Optional<User> {
        return Optional.ofNullable(inMemoryDb[id])
    }

    override suspend fun findByEmail(email: String): Optional<User> {
        return inMemoryDb.values.stream()
            .filter { (_, email1) -> email1 == email }
            .findAny()
    }

    override suspend fun findAllUsers(): List<User> {
        return ArrayList(inMemoryDb.values)
    }
}

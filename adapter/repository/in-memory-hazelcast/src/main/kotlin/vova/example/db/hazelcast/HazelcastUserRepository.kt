package vova.example.db.hazelcast

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import vova.example.db.hazelcast.model.UserDb
import vova.example.domain.entity.User
import vova.example.domain.port.UserRepository
import java.util.Optional

class HazelcastUserRepository : UserRepository {

    override suspend fun create(user: User): User {
        val userDb = UserDb.from(user)
        val map = HAZELCAST.getMap<Any, Any>(MAP_NAME)
        map[userDb.id] = userDb
        return user
    }

    override suspend fun findById(id: String): Optional<User> {
        val map = HAZELCAST.getMap<String, UserDb>(MAP_NAME)
        if (map.containsKey(id)) {
            return Optional.of(map[id]!!.toUser())
        }
        return Optional.empty()
    }

    override suspend fun findByEmail(email: String): Optional<User> {
        return HAZELCAST.getMap<String, UserDb>(MAP_NAME)
            .values.stream()
            .filter { userDb -> userDb.toUser().email == email }
            .map<User> { it.toUser() }
            .findAny()
    }

    @FlowPreview
    override fun findAllUsers(): Flow<User> {
        return HAZELCAST.getMap<String, UserDb>(MAP_NAME)
            .values
            .map { it.toUser() }
            .asFlow()
    }

    companion object {

        private val HAZELCAST = Hazelcast.instance
        private const val MAP_NAME = "user"
    }
}

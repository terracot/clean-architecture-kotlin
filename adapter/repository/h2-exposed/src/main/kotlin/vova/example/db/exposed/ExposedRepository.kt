package vova.example.db.exposed

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import vova.example.db.exposed.DatabaseFactory.dbQuery
import vova.example.db.exposed.model.Users
import vova.example.domain.entity.User
import vova.example.domain.port.UserRepository
import java.util.*

class ExposedRepository : UserRepository {

    //TODO Switch to suspend version later to not run blocking

    override suspend fun create(user: User): User {
        dbQuery {
            Users.insert {
                it[id] = user.id!!
                it[email] = user.email
                it[password] = user.password
                it[firstName] = user.firstName
                it[lastName] = user.lastName
            }
        }
        return user
    }

    override suspend fun findById(id: String): Optional<User> =
        dbQuery {
            Optional.ofNullable(
                Users.select { Users.id eq id }
                    .mapNotNull { toUser(it) }
                    .singleOrNull())
        }


    override suspend fun findByEmail(email: String): Optional<User> =
        dbQuery {
            Optional.ofNullable(
                Users.select { Users.email eq email }
                    .mapNotNull { toUser(it) }
                    .singleOrNull())
        }


    override suspend fun findAllUsers(): List<User> =
        dbQuery {
            Users.selectAll()
                .map { toUser(it) }
        }


    private fun toUser(row: ResultRow) =
        User(
            id = row[Users.id],
            email = row[Users.email],
            password = row[Users.password],
            firstName = row[Users.firstName],
            lastName = row[Users.lastName]
        )
}



package vova.example.db.hazelcast.model


import vova.example.domain.entity.User

import java.io.Serializable

class UserDb(
    val id: String,
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val role: String = "none"
) : Serializable {

    fun toUser(): User {
        return User(
            id = id,
            email = email,
            password = password,
            lastName = lastName,
            firstName = firstName
        )
    }

    companion object {
        private const val serialVersionUID = 1L

        fun from(user: User): UserDb {
            return UserDb(
                id = user.id!!,
                email = user.email,
                password = user.password,
                firstName = user.firstName,
                lastName = user.lastName)
        }
    }
}

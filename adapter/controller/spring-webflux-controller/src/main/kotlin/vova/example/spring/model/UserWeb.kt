package vova.example.spring.model

import com.fasterxml.jackson.annotation.JsonInclude
import vova.example.domain.entity.User

@JsonInclude(JsonInclude.Include.NON_NULL)
class UserWeb(
    val id: String? = null,
    val email: String,
    val password: String? = null,
    val firstName: String,
    val lastName: String
) {

    fun toUser(): User {
        return User(
            email = email,
            password = password!!,
            lastName = lastName,
            firstName = firstName
        )
    }

    companion object {

        fun toUserWeb(user: User): UserWeb {
            return UserWeb(
                id = user.id,
                email = user.email,
                // do not map password
                lastName = user.lastName,
                firstName = user.firstName
            )
        }
    }
}

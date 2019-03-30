package vova.example.vertx.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import vova.example.domain.entity.User

@JsonIgnoreProperties(ignoreUnknown = true)
class UserWeb(
    val id: String? = null,
    val password: String? = null,
    val email: String,
    val firstName: String,
    val lastName: String) {

    fun toUser() = User(
            email = email,
            password = password!!,
            lastName = lastName,
            firstName = firstName)

    companion object {
        fun toUserWeb(user: User) =
            UserWeb(
                id = user.id,
                email = user.email,
                // do not map password
                lastName = user.lastName,
                firstName = user.firstName)

    }
}

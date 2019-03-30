package vova.example.domain.entity

data class User (
    val id: String? = null,
    val email: String,
    val password: String,
    val lastName: String,
    val firstName: String
)

object UserWebPath {
    const val USERS = "/users"
    const val LOGIN = "/login"
    const val LOGIN_EMAIL = "email"
    const val LOGIN_PASSWORD = "password"
}

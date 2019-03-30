package vova.example.domain.port

interface PasswordEncoder {

    fun encode(str: String): String
}

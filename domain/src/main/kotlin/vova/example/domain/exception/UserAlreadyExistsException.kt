package vova.example.domain.exception

class UserAlreadyExistsException(email: String) : RuntimeException(email)

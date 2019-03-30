package vova.example.usecase


import vova.example.domain.entity.User
import vova.example.domain.exception.UserAlreadyExistsException
import vova.example.domain.port.IdGenerator
import vova.example.domain.port.PasswordEncoder
import vova.example.domain.port.UserRepository
import vova.example.usecase.validator.UserValidator

class CreateUser(
    private val repository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val idGenerator: IdGenerator
) {

    suspend fun create(user: User): User {
        UserValidator.validateCreateUser(user)
        if (repository.findByEmail(user.email).isPresent) {
            throw UserAlreadyExistsException(user.email)
        }
        val userToSave = User(
            id = idGenerator.generate(),
            email = user.email,
            password = passwordEncoder.encode(user.email + user.password),
            lastName = user.lastName,
            firstName = user.firstName
        )
        return repository.create(userToSave)
    }
}

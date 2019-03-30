package vova.example.usecase.validator


import vova.example.domain.entity.User
import vova.example.domain.exception.UserValidationException

import org.apache.commons.lang3.StringUtils.isBlank

object UserValidator {

    fun validateCreateUser(user: User?) {
        if (user == null) throw UserValidationException("User should not be null")
        if (isBlank(user.email)) throw UserValidationException("Email should not be null")
        if (isBlank(user.firstName)) throw UserValidationException("First name should not be null")
        if (isBlank(user.lastName)) throw UserValidationException("Last name should not be null")
    }
}

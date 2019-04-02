package vova.example

import vova.example.config.ManualConfig
import vova.example.domain.entity.User

suspend fun main() {
    // Setup
    val config = ManualConfig()
    val createUser = config.createUser()
    val findUser = config.findUser()
    val loginUser = config.loginUser()
    val user = User(
        email = "john.doe@gmail.com",
        password = "mypassword",
        lastName = "doe",
        firstName = "john")

    // Create a user
    val actualCreateUser = createUser.create(user)
    System.out.println("User created with id " + actualCreateUser.id)

    // Find a user by id
    val actualFindUser = findUser.findById(actualCreateUser.id!!)
    System.out.println("Found user with id " + actualFindUser.get().id)

    // List all users
    val users = findUser.findAllUsers()
    println("List all users: $users")

    // Login
    loginUser.login("john.doe@gmail.com", "mypassword")
    println("Allowed to login with email 'john.doe@gmail.com' and password  'mypassword'")

}
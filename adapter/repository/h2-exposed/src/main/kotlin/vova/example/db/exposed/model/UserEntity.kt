package vova.example.db.exposed.model

import org.jetbrains.exposed.sql.Table

object Users : Table() {
    val id = varchar("id",40).primaryKey()
    val email = varchar("email", 255)
    val password = varchar("password", 255)
    val firstName = varchar("firstName", 255)
    val lastName = varchar("lastName", 255)
}

//data class UserDb(val id: String, val name:)
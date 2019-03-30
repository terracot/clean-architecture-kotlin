package vova.example.uuid


import vova.example.domain.port.IdGenerator

import java.util.UUID

class UuidGenerator : IdGenerator {

    override fun generate(): String {
        return UUID.randomUUID().toString()
    }
}

package vova.example.jug

import com.fasterxml.uuid.EthernetAddress
import com.fasterxml.uuid.Generators
import com.fasterxml.uuid.NoArgGenerator
import vova.example.domain.port.IdGenerator

class JugIdGenerator : IdGenerator {

    override fun generate(): String {
        return generator().generate().toString().replace("-".toRegex(), "")
    }

    private fun generator(): NoArgGenerator {
        return Generators.timeBasedGenerator(EthernetAddress.fromInterface())
    }
}

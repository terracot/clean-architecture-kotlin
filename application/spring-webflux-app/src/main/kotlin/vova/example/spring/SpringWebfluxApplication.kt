package vova.example.spring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(value = ["vova.example"])
class SpringWebfluxApplication

fun main(args: Array<String>) {
    runApplication<SpringWebfluxApplication>(*args)
}
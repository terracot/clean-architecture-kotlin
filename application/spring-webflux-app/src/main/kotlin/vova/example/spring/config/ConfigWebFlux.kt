package vova.example.spring.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import vova.example.config.SpringConfig

@Configuration
open class ConfigWebFlux {

    private val config = SpringConfig()

    @Bean
    open fun createUser() = config.createUser()

    @Bean
    open fun findUser() = config.findUser()

    @Bean
    open fun loginUser() = config.loginUser()
}

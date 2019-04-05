package vova.example.spring.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import vova.example.config.SpringConfig
import vova.example.usecase.CreateUser
import vova.example.usecase.FindUser
import vova.example.usecase.LoginUser

@Configuration
open class ConfigWebFlux {

    private val config = SpringConfig()

    @Bean
    open fun createUser(): CreateUser {
        return config.createUser()
    }

    @Bean
    open fun findUser(): FindUser {
        return config.findUser()
    }

    @Bean
    open fun loginUser(): LoginUser {
        return config.loginUser()
    }
}

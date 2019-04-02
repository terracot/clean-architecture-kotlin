package vova.example.spring.controller

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.future.asCompletableFuture
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import vova.example.domain.entity.UserWebPath
import vova.example.spring.model.UserWeb
import vova.example.usecase.LoginUser
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping(UserWebPath.LOGIN)
open class LoginController @Autowired
constructor(private val loginUser: LoginUser) {

    @Async
    @GetMapping
    open fun login(@RequestParam(UserWebPath.LOGIN_EMAIL) email: String,
                   @RequestParam(UserWebPath.LOGIN_PASSWORD) password: String): CompletableFuture<UserWeb> {
        return GlobalScope.async {
            UserWeb.toUserWeb(loginUser.login(email, password))
        }.asCompletableFuture()
    }

}

package vova.example.spring.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import vova.example.domain.entity.UserWebPath
import vova.example.spring.model.UserWeb
import vova.example.usecase.LoginUser

@RestController
@RequestMapping(UserWebPath.LOGIN)
class LoginController @Autowired
constructor(private val loginUser: LoginUser) {

    @GetMapping
    fun login(@RequestParam(UserWebPath.LOGIN_EMAIL) email: String,
              @RequestParam(UserWebPath.LOGIN_PASSWORD) password: String): UserWeb {
        return UserWeb(email="",firstName = "",lastName = "")//UserWeb.toUserWeb(loginUser.login(email, password))
    }

}

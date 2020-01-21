package de.htwesports.wesports.registrations

import de.htwesports.wesports.users.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

//@Profile("test")
@Controller
class UserTestEnabler{

    @Autowired
    lateinit var userService: UserService

    @GetMapping("/enable/{email:.+}")
    fun enableUser(@PathVariable("email")email: String):ModelAndView{
        userService.enableUser(email)
        return ModelAndView("index.html")    }
}
package de.htwesports.wesports.registrations

import de.htwesports.wesports.errors.UserAlreadyExistsException
import de.htwesports.wesports.users.User
import de.htwesports.wesports.users.UserDto
import de.htwesports.wesports.users.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.ModelAndView
import javax.validation.Valid

@Controller
class RegistrationsController {

    @Autowired
    private lateinit var userService: UserService

    @GetMapping("/register")
    fun show(model: Model): ModelAndView {
        val userDto = UserDto()
        model.addAttribute("user", userDto)
        return ModelAndView("register")
    }

    @PostMapping("/register")
    fun new(@Valid @ModelAttribute("user") accountDto: UserDto, result: BindingResult, model: Model): ModelAndView {
        if (!result.hasErrors()) {
            val registered: User? = create(accountDto, result)
            if (registered == null) {
                result.rejectValue("email", "message.regError")
            }
        }
        return if (result.hasErrors()) {
            model.addAttribute("user", accountDto)
            ModelAndView("register")
        } else {
            ModelAndView("redirect:/")
        }
    }

    fun create(accountDto: UserDto, result: BindingResult): User? {
        return try {
            userService.createUserAccount(accountDto)
        } catch (e: UserAlreadyExistsException) {
            null
        }
    }
}

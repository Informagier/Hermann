package de.htwesports.wesports.registrations

import de.htwesports.wesports.errors.UserAlreadyExistsException
import de.htwesports.wesports.users.User
import de.htwesports.wesports.users.UserDto
import de.htwesports.wesports.users.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.ModelAndView
import java.util.*
import javax.validation.Valid


@Controller
class RegistrationsController(var userService: UserService, var eventPublisher: ApplicationEventPublisher) {

    @GetMapping("/register")
    fun show(model: Model): ModelAndView {
        val userDto = UserDto()
        model.addAttribute("user", userDto)
        return ModelAndView("register.html")
    }

    @PostMapping("/register")
    fun new(
            @Valid @ModelAttribute("user") accountDto: UserDto,
            result: BindingResult,
            model: ModelMap,
            request: WebRequest): ModelAndView{
        var registered: User? = null
        if (!result.hasErrors()) {
             registered = create(accountDto, result)
            if (registered == null) {
                result.rejectValue("email", "message.regError")
            }
        }
        return if (result.hasErrors()) {
            model.addAttribute("user", accountDto)
            ModelAndView("register.html")
        } else {
            try{
                var appUrl = request.contextPath
                eventPublisher.publishEvent(RegistrationCompleteEvent(registered, appUrl))
            }
            catch(e : Exception){
                e.printStackTrace()
                return ModelAndView("emailError", "user", accountDto)

            }
            val succMessage = "You registered successfully. We will send you a confirmation message to your email account."
            model.addAttribute("succMessage", succMessage)
            return ModelAndView("redirect:/", model)
        }
    }

    @GetMapping("/")
    fun goToIndex(model: ModelMap?, @ModelAttribute("succMessage") succMessage: String?): ModelAndView{
        if(succMessage != null && succMessage != "") model?.addAttribute("succMessage", succMessage)
        return ModelAndView("index")
    }

    @RequestMapping(value = ["/registrationConfirm"], method = [RequestMethod.GET])
    fun confirmRegistration(request: WebRequest, model: Model, @RequestParam("token") token: String ):String {

        val verificationToken = userService.getVerificationToken(token)
        if (verificationToken == null) {
            val message = "Invalid token."
            model.addAttribute("message", message)
            return "redirect:/badUser.html"
        }
        val user = verificationToken.user
        val now = Date(Calendar.getInstance().time.time).time
        val expiringDate = verificationToken.expiryDate!!.time
        if ((expiringDate.minus(now)) <= 0) {
            val messageValue = "Your registration token has expired. Please register again."
            model.addAttribute("message", messageValue)
            return "redirect:/badUser.html"
        }
        user!!.enabled = true
        userService.saveRegisteredUser(user)
        return "redirect:/"
    }

        fun create(accountDto: UserDto, result: BindingResult): User? {
        return try {
            userService.createUserAccount(accountDto)
        } catch (e: UserAlreadyExistsException) {
            null
        }
    }
}

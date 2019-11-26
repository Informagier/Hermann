package de.htwesports.wesports.welcome

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class WelcomeController {

    @GetMapping("/")
    fun index(model: Model): ModelAndView {
        model.addAttribute("title", "Home")
        return ModelAndView("index")
    }
}

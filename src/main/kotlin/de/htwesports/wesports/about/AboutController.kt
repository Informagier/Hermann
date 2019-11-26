package de.htwesports.wesports.about

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView
import java.time.LocalDateTime

@Controller
class AboutController {

    @GetMapping("/about")
    fun index(model: Model): ModelAndView {
        model.addAttribute("localDateTime", LocalDateTime.now())
        return ModelAndView("about")
    }
}

package de.htwesports.wesports

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView
import java.time.LocalDateTime

@Controller
class AboutController {
    @RequestMapping("/about")
    fun aboutUs(model: Model): ModelAndView {
        model.addAttribute("localDateTime", LocalDateTime.now())
        return ModelAndView("about")
    }
}

package de.htwesports.wesports.profile

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
class ProfileListController {

    @Autowired
    private lateinit var profileRepository: ProfileRepository

    @GetMapping("profiles/profileList")
    fun getProfileList(model: Model): ModelAndView {
        var profiles = profileRepository.findAll()
        model.addAttribute("profiles",profiles)
        return ModelAndView("profileList")
    }
}
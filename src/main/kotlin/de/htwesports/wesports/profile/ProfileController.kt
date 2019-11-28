package de.htwesports.wesports.profile

import de.htwesports.wesports.users.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.ModelAndView

@Controller
class ProfileController {

    @Autowired
    private lateinit var profileRepository: ProfileRepository

    @Autowired
    private lateinit var userRepository: UserRepository


    @GetMapping("/profile")
    fun getProfiles(model: Model, request: WebRequest):ModelAndView{
        //em.find(Profile::class,1)
        var profile = userRepository.findByEmail(request.userPrincipal!!.name)?.profile

        profile = profileRepository.findByProfileId(profile!!.profileId)
        model.addAttribute("Profile",profile)
        return ModelAndView("profile")
    }
}

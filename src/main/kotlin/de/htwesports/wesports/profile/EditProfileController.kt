package de.htwesports.wesports.profile

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.context.request.WebRequest
import org.springframework.validation.BindingResult
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*


@Controller
class EditProfileController {

    @Autowired
    lateinit var service: ProfileService

    @Autowired
    lateinit var profileRepository: ProfileRepository


    @GetMapping("/profiles/{uri}/edit")
    //TODO uncomment annotation below
    @PreAuthorize("profileRepository.findByUri(#uri).user.email eq principal.username")
    fun showEditForm(@PathVariable("uri") uri: String, model: Model, request: WebRequest): ModelAndView {
        //println("URI: "+userRepository.findByEmail(SecurityContextHolder.getContext().authentication.name)?.profile?.uri)
        //println("Uri User: "+profileRepository.findByUri(uri)?.user?.email)
        //println("Principal.Username: "+authentification.principal.username)
        val profile: Profile = profileRepository.findByUri(uri) ?: return ModelAndView("profileList")
        model.addAttribute("profile", profile)
        return ModelAndView("editProfile")
    }

    @PostMapping("/profiles/{uri}")
    //TODO uncomment annotation below
    //@PreAuthorize("#uri == userRepository.findByEmail(authentication.principal.username).profile?.uri")
    fun safeProfile(@PathVariable("uri") uri: String,
                    @ModelAttribute("profile") profileDto: ProfileDto,
                    result: BindingResult, request: WebRequest, model:Model, errors: Errors): ModelAndView {
        val existingProfile = profileRepository.findByUri(uri)?: return ModelAndView("index")
        if (!result.hasErrors()) {
            service.saveProfile(profileDto,existingProfile)
        }
        model.addAttribute("Profile",existingProfile)
        return  ModelAndView("profile")
    }

}

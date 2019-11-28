package de.htwesports.wesports.profile

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.context.request.WebRequest
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.validation.BindingResult
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable


@Controller
class EditProfileController {

    @Autowired
    lateinit var service: ProfileService

    @Autowired
    private lateinit var profileRepository: ProfileRepository


    @RequestMapping(value = ["/edit/{uri}"], method = [RequestMethod.GET])
    //TODO uncomment annotation below
    @PreAuthorize("#username == userRepository.findByEmail(authentication.principal.username).profile?.username")
    fun showEditForm(@PathVariable("uri") uri: String, model: Model): String {
        //val profileDto = ProfileDto()
        val profile = profileRepository.findByUri(uri)
        model.addAttribute("profile", profile)
        return "editProfile"
    }
    @RequestMapping(value = ["/{uri}"], method = [RequestMethod.POST])
    //TODO uncomment annotation below
    //@PreAuthorize("#username == userRepository.findByEmail(authentication.principal.username).profile?.username")
    fun safeProfile(@PathVariable("uri") uri: String,
                    @ModelAttribute("profile") profileDto: ProfileDto,
                    result: BindingResult, request: WebRequest, model:Model, errors: Errors): ModelAndView {
        val existingProfile = profileRepository.findByUri(uri)
        if (!result.hasErrors()) {
            service.saveProfile(profileDto,existingProfile)
        }
        val profile = profileRepository.findByUri(uri)
        model.addAttribute("Profile",profile)
        return  ModelAndView("profile")
    }

}

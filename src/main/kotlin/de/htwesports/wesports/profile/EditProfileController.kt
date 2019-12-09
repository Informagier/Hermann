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
class EditProfileController(var profileRepository: ProfileRepository) {

    @Autowired
    lateinit var service: ProfileService

    @GetMapping("/profiles/{uri}/edit")
    @PreAuthorize("@accessService.isOwner(authentication.name,#uri)")
    fun showEditForm(@PathVariable("uri") uri: String, model: Model): ModelAndView {
        val profile: Profile = profileRepository.findByUri(uri) ?: return ModelAndView("profileList")
        model.addAttribute("profile", profile)
        return ModelAndView("editProfile")
    }


    @PostMapping("/profiles/{uri}")
    @PreAuthorize("@accessService.isOwner(authentication.name,#uri)")
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

package de.htwesports.wesports.profile

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.context.request.WebRequest
import org.springframework.validation.BindingResult
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException


@Controller
class EditProfileController(var profileRepository: ProfileRepository) {

    @Autowired
    lateinit var service: ProfileService

    @PreAuthorize("isOwner(#uri)")
    @GetMapping("/profiles/{uri}/edit")
    @ResponseBody
    fun showEditForm(@PathVariable("uri") uri: String, model: Model): ModelAndView {
        val profile: Profile = profileRepository.findByUri(uri)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "profile not found")
        model.addAttribute("profile", profile)
        return ModelAndView("editProfile")
    }


    @PostMapping("/profiles/{uri}")
    @PreAuthorize("isOwner(#uri)")
    fun safeProfile(@PathVariable("uri") uri: String,
                    @ModelAttribute("profile") profileDto: ProfileDto,
                    result: BindingResult, request: WebRequest, model:Model, errors: Errors): ModelAndView {
        val existingProfile = profileRepository.findByUri(uri)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "profile not found")
        if (!result.hasErrors()) {
            service.saveProfile(profileDto,existingProfile)
        }
        model.addAttribute("profile",existingProfile)
        return  ModelAndView("redirect:/profiles/${uri}")
    }

}

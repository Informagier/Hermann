package de.htwesports.wesports.profile

import de.htwesports.wesports.users.User
import de.htwesports.wesports.users.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.ModelAndView
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.server.ResponseStatusException



@Controller
class ProfileController (
        private var profileRepository: ProfileRepository,
        private var userRepository: UserRepository
){



    @GetMapping("profiles/profile")
    @PreAuthorize("isAuthenticated()")
    fun getProfile(request: WebRequest):ModelAndView{
        val username: String? =SecurityContextHolder.getContext()?.authentication?.name
        val profile: Profile?
        if(username != null){
            profile = userRepository.findByEmail(username)?.profile
                    ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "profile not found")
        }
        else{
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "profile not found")

        }
        return ModelAndView("redirect:/profiles/${profile.uri}")
    }

    @GetMapping("profiles/{uri}")
    fun goToProfile(@PathVariable("uri") uri: String, model: Model): ModelAndView {

        val profile:Profile? = profileRepository.findByUri(uri)
        if (profile != null) {
            model.addAttribute("profile", profile)
        }
        else{
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "profile not found")
        }
        return ModelAndView("profile")
    }

}

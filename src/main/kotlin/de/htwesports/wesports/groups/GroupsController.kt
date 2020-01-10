package de.htwesports.wesports.groups

import de.htwesports.wesports.users.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.ModelAndView
import javax.validation.Valid

@Controller
class GroupsController(
    val groupRepository: GroupRepository,
    val userRepository: UserRepository
){

    @RequestMapping(value = ["groups"],
            method = [RequestMethod.GET],
            produces = [MediaType.TEXT_HTML_VALUE])
    @PreAuthorize("isAuthenticated()")
    fun showGroups(model: Model): ModelAndView{
        val userEmail = SecurityContextHolder.getContext()?.authentication?.name
        if(userEmail == null)
            throw ResponseStatusException(HttpStatus.FORBIDDEN)
        val profile = userRepository.findByEmail(userEmail)!!
        val groups = profile.groups
        model.addAttribute("groups", groups)
        return ModelAndView("groups")
    }
}
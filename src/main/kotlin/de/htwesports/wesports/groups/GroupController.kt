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
class GroupController(
    val groupRepository: GroupRepository,
    val userRepository: UserRepository
){
    @RequestMapping(value = ["groups/{group_uri}"],
            method = [RequestMethod.GET],
            produces = [MediaType.TEXT_HTML_VALUE])
    @PreAuthorize("isAuthenticated()")
    fun showGroup(@PathVariable("group_uri") uri: String, model: Model): ModelAndView {
        val group = groupRepository.findByUri(uri)
        return if( group != null) {
            val vars = mapOf("has_group_edit_access" to (SecurityContextHolder.getContext()?.authentication?.name == group.owner.email))
            model.addAttribute("group", group)
            ModelAndView("group", vars)
        }else{
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "group not found")
        }
    }

    @RequestMapping(value = ["groups/{uri}/edit"],
            method = [RequestMethod.GET],
            produces = [MediaType.TEXT_HTML_VALUE])
    @PreAuthorize("isAuthenticated()")
    fun showGroupEdit(@PathVariable("group_uri") uri: String, model: Model): ModelAndView {
        val group = groupRepository.findByUri(uri)
        val user = userRepository.findByEmail(SecurityContextHolder.getContext()?.authentication?.name!!)!!
        return if(group == null){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "group not found")
        }else if(user != group.owner){
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
        }else{
            model.addAttribute("group", GroupDto())
            ModelAndView("editGroup")
        }
    }

    @PostMapping("/groups/{uri}/edit")
    @PreAuthorize("isAuthenticated()")
    fun editGroup(@PathVariable("uri") uri: String, @Valid @ModelAttribute("group") groupDto: GroupDto, result: BindingResult, model: Model): ModelAndView {
        return if (!result.hasErrors()) {
            val user = userRepository.findByEmail(SecurityContextHolder.getContext()?.authentication?.name!!)!!
            val group = groupRepository.findByUri(uri) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
            if(user != group.owner)
                throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
            ModelAndView("redirect:/groups/" + group.uri)
        } else {
            ModelAndView("/groups/" + uri + "/new")
        }
    }

    @RequestMapping(value = ["groups/new"],
            method = [RequestMethod.GET],
            produces = [MediaType.TEXT_HTML_VALUE])
    @PreAuthorize("isAuthenticated()")
    fun newGroup(model: Model): ModelAndView {
        model.addAttribute("group", GroupDto())
        return ModelAndView("newGroup")
    }

    @PostMapping("/groups/new")
    @PreAuthorize("isAuthenticated()")
    fun createGroup(@Valid @ModelAttribute("group") groupDto: GroupDto, result: BindingResult, model: Model): ModelAndView {
        return if (!result.hasErrors()) {
            val user = userRepository.findByEmail(SecurityContextHolder.getContext()?.authentication?.name!!)!!
            val group = groupRepository.save(groupDto.createGroup(user))
            ModelAndView("redirect:/groups/" + group.uri)
        } else {
            ModelAndView("redirect:/groups/new")
        }
    }
}
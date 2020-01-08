package de.htwesports.wesports.groups

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.ModelAndView

@Controller
class GroupController(
    val groupRepository: GroupRepository
){

    @RequestMapping(value = ["groups/{group_uri}"],
            method = [RequestMethod.GET],
            produces = [MediaType.TEXT_HTML_VALUE])
    @PreAuthorize("isAuthenticated()")
    fun showGroup(@PathVariable("group_uri") uri: String, model: Model): ModelAndView {
        val group = groupRepository.findByUri(uri)
        return if( group != null) {
            val vars = mapOf("has_group_edit_access" to false)
            model.addAttribute(group)
            ModelAndView("group", vars)
        }else{
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "group not found")
        }
    }
}
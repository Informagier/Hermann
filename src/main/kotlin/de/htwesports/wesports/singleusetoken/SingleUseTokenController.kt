package de.htwesports.wesports.singleusetoken

import de.htwesports.wesports.profile.ProfileRepository
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class SingleUseTokenController(
        val profRepo: ProfileRepository,
        val sutRepo: SingleUseTokenRepository
) {
    @RequestMapping(value = ["profiles/{prof_uri}/suts/new"],
            method = [RequestMethod.POST],
            produces = [MediaType.TEXT_PLAIN_VALUE])
    @ResponseBody
    fun getNewSUT(@PathVariable("prof_uri") uri: String): ResponseEntity<String> {
        val profile = profRepo.findByUri(uri)
        return when {
            (profile == null) -> ResponseEntity(HttpStatus.BAD_REQUEST)

            (profile.user!!.email != SecurityContextHolder.getContext().authentication.name) ->
                ResponseEntity(HttpStatus.FORBIDDEN)

            else -> {
                val token = sutRepo.save(SingleUseToken(profRepo.findByUri(uri)?.user!!))
                return ResponseEntity(token.token!!, HttpStatus.CREATED)
            }
        }
    }
}
package de.htwesports.wesports.profile

import de.htwesports.wesports.users.User
import org.springframework.stereotype.Service

@Service
class AccessService(private val profileRepository: ProfileRepository) {

    fun isOwner(username:String, uri: String ):Boolean{
        val user: User? = profileRepository.findByUri(uri)?.user
        if(user == null || user.email != username){
            return false
        }
        return true
    }
}
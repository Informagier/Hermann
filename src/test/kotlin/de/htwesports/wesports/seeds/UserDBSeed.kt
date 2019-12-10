package de.htwesports.wesports.seeds

import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener


import de.htwesports.wesports.errors.UserAlreadyExistsException
import de.htwesports.wesports.users.*


@Component
internal class UserDBSeed{

    @Autowired
    private lateinit var userService: UserService

    @EventListener
    fun seedUserDB(evt : ContextRefreshedEvent) {
        val admin = UserDto("admin@example.com", "admin", "admin")
        val user = UserDto("user@example.com", "user", "user")
        try {
            userService.createUserAccount(admin)
            userService.createUserAccount(user)
        }catch (_ : UserAlreadyExistsException){
            //ignore me
        }
    }
}
package de.htwesports.wesports.registrations

import de.htwesports.wesports.users.User
import org.springframework.context.ApplicationEvent
import java.util.*

class RegistrationCompleteEvent(
        var user: User?= null ,
        var appUrl: String = "") : ApplicationEvent(user!!)

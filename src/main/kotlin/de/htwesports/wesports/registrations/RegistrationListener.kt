package de.htwesports.wesports.registrations

import de.htwesports.wesports.users.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.ApplicationListener
import org.springframework.context.MessageSource
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import java.util.*

@Component
class RegistrationListener : ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    lateinit var userService: UserService

    @Qualifier("messageSource")
    @Autowired
    lateinit var messages: MessageSource

    @Autowired
    lateinit var mailSender: JavaMailSender

    override fun onApplicationEvent(event: RegistrationCompleteEvent) {
        this.confirmRegistration(event)
    }

    private fun confirmRegistration(event: RegistrationCompleteEvent) {
        val user = event.user
        val token = UUID.randomUUID().toString()
        userService.createVerificationToken(user!!, token)

        var recipientAddress = user.email
        var subject = "Registration confirmation"
        val confirmationUrl = event.appUrl+"/registrationConfirm.html?token=" + token
        val message = "You registered successfully. Click the link to validate your email address and finish up your registration.."
        var email = SimpleMailMessage()
        email.setTo(recipientAddress)
        email.setSubject(subject)
        email.setText("$message http://localhost:8080$confirmationUrl")
        mailSender.send(email)
    }
}
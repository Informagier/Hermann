package de.htwesports.wesports.registrations

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.web.WebAttributes
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException;



class CustomAuthenticationFailureHandler : SimpleUrlAuthenticationFailureHandler() {

    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationFailure(request: HttpServletRequest,
                                         response: HttpServletResponse?, exception: AuthenticationException) {
        setDefaultFailureUrl("/login?error")
        super.onAuthenticationFailure(request, response, exception)
        println("Exception Message"+exception.message)
        println("Exception Localized Message"+exception.localizedMessage)
        var errorMessage = "Invalid Credentials"
        if (exception.message.equals("User is disabled",ignoreCase = true)) {
            errorMessage = "Your account is not enabled yet. Please check your mail and click on the confirmation link"
        } else if (exception.message.equals("User account has expired", ignoreCase = true)) {
            errorMessage = "Your registration token has expired. Please register again."
        }
        request.session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage)
    }
}
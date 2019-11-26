package de.htwesports.wesports.validation

import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class EmailValidator : ConstraintValidator<ValidEmail, String> {

    private lateinit var matcher: Matcher;
    companion object {
        /*
         * RegExp was taken from Baeldung tutorial repository:
         * https://github.com/Baeldung/spring-security-registration/blob/master/src/main/java/org/baeldung/validation/EmailValidator.java#L12
         */
        private const val EMAIL_PATTERN: String = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        private val pattern: Pattern = Pattern.compile(EMAIL_PATTERN)
    }

    override fun initialize(constraintAnnotation: ValidEmail?) {}

    override fun isValid(email: String, context: ConstraintValidatorContext?): Boolean {
        return validateEmail(email)
    }

    private fun validateEmail(email: String): Boolean {
        matcher = pattern.matcher(email)
        return matcher.matches()
    }
}

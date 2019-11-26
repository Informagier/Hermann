package de.htwesports.wesports.validation

import org.passay.*
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class PasswordConstraintValidator : ConstraintValidator<ValidPassword, String> {

    override fun initialize(constraintAnnotation: ValidPassword?) {}

    override fun isValid(password: String, context: ConstraintValidatorContext): Boolean {
        val validator = PasswordValidator(
                LengthRule(8, 64),
                CharacterRule(EnglishCharacterData.UpperCase, 1),
                CharacterRule(EnglishCharacterData.LowerCase, 1),
                CharacterRule(EnglishCharacterData.Digit, 1),
                WhitespaceRule())
        val result: RuleResult = validator.validate(PasswordData(password))
        if (result.isValid) {
            return true
        }
        val msgTemplate = validator.getMessages(result).joinToString(",")
        context.disableDefaultConstraintViolation()
        context.buildConstraintViolationWithTemplate(msgTemplate).addConstraintViolation()
        return false
    }
}

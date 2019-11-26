package de.htwesports.wesports.validation

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS, AnnotationTarget.FIELD, AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [PasswordConstraintValidator::class])
@MustBeDocumented
annotation class ValidPassword(
        val message: String = "Invalid password",
        val groups : Array<KClass<*>> = [],
        val payload : Array<KClass<out Payload>> = []
)

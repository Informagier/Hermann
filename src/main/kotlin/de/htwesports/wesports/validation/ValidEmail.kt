package de.htwesports.wesports.validation

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS, AnnotationTarget.FIELD, AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [EmailValidator::class])
@MustBeDocumented
annotation class ValidEmail(
        val message: String = "Invalid email",
        val groups : Array<KClass<*>> = [],
        val payload : Array<KClass<out Payload>> = []
)

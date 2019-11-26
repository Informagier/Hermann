package de.htwesports.wesports.users

import de.htwesports.wesports.validation.ValidEmail
import de.htwesports.wesports.validation.ValidPassword
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class UserDto(
        @ValidEmail(message="ValidEmail.user.email")
        @get:NotNull(message="NotNull.user.email")
        @get:NotEmpty(message="NotEmpty.user.email")
        var email: String = "",

        @ValidPassword
        var password: String = ""
)

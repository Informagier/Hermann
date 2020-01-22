package de.htwesports.wesports.users

import de.htwesports.wesports.errors.UserAlreadyExistsException
import de.htwesports.wesports.registrations.VerificationToken

interface UserService {
    @Throws(UserAlreadyExistsException::class)
    fun createUserAccount(accountDto: UserDto): User
    fun createVerificationToken(user: User, token: String)
    fun getVerificationToken(verificationToken: String): VerificationToken?
    fun saveRegisteredUser(user: User)
    fun enableUser(email: String)
}

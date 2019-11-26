package de.htwesports.wesports.users

import de.htwesports.wesports.errors.UserAlreadyExistsException

interface UserService {
    @Throws(UserAlreadyExistsException::class)
    fun createUserAccount(accountDto: UserDto): User
}

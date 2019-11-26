package de.htwesports.wesports.errors

import java.lang.RuntimeException

class UserAlreadyExistsException(message: String) : RuntimeException(message)

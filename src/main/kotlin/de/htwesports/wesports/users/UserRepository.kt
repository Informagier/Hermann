package de.htwesports.wesports.users

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, String> {
    fun findByUuid(id: String): User?
    fun findByEmail(email: String): User?
}

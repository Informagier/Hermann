package de.htwesports.wesports.registrations

import de.htwesports.wesports.users.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VerificationTokenRepository: JpaRepository<VerificationToken, String>{
    fun findByToken(token: String): VerificationToken?
    fun findByUser(user: User): VerificationToken
}
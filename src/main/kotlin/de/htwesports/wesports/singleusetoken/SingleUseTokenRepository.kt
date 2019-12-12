package de.htwesports.wesports.singleusetoken

import de.htwesports.wesports.users.User
import org.springframework.data.jpa.repository.JpaRepository

interface SingleUseTokenRepository: JpaRepository<SingleUseToken, String> {
    fun findByToken(token: String) : SingleUseToken?
}
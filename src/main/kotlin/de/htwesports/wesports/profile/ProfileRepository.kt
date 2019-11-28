package de.htwesports.wesports.profile

import org.springframework.data.repository.CrudRepository

interface ProfileRepository: CrudRepository<Profile, Long> {
    fun findByProfileId(id: Long): Profile?
    fun findByUsername(id: String): Profile
    fun findByUri(id: String): Profile
}
package de.htwesports.wesports.profile

import org.springframework.data.repository.CrudRepository

interface ProfileRepository: CrudRepository<Profile, Long> {
    fun findByUri(id: String): Profile?
}
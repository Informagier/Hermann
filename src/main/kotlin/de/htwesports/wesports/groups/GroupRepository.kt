package de.htwesports.wesports.groups

import org.springframework.data.jpa.repository.JpaRepository

interface GroupRepository: JpaRepository<Group, String> {
    fun findByUuid(uuid: String): Group?
    fun findByUri(uri: String): Group?
}
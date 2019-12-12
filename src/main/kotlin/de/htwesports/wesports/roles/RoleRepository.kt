package de.htwesports.wesports.roles

import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, String>{
    fun findByName(name: String) : Role
}
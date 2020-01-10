package de.htwesports.wesports.posts

import de.htwesports.wesports.users.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PostsRepository: JpaRepository<Post, Long> {
}

package de.htwesports.wesports.posts

import de.htwesports.wesports.groups.Group
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PostsRepository: JpaRepository<Post, Long> {
    fun findPostsByGroup(group: Group): List<Post>
    fun findPostByGroupAndId(group: Group, id: Long): Optional<Post>
}

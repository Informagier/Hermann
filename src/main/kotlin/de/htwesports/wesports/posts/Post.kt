package de.htwesports.wesports.posts

import de.htwesports.wesports.groups.Group
import de.htwesports.wesports.users.User
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "posts")
class Post(
        var title: String,
        var content: String,
        @ManyToOne
        var author: User,
        @ManyToOne
        var group: Group?
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is Post) return false

        return title == other.title
                && content == other.content
                && author == other.author
                && group == other.group
                && id == other.id
    }

    override fun hashCode(): Int {
        return Objects.hash(title, content, author, group, id)
    }
}

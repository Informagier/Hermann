package de.htwesports.wesports.groups

import de.htwesports.wesports.posts.Post
import org.hibernate.annotations.GenericGenerator
import java.util.UUID
import de.htwesports.wesports.users.User
import java.util.Objects
import javax.persistence.*

@Entity
@Table(name = "groups")
class Group(@ManyToOne
            var owner: User,
            var name: String = "",
            var about: String = "",
            var game: String = "",
            var city: String = "") {

        @Id
        @GeneratedValue(generator = "uuid2")
        @GenericGenerator(name = "uuid2", strategy = "uuid2")
        var uuid: String = UUID.randomUUID().toString()

        @Column(unique=true)
        @GeneratedValue(generator = "uuid2")
        @GenericGenerator(name = "uuid2", strategy = "uuid2")
        var uri: String = UUID.randomUUID().toString()

        @ManyToMany
        @JoinTable(name="group_memberships",
                   joinColumns = [JoinColumn(name = "group_id", referencedColumnName = "uuid")],
                   inverseJoinColumns = [JoinColumn(name = "user_id", referencedColumnName = "uuid")])
        var users: MutableCollection<User> = ArrayList()

        @OneToMany(mappedBy = "group", cascade = [CascadeType.ALL], orphanRemoval = true)
        var posts: MutableList<Post> = ArrayList()

        override fun equals(other: Any?): Boolean {
            return if (this === other){ 
                true
            }else if (other == null || other !is Group){
                return false
            }else{
                listOf(owner, name, about, game) ==
                    listOf(other.owner, other.name, other.about, other.game)
            }
        }

        override fun hashCode(): Int {
                return Objects.hash(owner, name, about, game)
        }

}

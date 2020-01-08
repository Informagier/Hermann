package de.htwesports.wesports.groups

import org.hibernate.annotations.GenericGenerator
import java.util.UUID
import de.htwesports.wesports.roles.Role
import de.htwesports.wesports.users.User
import java.util.Objects
import javax.persistence.*

@Entity
@Table(name = "groups")
class Group(@OneToOne(mappedBy="uuid")
            var owner: User,
            var name: String = "",
            var about: String = "",
            var game: String = "") {

        init {
            if(!users.contains(owner))
                users.add(owner)
        }

        @Id
        @GeneratedValue(generator = "uuid2")
        @GenericGenerator(name = "uuid2", strategy = "uuid2")
        var group_uuid: String = UUID.randomUUID().toString()

        @GeneratedValue(generator = "uuid2")
        @GenericGenerator(name = "uuid2", strategy = "uuid2")
        var uri: String = UUID.randomUUID().toString()

        @ManyToMany
        @JoinTable(name="group_memberships",
                   joinColumns = [JoinColumn(name = "group_id", referencedColumnName = "group_uuid")],
                   inverseJoinColumns = [JoinColumn(name = "user_id", referencedColumnName = "uuid")])
        lateinit var users: MutableCollection<User>

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

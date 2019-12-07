package de.htwesports.wesports.users

import de.htwesports.wesports.profile.Profile
import org.hibernate.annotations.GenericGenerator
import java.util.UUID
import de.htwesports.wesports.roles.Role
import java.util.Objects
import javax.persistence.*

@Entity
@Table(name = "users")
class User(var email: String = "",
           var password: String = "",
           @OneToOne(cascade = [CascadeType.ALL])
           @JoinColumn(name = "profile_id", referencedColumnName = "profileid")
           var profile: Profile? = null) {

        @Id
        @GeneratedValue(generator = "uuid2")
        @GenericGenerator(name = "uuid2", strategy = "uuid2")
        var id: String = UUID.randomUUID().toString()



        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (other == null || other !is User) return false

                return email == other.email
                        && password == other.password
                        && id == other.id
        }

        override fun hashCode(): Int {
                return Objects.hash(email, password, id)
        }

        @ManyToMany
        @JoinTable(
                name = "users_roles",
                joinColumns = [JoinColumn(
                        name = "user_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(
                        name = "role_id", referencedColumnName = "id")])

        lateinit var roles: MutableCollection<Role>
}

package de.htwesports.wesports.users

import de.htwesports.wesports.roles.Role
import org.hibernate.annotations.GenericGenerator
import java.util.UUID
import java.util.Objects
import javax.persistence.*

@Entity
@Table(name = "users")
class User(var email: String = "", var password: String = "") {

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

        private var roles: MutableCollection<Role>? = null
}

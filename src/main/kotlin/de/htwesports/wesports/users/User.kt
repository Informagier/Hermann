package de.htwesports.wesports.users

import de.htwesports.wesports.profile.Profile
import org.hibernate.annotations.GenericGenerator
import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "users")
class User(var email: String = "",
           var password: String = "",
           @OneToOne(cascade =  arrayOf(CascadeType.ALL))
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
                var result = email.hashCode()
                result = 31 * result + password.hashCode()
                result = 31 * result + id.hashCode()
                return result
        }
}

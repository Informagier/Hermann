package de.htwesports.wesports.users

import org.hibernate.annotations.GenericGenerator
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

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
                var result = email.hashCode()
                result = 31 * result + password.hashCode()
                result = 31 * result + id.hashCode()
                return result
        }
}

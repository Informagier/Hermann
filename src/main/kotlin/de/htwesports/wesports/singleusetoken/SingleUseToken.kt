package de.htwesports.wesports.singleusetoken

import de.htwesports.wesports.users.User
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "single_use_tokens")
class SingleUseToken( // will be reffered to as SUT, because of reasons
        @ManyToOne
        @JoinColumn(name = "usa", referencedColumnName = "uuid")
        var user: User,
        @Id
        @GeneratedValue(generator = "uuid2")
        @GenericGenerator(name = "uuid2", strategy = "uuid2")
        var token: String? = null) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SingleUseToken) return false
        if (user != other.user) return false
        if (token != other.token) return false
        return true
    }

    override fun hashCode(): Int =
            Objects.hash(user, token)


}
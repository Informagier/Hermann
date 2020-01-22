package de.htwesports.wesports.registrations

import de.htwesports.wesports.users.User
import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Entity
class VerificationToken{
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int = -1
        private final val EXPIRATION: Int = 60 * 24
        var token: String = ""
        @OneToOne(targetEntity = User::class, fetch = FetchType.EAGER)
        @JoinColumn(nullable = false, name = "uuid")
        var user: User? = null
        var expiryDate: Date? = null

    constructor(user: User){
        this.user = user
        expiryDate = calculateExpiryDate(EXPIRATION)
    }
    constructor(token: String, user: User){
        this.token = token
        this.user = user
        expiryDate = calculateExpiryDate(EXPIRATION)
    }
    private final fun calculateExpiryDate(expiryTimeInMinutes : Int): Date{
        val cal : Calendar = Calendar.getInstance()
        cal.time = Timestamp(cal.time.time)
        cal.add(Calendar.MINUTE, expiryTimeInMinutes)
        return Date(cal.time.time)
    }
    fun updateToken(token: String?) {
        this.token = token!!
        expiryDate = calculateExpiryDate(EXPIRATION)
    }
    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + if (expiryDate == null) 0 else expiryDate.hashCode()
        result = prime * result + if (token == null) 0 else token.hashCode()
        result = prime * result + if (user == null) 0 else user.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null) {
            return false
        }
        if (javaClass != other.javaClass) {
            return false
        }
        val other = other as VerificationToken
        if (expiryDate == null) {
            if (other.expiryDate != null) {
                return false
            }
        } else if (expiryDate != other.expiryDate) {
            return false
        }
        if (token != other.token) {
            return false
        }
        if (user == null) {
            if (other.user != null) {
                return false
            }
        } else if (!user!!.equals(other.user)) {
            return false
        }
        return true
    }
    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("Token [String=").append(token).append("]").append("[Expires").append(expiryDate).append("]")
        return builder.toString()
    }
}
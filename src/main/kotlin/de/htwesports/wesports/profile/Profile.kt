package de.htwesports.wesports.profile

import de.htwesports.wesports.users.User
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "profile")
class Profile(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var profileId: Long = -1,
        var username: String= "",
        var game: String = "",
        var genre: String = "",
        var date: String = "",
        var city: String = "",
        var hobby: String = "",
        /*@Lob
        @Column(name="PROFILE_IMAGE", nullable=false, columnDefinition="mediumblob")
        var photo: ByteArray?=null,*/
        @OneToOne(mappedBy = "profile")
        var user: User? = null

){
        @GeneratedValue(generator = "uuid2")
        @GenericGenerator(name = "uuid2", strategy = "uuid2")
        var uri: String = UUID.randomUUID().toString()
}



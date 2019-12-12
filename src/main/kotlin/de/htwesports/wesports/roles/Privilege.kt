package de.htwesports.wesports.roles

import javax.persistence.*


@Entity
class Privilege (var name : String){

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @ManyToMany(mappedBy = "privileges")
    lateinit var roles: MutableCollection<Role>
}
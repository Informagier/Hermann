package de.htwesports.wesports.roles

import javax.persistence.*


@Entity
class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Long? = null

    private var name: String? = null

    @ManyToMany(mappedBy = "privileges")

    private var roles: MutableCollection<Role>? = null
}
package de.htwesports.wesports.roles

import de.htwesports.wesports.users.User
import javax.persistence.*
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.GenerationType
import javax.persistence.GeneratedValue


@Entity
class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Long? = null

    private var name: String? = null

    @ManyToMany(mappedBy = "roles")

    private var users: MutableCollection<User>? = null

    @ManyToMany
    @JoinTable(name = "roles_privileges", joinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")], inverseJoinColumns = [JoinColumn(name = "privilege_id", referencedColumnName = "id")])
    private var privileges: MutableCollection<Privilege>? = null
}

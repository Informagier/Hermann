package de.htwesports.wesports.roles

import de.htwesports.wesports.users.User
import javax.persistence.*
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.GenerationType
import javax.persistence.GeneratedValue


@Entity
class Role (var name: String){
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0L

    @ManyToMany(mappedBy = "roles")

    lateinit var users: MutableCollection<User>

    @ManyToMany
    @JoinTable(name = "roles_privileges", joinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")], inverseJoinColumns = [JoinColumn(name = "privilege_id", referencedColumnName = "id")])
    lateinit var privileges: MutableCollection<Privilege>
}

package de.htwesports.wesports.groups

import de.htwesports.wesports.users.User

data class GroupDto(
        var name: String? = null,
        var game: String? = null,
        var city: String? = null,
        var about: String? = null
){
    fun createGroup(owner: User): Group = Group(owner, name?:"", about?:"", game?:"", city?:"")
    fun editGroup(group: Group): Group {
        val n = name
        val g = game
        val c = city
        val a = about
        if(n != null)
            group.name = n
        if(g != null)
            group.game = g
        if(c != null)
            group.city = c
        if(a != null)
            group.about = a
        return group
    }
}
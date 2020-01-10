package de.htwesports.wesports.groups

import de.htwesports.wesports.users.User

data class GroupDto(
        var name: String? = null,
        var game: String? = null,
        var city: String? = null,
        var about: String? = null,
        var uri: String? = null
){
    fun createGroup(owner: User): Group {
        val group = Group(owner, name?:"", about?:"", game?:"", city?:"")
        group.users.add(owner)
        return group
    }

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

    constructor(group: Group): this(group.name, group.game, group.city, group.about, group.uri){}
}
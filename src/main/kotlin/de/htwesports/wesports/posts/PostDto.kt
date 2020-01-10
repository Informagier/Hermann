package de.htwesports.wesports.posts

data class PostDto(
        var title: String? = null,
        var content: String? = null,
        var id: Long? = null
) {
    constructor(post: Post): this(post.title, post.content, post.id)
}
package de.htwesports.wesports.groups

import de.htwesports.wesports.posts.Post
import de.htwesports.wesports.posts.PostDto
import de.htwesports.wesports.posts.PostsRepository
import de.htwesports.wesports.users.User
import de.htwesports.wesports.users.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.ModelAndView
import java.util.*

@Controller
class GroupPostsController(
        @Autowired
        private val postsRepository: PostsRepository,
        @Autowired
        private val userRepository: UserRepository,
        @Autowired
        private val groupRepository: GroupRepository
) {

    @GetMapping("/groups/{group_uri}/posts")
    fun showAllPosts(@PathVariable group_uri: String, model: Model): ModelAndView {
        val group = groupRepository.findByUri(group_uri)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "group uri not found")
        val posts: List<Post> = postsRepository.findPostsByGroup(group)
        model.addAttribute("posts", posts)
        model.addAttribute("group", group)
        return ModelAndView("groups/posts/index")
    }

    @GetMapping("/groups/{group_uri}/posts/{id}")
    fun showPost(@PathVariable group_uri: String, @PathVariable id: Long, model: Model): ModelAndView {
        val user = userRepository.findByEmail(SecurityContextHolder.getContext()?.authentication?.name!!)!!
        val group = groupRepository.findByUri(group_uri)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "group uri not found")
        val post: Optional<Post> = postsRepository.findPostByGroupAndId(group, id)

        if (post.isPresent) {
            model.addAttribute("post", post.get())
            model.addAttribute("group", group)
            model.addAttribute("has_post_edit_access", user == post.get().author)
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "post not found")
        }

        return ModelAndView("groups/posts/show")
    }

    @GetMapping("/groups/{group_uri}/posts/new")
    @PreAuthorize("isAuthenticated()")
    fun newPost(@PathVariable group_uri: String, model: Model): ModelAndView {
        val group = groupRepository.findByUri(group_uri)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "group uri not found")
        model.addAttribute("post", PostDto())
        model.addAttribute("group", group)
        return ModelAndView("groups/posts/new")
    }

    @PostMapping("/groups/{group_uri}/posts/new")
    @PreAuthorize("isAuthenticated()")
    fun createPost(@PathVariable group_uri: String, @ModelAttribute("post") postDto: PostDto, model: Model): ModelAndView {
        val user = userRepository.findByEmail(SecurityContextHolder.getContext()?.authentication?.name!!)!!
        val group = groupRepository.findByUri(group_uri)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "post not found")
        val post = Post(postDto.title!!, postDto.content!!, user, group)

        user.posts.add(post)
        group.posts.add(post)
        postsRepository.save(post)

        model.addAttribute("post", post)
        model.addAttribute("group", group)
        return ModelAndView("redirect:/groups/${group.uri}/posts/${post.id}")
    }

    @GetMapping("/groups/{group_uri}/posts/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    fun editPost(@PathVariable group_uri: String, @PathVariable id: Long, model: Model): ModelAndView {
        val group = groupRepository.findByUri(group_uri)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "post not found")
        val post: Optional<Post> = postsRepository.findPostByGroupAndId(group, id)

        if (post.isPresent) {
            model.addAttribute("post", PostDto(post.get()))
            model.addAttribute("group", group)
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "post not found")
        }

        return ModelAndView("groups/posts/edit")
    }

    @PostMapping("/groups/{group_uri}/posts/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    fun updatePost(@PathVariable group_uri: String, @PathVariable id: Long, @ModelAttribute("post") postDto: PostDto, model: Model): ModelAndView {
        val user = userRepository.findByEmail(SecurityContextHolder.getContext()?.authentication?.name!!)!!
        val group = groupRepository.findByUri(group_uri)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "post not found")
        val post: Optional<Post> = postsRepository.findPostByGroupAndId(group, id)

        if (!post.isPresent) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "post not found")
        }

        val author: User = post.get().author
        if (user != author) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "user not allowed to edit others post")
        }

        post.get().title = postDto.title!!
        post.get().content = postDto.content!!
        postsRepository.save(post.get())

        model.addAttribute("post", post.get())
        model.addAttribute("group", group)
        return ModelAndView("redirect:/groups/${group.uri}/posts/${post.get().id}")
    }
}

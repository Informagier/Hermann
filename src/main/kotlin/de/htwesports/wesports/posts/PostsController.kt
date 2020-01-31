package de.htwesports.wesports.posts

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
class PostsController(
        @Autowired
        private val postsRepository: PostsRepository,
        @Autowired
        private val userRepository: UserRepository
) {

    @GetMapping("/posts")
    fun showAllPosts(model: Model): ModelAndView {
        val user = userRepository.findByEmail(SecurityContextHolder.getContext()?.authentication?.name!!)!!
        val posts: List<Post> = postsRepository.findAll().filter { it.group == null }

        model.addAttribute("posts", posts)
        model.addAttribute("user", user)
        return ModelAndView("posts/index")
    }

    @GetMapping("/posts/{id}")
    fun showPost(@PathVariable id: Long, model: Model): ModelAndView {
        val user = userRepository.findByEmail(SecurityContextHolder.getContext()?.authentication?.name!!)!!
        val post: Optional<Post> = postsRepository.findById(id)

        if (post.isPresent) {
            model.addAttribute("post", post.get())
            model.addAttribute("has_post_edit_access", user == post.get().author)
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "post not found")
        }

        return ModelAndView("posts/show")
    }

    @GetMapping("/posts/new")
    @PreAuthorize("isAuthenticated()")
    fun newPost(model: Model): ModelAndView {
        model.addAttribute("post", PostDto())
        return ModelAndView("posts/new")
    }

    @PostMapping("/posts/new")
    @PreAuthorize("isAuthenticated()")
    fun createPost(@ModelAttribute("post") postDto: PostDto, model: Model): ModelAndView {
        val user = userRepository.findByEmail(SecurityContextHolder.getContext()?.authentication?.name!!)!!
        val post = Post(postDto.title!!, postDto.content!!, user, null)

        user.posts.add(post)
        postsRepository.save(post)

        model.addAttribute("post", post)
        return ModelAndView("redirect:/posts")
    }

    @GetMapping("/posts/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    fun editPost(@PathVariable id: Long, model: Model): ModelAndView {
        val user = userRepository.findByEmail(SecurityContextHolder.getContext()?.authentication?.name!!)!!
        val post: Optional<Post> = postsRepository.findById(id)

        if (post.isPresent) {
            if (user != post.get().author) {
                throw ResponseStatusException(HttpStatus.FORBIDDEN, "user not author of this post")
            }
            model.addAttribute("post", PostDto(post.get()))
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "post not found")
        }

        return ModelAndView("posts/edit")
    }

    @PostMapping("/posts/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    fun updatePost(@PathVariable id: Long, @ModelAttribute("post") postDto: PostDto, model: Model): ModelAndView {
        val user = userRepository.findByEmail(SecurityContextHolder.getContext()?.authentication?.name!!)!!
        val post: Optional<Post> = postsRepository.findById(id)

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
        return ModelAndView("redirect:/posts")
    }
}

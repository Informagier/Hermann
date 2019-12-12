package de.htwesports.wesports.users

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
internal class UserDetailsServiceIT {

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Test
    fun givenValidEmail_whenEmailExists_thenReturnsUserDetails() {
        val user = User("max.mustermann@example.com", passwordEncoder.encode("Test1234"))
        userRepository.save(user)
        val userDetails = MyUserPrincipal(user)
        assertEquals(userDetails.user.email,
                (userDetailsService.loadUserByUsername(user.email) as MyUserPrincipal).user.email)
    }

    @Test
    fun givenValidEmail_whenEmailNotExists_thenFails() {
        val user = User("max.mustermann@example.com", passwordEncoder.encode("Test1234"))
        assertThrows<UsernameNotFoundException> {
            userDetailsService.loadUserByUsername(user.email)
        }
    }
}

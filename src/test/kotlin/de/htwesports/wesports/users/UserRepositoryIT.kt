package de.htwesports.wesports.users

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
internal class UserRepositoryIT {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Test
    fun givenValidEmail_whenEmailExists_thenReturnsUser() {
        val user = User("max.mustermann@example.com", passwordEncoder.encode("Test1234"))
        userRepository.save(user)
        val found: User? = userRepository.findByEmail(user.email)
        assertNotNull(found)
    }

    @Test
    fun givenValidEmail_whenEmailNotExists_thenReturnsNull() {
        val user = User("max.mustermann@example.com", passwordEncoder.encode("Test1234"))
        val found: User? = userRepository.findByEmail(user.email)
        assertNull(found)
    }
}

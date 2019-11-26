package de.htwesports.wesports.users

import de.htwesports.wesports.errors.UserAlreadyExistsException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
internal class UserServiceIT {

    @TestConfiguration
    internal class UserServiceITConfig {
        @Bean
        fun userService(): UserService {
            return UserServiceImpl()
        }
    }

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Test
    fun givenFormData_whenEmailNotExists_thenUserShouldBeCreated() {
        val accountDto = UserDto("max.mustermann@example.com", "Test1234")
        val user = User("max.mustermann@example.com", passwordEncoder.encode("Test1234"))
        val found: User = userService.createUserAccount(accountDto)
        assertNotNull(found)
    }

    @Test
    fun givenFormData_whenEmailExists_thenUserShouldNotBeCreated() {
        val accountDto = UserDto("max.mustermann@example.com", "Test1234")
        userService.createUserAccount(accountDto)
        assertThrows<UserAlreadyExistsException> {
            userService.createUserAccount(accountDto)
        }
    }
}

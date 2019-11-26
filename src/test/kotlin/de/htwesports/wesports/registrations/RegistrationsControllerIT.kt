package de.htwesports.wesports.registrations

import de.htwesports.wesports.users.UserDto
import de.htwesports.wesports.users.UserService
import de.htwesports.wesports.users.UserServiceImpl
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
internal class RegistrationsControllerIT {

    @TestConfiguration
    internal class RegistrationsControllerITConfig {
        @Bean
        fun userService(): UserService {
            return UserServiceImpl()
        }
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var userService: UserService

    @Test
    fun givenRegisterPageURI_whenMockMVC_thenReturnsRegisterView() {
        this.mockMvc.get("/register").andDo { print() }.andExpect {
            status { isOk }
            model { attribute("title", "Register") }
            view { name("register") }
        }
    }

    @Test
    fun givenFormData_whenUserCreated_thenReturnsIndexView() {
        val accountDto = UserDto("max.mustermann@example.com", "Test1234")

        this.mockMvc.post("/register") {
            flashAttr("user", accountDto)
        }.andDo { print() }.andExpect {
            status { isOk }
            model { attribute("title", "Home") }
            view { name("index") }
        }
    }

    @Test
    fun givenFormData_whenUserAlreadyExists_thenReturnsRegisterView() {
        val accountDto = UserDto("admin@example.com", "Admin123")
        userService.createUserAccount(accountDto)

        this.mockMvc.post("/register") {
            flashAttr("user", accountDto)
        }.andDo { print() }.andExpect {
            status { isOk }
            model { attribute("title", "Register") }
            view { name("register") }
        }
    }
}

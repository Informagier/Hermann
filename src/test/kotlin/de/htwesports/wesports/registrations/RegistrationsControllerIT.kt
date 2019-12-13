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
    fun whenRequestRegisterPage_thenReturnStatus200() {
        this.mockMvc.get("/register")
                .andDo { print() }
                .andExpect {
                    status { isOk }
                }
    }

    @Test
    fun whenRequestRegisterPage_thenReturnRegisterView() {
        this.mockMvc.get("/register")
                .andDo { print() }
                .andExpect {
                    view { name("register") }
                }
    }

    @Test
    fun givenFormData_whenUserCreated_thenReturnStatus302() {
        val accountDto = UserDto("john.doe@example.com", "JohnDoe", "Password123")

        this.mockMvc.post("/register") { flashAttr("user", accountDto) }
                .andDo { print() }
                .andExpect {
                    status { isFound }
                }
    }

    @Test
    fun givenFormData_whenUserCreated_thenRedirectToHomePage() {
        val accountDto = UserDto("john.doe@example.com", "JohnDoe", "Password123")

        this.mockMvc.post("/register") { flashAttr("user", accountDto) }
                .andDo { print() }
                .andExpect {
                    view { name("redirect:/") }
                }
    }

    @Test
    fun givenFormData_whenUserAlreadyExists_thenReturnStatus200() {
        val accountDto = UserDto("john.doe@example.com", "JohnDoe", "Password123")
        userService.createUserAccount(accountDto)

        this.mockMvc.post("/register") { flashAttr("user", accountDto) }
                .andDo { print() }
                .andExpect {
                    status { isOk }
                }
    }

    @Test
    fun givenFormData_whenUserAlreadyExists_thenReturnRegisterView() {
        val accountDto = UserDto("john.doe@example.com", "JohnDoe", "Password123")
        userService.createUserAccount(accountDto)

        this.mockMvc.post("/register") { flashAttr("user", accountDto) }
                .andDo { print() }
                .andExpect {
                    view { name("register") }
                }
    }
}

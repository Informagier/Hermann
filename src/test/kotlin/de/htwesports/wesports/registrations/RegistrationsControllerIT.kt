package de.htwesports.wesports.registrations

import de.htwesports.wesports.errors.UserAlreadyExistsException
import de.htwesports.wesports.profile.Profile
import de.htwesports.wesports.users.User
import de.htwesports.wesports.users.UserDto
import de.htwesports.wesports.users.UserService
import de.htwesports.wesports.users.UserServiceImpl
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MockMvcBuilder
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.util.*


//@SpringBootTest
//@AutoConfigureMockMvc
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
internal class RegistrationsControllerIT {

    /*@TestConfiguration
    internal class RegistrationsControllerITConfig {
        @Bean
        fun userService(): UserService {
            return UserServiceImpl()
        }
    }*/
    private lateinit var mockMvc: MockMvc

    private lateinit var userService: UserService

    private lateinit var eventPublisher: ApplicationEventPublisher

    @BeforeEach
    fun setup(){
        userService = mock(UserService::class.java)
        eventPublisher = mock(ApplicationEventPublisher::class.java)
        val registrationsController = RegistrationsController(userService,eventPublisher)
        mockMvc = MockMvcBuilders.standaloneSetup(registrationsController).build()
    }

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
                    view { name("register.html") }
                }
    }

    @Test
    fun givenFormData_whenUserCreated_thenReturnStatus302() {
        val accountDto = UserDto("john.doe@example.com", "JohnDoe", "Password123")
        var profile= Profile()
        profile.username= accountDto.username
        val user = User(accountDto.email, "Test123", profile)
        Mockito.`when`(userService.createUserAccount(accountDto)).thenReturn(user)

        this.mockMvc.post("/register") { flashAttr("user", accountDto) }
                .andDo { print() }
                .andExpect {
                    status { isFound }
                }
    }

    @Test
    fun givenFormData_whenUserCreated_thenRedirectToHomePage() {
        val accountDto = UserDto("john.doe@example.com", "JohnDoe", "Password123")
        var profile= Profile()
        profile.username= accountDto.username
        val user = User(accountDto.email, "Test123", profile)
        `when`(userService.createUserAccount(accountDto)).thenReturn(user)

        this.mockMvc.post("/register") { flashAttr("user", accountDto) }
                .andDo { print() }
                .andExpect {
                    view { name("redirect:/") }
                }
    }

    @Test
    fun givenFormData_whenUserAlreadyExists_thenReturnStatus200() {
        val accountDto = UserDto("john.doe@example.com", "JohnDoe", "Password123")
        `when`(userService.createUserAccount(accountDto)).thenThrow(UserAlreadyExistsException::class.java)
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
                    view { name("register.html") }
                }
    }
    @Test
    fun confirmRegistrationTest(){
        var profile= Profile()
        val user = User("john.doe@example.com", "Test123")
        user.profile = profile
        var verificationToken = VerificationToken("testToken", user)
        `when`(userService.getVerificationToken(verificationToken.token)).thenReturn(verificationToken)
        mockMvc.perform(MockMvcRequestBuilders.get("/registrationConfirm.html?token=testToken"))
                .andExpect(status().is3xxRedirection)
                .andExpect(MockMvcResultMatchers.view().name("redirect:/"))
        assertTrue(user.enabled)
    }
    @Test
    fun confirmRegistrationTest_tokenExpired(){
        var profile= Profile()
        val user = User("john.doe@example.com", "Test123")
        user.profile = profile
        var verificationToken = VerificationToken("testToken", user)
        verificationToken.expiryDate = Date(Calendar.getInstance().time.time)
        `when`(userService.getVerificationToken(verificationToken.token)).thenReturn(verificationToken)

        mockMvc.perform(MockMvcRequestBuilders.get("/registrationConfirm.html?token=testToken"))
                .andExpect(status().is3xxRedirection)
                .andExpect(MockMvcResultMatchers.view().name("redirect:/badUser.html"))
        assertFalse(user.enabled)
    }

    @Test
    fun confirmRegistrationTest_tokenIsNull(){
        var profile= Profile()
        val user = User("john.doe@example.com", "Test123")
        user.profile = profile
        var verificationToken = VerificationToken("testToken", user)
        `when`(userService.getVerificationToken(verificationToken.token)).thenReturn(null)

        mockMvc.perform(MockMvcRequestBuilders.get("/registrationConfirm.html?token=testToken"))
                .andExpect(status().is3xxRedirection)
                .andExpect(MockMvcResultMatchers.view().name("redirect:/badUser.html"))
        assertFalse(user.enabled)
    }
    @Test
    fun goToIndexTest(){
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk)
                .andExpect(MockMvcResultMatchers.view().name("index"))
    }
}

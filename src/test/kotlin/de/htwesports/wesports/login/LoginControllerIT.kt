package de.htwesports.wesports.login

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
internal class LoginControllerIT {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun givenLoginPageURI_whenMockMVC_thenReturnsLoginView() {
        this.mockMvc.get("/login").andDo { print() }.andExpect {
            status { isOk }
            view { name("login") }
        }
    }

    @Test
    fun givenFormData_whenLoginSuccess_thenReturnsIndexView() {
        this.mockMvc.post("/perform_login") {
            param("username", "user@example.com")
            param("password", "user")
        }.andDo { print() }.andExpect {
            status { isFound }
        }
    }

    @Test
    fun givenFormData_whenLoginFails_thenReturnsLoginView() {
        this.mockMvc.post("/perform_login") {
            param("username", "max.mustermann@example.com")
            param("password", "Test1234")
        }.andDo { print() }.andExpect {
            status { isFound }
        }
    }
}

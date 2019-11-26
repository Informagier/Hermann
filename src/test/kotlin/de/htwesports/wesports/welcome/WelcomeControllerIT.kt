package de.htwesports.wesports.welcome

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
internal class WelcomeControllerIT {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun givenHomePageURI_whenMockMVC_thenReturnsIndexView() {
        this.mockMvc.get("/").andDo { print() }.andExpect {
            status { isOk }
            model { attribute("title", "Home") }
            view { name("index") }
        }
    }
}

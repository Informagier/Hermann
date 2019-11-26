package de.htwesports.wesports.about

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.time.LocalDateTime

@SpringBootTest
@AutoConfigureMockMvc
internal class AboutControllerIT {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun givenAboutPageURI_whenMockMVC_thenReturnsAboutView() {
        this.mockMvc.get("/about").andDo { print() }.andExpect {
            status { isOk }
            view { name("about") }
        }
    }
}

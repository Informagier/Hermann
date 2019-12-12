package de.htwesports.wesports.singleusetoken

import de.htwesports.wesports.users.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@ExtendWith(SpringExtension::class)
@SpringBootTest

internal class SingleUseTokenControllerIT{
    lateinit var mmvc : MockMvc
    @Autowired
    lateinit var userRepo : UserRepository



    lateinit var sutRepo : SingleUseTokenRepository


    @BeforeEach
    fun setup(){
        sutRepo = Mockito.mock(SingleUseTokenRepository::class.java)
        mmvc = MockMvcBuilders.standaloneSetup(sutRepo).build()
    }
    /*
    @Test
    @WithMockUser(username = "user@example.com")
    fun givenAProfileTheCreationOfASingleUseTokenShouldSucceed(){
        val user = userRepo.findByEmail("user@example.com")!!
        val profileUri = "/profiles/${user.profile?.uri!!}"
        val resultToken = SingleUseToken(user, "1234")
        val postUri = "${profileUri}/suts/new"
        Mockito.`when`(sutRepo.save(Mockito.any<SingleUseToken>())).thenReturn(resultToken)
        mmvc.perform(MockMvcRequestBuilders.post(postUri))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
                .andExpect(MockMvcResultMatchers.content().string(resultToken.token!!))
        Mockito.verify(sutRepo, Mockito.times(1)).save(Mockito.any())
    }
      */
}
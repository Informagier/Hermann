package de.htwesports.wesports.profile

import de.htwesports.wesports.users.User
import de.htwesports.wesports.users.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.web.server.ResponseStatusException
import kotlin.math.roundToInt


@ExtendWith(SpringExtension::class)
@SpringBootTest


internal class ProfileControllerIT{
    var mockMvc: MockMvc? = null
    private var profileRepository: ProfileRepository? = null
    private  var userRepository: UserRepository? = null

    @BeforeEach
    fun setup(){
        profileRepository = Mockito.mock(ProfileRepository::class.java)
        userRepository = Mockito.mock(UserRepository::class.java)
        val profileController = ProfileController(profileRepository!!,userRepository!!)
        mockMvc = MockMvcBuilders.standaloneSetup(profileController).build()
    }
    @Test
    fun goToProfile_returnsProfileFromRepository(){
        val random = (Math.random() * 100).roundToInt()
        val profile: Profile? = Profile(random.toLong(), "dragonslayer$random", "Skyrim$random","rpg$random","Berlin$random","archery$random")
        val profileUri =random.toString()
        profile!!.uri=profileUri
        Mockito.`when`(profileRepository!!.findByUri(profileUri)).thenReturn(profile)
        mockMvc!!.perform(get("/profiles/$profileUri"))
                .andExpect(model().attribute("profile", profile))
                .andExpect(view().name("profile"))
                .andExpect(status().is2xxSuccessful)
        verify(profileRepository)!!.findByUri(profileUri)
    }
    @Test
    fun goToProfile_returnsError_whenProfileNotFound() {
        val random = (Math.random() * 100).roundToInt().toString()
        Mockito.`when`(profileRepository!!.findByUri(random)).thenReturn(null)
        mockMvc!!.perform(get("/profiles/$random"))
                .andExpect(status().isNotFound)
        verify(profileRepository)!!.findByUri(random)
    }

    @Test
    @WithMockUser(username = "user@example.com")
    fun getProfile_returnsStatusSuccessful(){
        val random = (Math.random() * 100).roundToInt().toString()
        val profile: Profile? = Profile(random.toLong(), "dragonslayer$random", "Skyrim$random","rpg$random","Berlin$random","archery$random")
        profile!!.uri=random

        val user = User("user@example.com","Test6372",profile)
        Mockito.`when`(userRepository!!.findByEmail(user.email)).thenReturn(user)
        mockMvc!!.perform(get("/profiles/profile"))
                .andExpect(status().is3xxRedirection)
        verify(userRepository)!!.findByEmail("user@example.com")
    }
    @Test
    fun getProfile_returnsStatusAccessDenied(){
        mockMvc!!.perform(get("/profiles/profile"))
                .andExpect(status().isForbidden)
    }
    @Test
    @WithMockUser(username = "user@example.com")
    fun getProfile_profileNotInRepository(){
        mockMvc!!.perform(get("/profiles/profile"))
                .andExpect(status().is4xxClientError)
    }

}
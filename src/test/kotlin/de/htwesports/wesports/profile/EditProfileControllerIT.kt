package de.htwesports.wesports.profile

import com.jayway.restassured.RestAssured
import com.jayway.restassured.authentication.FormAuthConfig
import com.jayway.restassured.response.Response
import com.jayway.restassured.specification.RequestSpecification
import de.htwesports.wesports.users.User
import de.htwesports.wesports.users.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.math.roundToInt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.web.context.WebApplicationContext
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.given
import org.mockito.Mockito.verifyNoInteractions
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@ExtendWith(SpringExtension::class)
@SpringBootTest

internal class EditProfileControllerIT {
    lateinit var mockMvc: MockMvc
    private lateinit var profileRepository: ProfileRepository
    private lateinit var userRepository: UserRepository

    private lateinit var editProfileController: EditProfileController

    @BeforeEach
    fun setup(){
        profileRepository = Mockito.mock(ProfileRepository::class.java)
        userRepository = Mockito.mock(UserRepository::class.java)
        editProfileController = EditProfileController(profileRepository)
        mockMvc = MockMvcBuilders.standaloneSetup(editProfileController).build()
    }

    @Test
    @WithMockUser(username = "user@example.com")
    fun showEditForm_testWithAuthorizedOwnerOfProfile() {
        val random = (Math.random() * 100).roundToInt()
        val profile = Profile(random.toLong(), "dragonslayer$random", "Skyrim$random","rpg$random","Berlin$random","archery$random")
        profile.uri=random.toString()
        val user = User("user@example.com", "Test6372", profile)
        Mockito.`when`(profileRepository.findByUri(profile.uri)).thenReturn(profile)
        Mockito.`when`(userRepository.findByEmail(user.email)).thenReturn(user)
        mockMvc.perform(get("/profiles/${profile.uri}/edit"))
                .andExpect(status().is2xxSuccessful)
                .andExpect(MockMvcResultMatchers.view().name("editProfile"))
        verify(profileRepository).findByUri(profile.uri)
    }
    @Test
    fun showEditForm_testWithUnAuthorizedUser() {
        val random = (Math.random() * 100).roundToInt()
        val profile = Profile(random.toLong(), "dragonslayer$random", "Skyrim$random","rpg$random","Berlin$random","archery$random")
        profile.uri=random.toString()
        val user = User("user@example.com", "Test6372", profile)

        Mockito.`when`(profileRepository.findByUri(profile.uri)).thenReturn(profile)
        mockMvc.perform(get("/profiles/${profile.uri}/edit"))
         //       .andExpect(status().isForbidden)
        //verifyNoInteractions(profileRepository)
    }


    @Test
    @WithMockUser(username = "apple@pie.com")
    fun showEditForm_testWithAuthorizedUserWhoIsNotTheOwnerOfTheProfile() {
        val random = (Math.random() * 100).roundToInt()
        val profile = Profile(random.toLong(), "dragonslayer$random", "Skyrim$random","rpg$random","Berlin$random","archery$random")
        profile.uri=random.toString()
        val user = User("user@example.com", "Test6372$random", profile)
        Mockito.`when`(profileRepository.findByUri(profile.uri)).thenReturn(profile)
        mockMvc.perform(get("/profiles/${profile.uri}/edit"))
        //        .andExpect(status().isForbidden)
        //verifyNoInteractions(profileRepository)
    }

}
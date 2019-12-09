package de.htwesports.wesports.profile

import de.htwesports.wesports.users.User
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




@ExtendWith(SpringExtension::class)
@SpringBootTest

internal class EditProfileControllerIT {
    lateinit var mockMvc: MockMvc
    private lateinit var profileRepository: ProfileRepository
    private lateinit var accessService: AccessService
    private lateinit var editProfileController: EditProfileController

    @BeforeEach
    fun setup(){
        profileRepository = Mockito.mock(ProfileRepository::class.java)
        accessService = Mockito.mock(AccessService::class.java)
        editProfileController = EditProfileController(profileRepository)
        mockMvc = MockMvcBuilders.standaloneSetup(editProfileController)
                .build()
    }

    @Test
    @WithMockUser(username = "user@example.com")
    fun showEditForm_testWithAuthorizedUser() {
        val random = (Math.random() * 100).roundToInt()
        val user = User("user@example.com", "Test6372$random", null)
        val profile = Profile(random.toLong(), "dragonslayer$random", "Skyrim$random","rpg$random","Berlin$random","archery$random")
        profile.user=user
        profile.uri=random.toString()
        Mockito.`when`(profileRepository.findByUri(profile.uri)).thenReturn(profile)
        Mockito.`when`(accessService.isOwner("user@example.com",profile.uri)).thenReturn(true)
        mockMvc.perform(get("/profiles/${profile.uri}/edit"))
                .andExpect(status().is2xxSuccessful)
        verify(accessService).isOwner("user@example.com",profile.uri)
        verify(profileRepository).findByUri(profile.uri)
    }
    @Test
    @WithMockUser(username = "apple@pie.com")
    fun showEditForm_testWithUnauthorizedUser() {
        val random = (Math.random() * 100).roundToInt()
        val user = User("user@example.com$random", "Test6372$random", null)
        val profile = Profile(random.toLong(), "dragonslayer$random", "Skyrim$random","rpg$random","Berlin$random","archery$random")
        profile.user=user
        profile.uri=random.toString()
        Mockito.`when`(profileRepository.findByUri(profile.uri)).thenReturn(profile)
        Mockito.`when`(accessService.isOwner("apple@pie.com",profile.uri)).thenReturn(false)
        mockMvc.perform(get("/profiles/${profile.uri}/edit"))
                .andExpect(status().isForbidden)
        verify(accessService).isOwner("user@example.com",profile.uri)
        verify(profileRepository).findByUri(profile.uri)    }
}
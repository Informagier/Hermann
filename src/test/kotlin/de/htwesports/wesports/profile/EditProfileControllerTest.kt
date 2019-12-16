package de.htwesports.wesports.profile


import de.htwesports.wesports.users.User
import de.htwesports.wesports.users.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.math.roundToInt
import org.mockito.Mockito.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


internal class EditProfileControllerTest {
    lateinit var mockMvc: MockMvc
    private lateinit var profileRepository: ProfileRepository
    private lateinit var userRepository: UserRepository

    private lateinit var editProfileController: EditProfileController

    @BeforeEach
    fun setup(){
        profileRepository = mock(ProfileRepository::class.java)
        userRepository = mock(UserRepository::class.java)
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
        `when`(profileRepository.findByUri(profile.uri)).thenReturn(profile)
        `when`(userRepository.findByEmail(user.email)).thenReturn(user)
        mockMvc.perform(get("/profiles/${profile.uri}/edit"))
                .andExpect(status().is2xxSuccessful)
                .andExpect(MockMvcResultMatchers.view().name("editProfile"))
        verify(profileRepository).findByUri(profile.uri)
    }
}
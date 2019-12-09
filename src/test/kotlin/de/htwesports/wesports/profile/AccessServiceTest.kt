package de.htwesports.wesports.profile

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import de.htwesports.wesports.users.User
import kotlin.math.roundToInt

internal class AccessServiceTest {

    private lateinit var profileRepository: ProfileRepository
    private lateinit var accessService: AccessService
    private lateinit var mockMvc : MockMvc

    @BeforeEach
    fun setup(){
        profileRepository = Mockito.mock(ProfileRepository::class.java)
        accessService = AccessService(profileRepository)
        mockMvc = MockMvcBuilders.standaloneSetup(accessService).build()
    }
    @Test
    fun isOwner_testWithActualOwner() {
        val random = (Math.random() * 100).roundToInt()
        val user = User("user@example.com$random", "Test6372$random", null)
        val profile = Profile(random.toLong(), "dragonslayer$random", "Skyrim$random","rpg$random","Berlin$random","archery$random")
        profile.user=user
        profile.uri=random.toString()
        Mockito.`when`(profileRepository.findByUri(profile.uri)).thenReturn(profile)
        assertTrue(accessService.isOwner(user.email,profile.uri))
    }
    @Test
    fun isOwner_testWithUnauthorizedUser(){
        val random = (Math.random() * 100).roundToInt()
        val user = User("user@example.com$random", "Test6372$random", null)
        val profile = Profile(random.toLong(), "dragonslayer$random", "Skyrim$random","rpg$random","Berlin$random","archery$random")
        profile.user=user
        profile.uri=random.toString()
        Mockito.`when`(profileRepository.findByUri(profile.uri)).thenReturn(profile)
        assertFalse(accessService.isOwner("lemon@cheesecake.de",profile.uri))
    }
    @Test
    fun isOwner_testWithNonExistingUri(){
        val random = (Math.random() * 100).roundToInt()
        val user = User("user@example.com$random", "Test6372$random", null)
        val profile = Profile(random.toLong(), "dragonslayer$random", "Skyrim$random","rpg$random","Berlin$random","archery$random")
        profile.user=user
        profile.uri=random.toString()
        Mockito.`when`(profileRepository.findByUri(profile.uri)).thenReturn(profile)
        assertFalse(accessService.isOwner(user.email,(random+3).toString()))
    }
}
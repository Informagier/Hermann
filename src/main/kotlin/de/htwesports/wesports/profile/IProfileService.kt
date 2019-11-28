package de.htwesports.wesports.profile

interface IProfileService {
    fun saveProfile( profileDto: ProfileDto, existingProfile: Profile)

}

package de.htwesports.wesports.profile

import org.springframework.beans.factory.annotation.Autowired
import javax.transaction.Transactional
import org.springframework.stereotype.Service


@Service
class ProfileService : IProfileService {
    @Autowired
    private lateinit var profileRepository: ProfileRepository

    @Transactional
    override fun saveProfile(profileDto: ProfileDto, existingProfile: Profile){
        existingProfile.game = profileDto.game
        existingProfile.date = profileDto.date
        existingProfile.genre = profileDto.genre
        existingProfile.city = profileDto.city
        existingProfile.hobby = profileDto.hobby
        //existingProfile.photo = profileDto.photo

        profileRepository.save(existingProfile)
    }
}

package de.htwesports.wesports.users

import de.htwesports.wesports.errors.UserAlreadyExistsException
import de.htwesports.wesports.profile.Profile
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder

@Service
@Transactional
class UserServiceImpl : UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Throws(UserAlreadyExistsException::class)
    override fun createUserAccount(accountDto: UserDto): User {
        if (emailExists(accountDto.email)) {
            throw UserAlreadyExistsException("There is an account with that email address: " + accountDto.email)
        }
        var profile= Profile()
        profile.username= accountDto.username
        val user = User(accountDto.email, passwordEncoder.encode(accountDto.password), profile)
        return userRepository.save(user)
    }

    private fun emailExists(email: String): Boolean {
        return userRepository.findByEmail(email) != null
    }
}

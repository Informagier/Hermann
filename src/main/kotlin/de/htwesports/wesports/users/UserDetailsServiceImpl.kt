package de.htwesports.wesports.users

import de.htwesports.wesports.roles.Role
import de.htwesports.wesports.roles.RoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import javax.transaction.Transactional
import org.springframework.security.core.userdetails.UsernameNotFoundException

@Service("userDetailsService")
@Transactional
class UserDetailsServiceImpl : UserDetailsService {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var roleRepository: RoleRepository

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email)
        if (user != null) {
            return org.springframework.security.core.userdetails.User(
                    user.email, user.password, getAuthorities(user.roles!!))
        }else {
            throw UsernameNotFoundException("No user found with username: $email")
        }
    }

    private fun getAuthorities(roles: Collection<Role>): Collection<GrantedAuthority> =
            getGrantedAuthorities(getPrivileges(roles))

    private fun getPrivileges(roles: Collection<Role>): List<String> =
            roles.flatMap { role -> role.privileges }
                    .map { privilege -> privilege.name }

    private fun getGrantedAuthorities(privileges: List<String>): List<GrantedAuthority> =
            privileges.map { privilege -> SimpleGrantedAuthority(privilege) }
}

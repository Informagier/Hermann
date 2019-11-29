package de.htwesports.wesports.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.authentication.dao.DaoAuthenticationProvider

@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
        auth.inMemoryAuthentication()
                .withUser("user@example.com").password(passwordEncoder().encode("user")).roles("USER").and()
                .withUser("admin@example.com").password(passwordEncoder().encode("admin")).roles("ADMIN")
    }

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/h2-console/**").permitAll() // Only local database
                .antMatchers("/webjars/**").permitAll() // Bootstrap, jQuery
                .antMatchers("/static/**").permitAll() // Static resources
                .antMatchers("/*").permitAll() // Allow everything on root
                .anyRequest().authenticated()
                .and()

                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/perform_login")
                .permitAll()
                .and()

                .logout()
                .logoutUrl("/perform_logout")
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()

                .httpBasic()
                .and()

                .headers().frameOptions().disable()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService)
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }
}

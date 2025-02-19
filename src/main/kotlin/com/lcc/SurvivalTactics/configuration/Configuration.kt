package com.lcc.SurvivalTactics.configuration

import com.lcc.SurvivalTactics.repositories.UserRepository
import com.lcc.SurvivalTactics.service.CustpmUserDetailsService
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class Configuration {

    @Bean
    fun userDetailsService(userRepository: UserRepository): UserDetailsService {
        return CustpmUserDetailsService(userRepository)
    }

    @Bean
    fun passwordEncoded(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun aunthenticationProvider(userRepository: UserRepository): AuthenticationProvider {
        return DaoAuthenticationProvider().also {
            it.setUserDetailsService(userDetailsService(userRepository))
            it.setPasswordEncoder(passwordEncoded())
        }
    }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }
}
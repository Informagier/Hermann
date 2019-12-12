package de.htwesports.wesports.config

import de.htwesports.wesports.profile.CustomMethodSecurityExpressionHandler
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
class MethodSecurityConfig : GlobalMethodSecurityConfiguration(){

    protected override fun createExpressionHandler(): MethodSecurityExpressionHandler {
        val expressionHandler = CustomMethodSecurityExpressionHandler()
        expressionHandler.setPermissionEvaluator(CustomPermissionEvaluator())
        return expressionHandler
    }
}
package de.htwesports.wesports.profile

import org.aopalliance.intercept.MethodInvocation
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations
import org.springframework.security.authentication.AuthenticationTrustResolverImpl
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler
import org.springframework.security.core.Authentication


class CustomMethodSecurityExpressionHandler : DefaultMethodSecurityExpressionHandler() {
    private val trustResolver = AuthenticationTrustResolverImpl()

    override fun createSecurityExpressionRoot(
            authentication: Authentication, invocation: MethodInvocation): MethodSecurityExpressionOperations {
        val root = CustomMethodSecurityExpressionRoot(authentication)
        root.setPermissionEvaluator(permissionEvaluator)
        root.setTrustResolver(trustResolver)
        root.setRoleHierarchy(roleHierarchy)
        return root
    }
}
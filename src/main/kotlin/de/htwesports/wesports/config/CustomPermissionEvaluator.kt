package de.htwesports.wesports.config

import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.core.Authentication
import java.io.Serializable

class CustomPermissionEvaluator : PermissionEvaluator {
    override fun hasPermission(authentication: Authentication?, targetDomainObject: Any?, permission: Any): Boolean {
        if((authentication == null) || (targetDomainObject == null) || (permission !is String)) {
            return false
        }
        val targetType = targetDomainObject.javaClass.simpleName.toUpperCase()

        return hasPrivilege(authentication, targetType, permission.toString().toUpperCase())
    }

    override fun hasPermission(authentication: Authentication?, targetId: Serializable?, targetType: String?, permission: Any?): Boolean {
        if((authentication == null) || (targetType == null) || (permission !is String)){
            return false
        }
        return hasPrivilege(authentication,targetType.toUpperCase(),permission.toString().toUpperCase())
    }
    private fun hasPrivilege(authentication: Authentication, targetType: String, permission: String): Boolean {
        authentication.authorities.forEach{
            if(it.authority.startsWith(targetType)){
                if(it.authority.contains(permission)){
                    return true
                }
            }
        }
        return false
    }
}
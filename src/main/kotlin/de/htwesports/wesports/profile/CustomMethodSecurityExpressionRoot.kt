package de.htwesports.wesports.profile

import de.htwesports.wesports.users.MyUserPrincipal
import org.springframework.security.access.expression.SecurityExpressionRoot
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations
import org.springframework.security.core.Authentication


class CustomMethodSecurityExpressionRoot(authentication: Authentication) : SecurityExpressionRoot(authentication), MethodSecurityExpressionOperations {

    private var filterObject: Any? = null

    private var returnObject: Any? = null


    fun isOwner(uri: String): Boolean{
        var myUserPrincipal: MyUserPrincipal = principal as MyUserPrincipal
        val userUri = myUserPrincipal.getProfile()?.uri
        //println("UserUri:$userUri")
        if(userUri == null || userUri != uri){
            return false
        }
        return true    }


    override fun setFilterObject(filterObject: Any) {
        this.filterObject=filterObject
    }

    override fun getFilterObject(): Any? {
        return filterObject
    }

    override fun setReturnObject(returnObject: Any) {
        this.returnObject = returnObject
    }

    override fun getReturnObject(): Any? {
        return this.returnObject
    }

    override fun getThis(): Any? {
        return this
    }
}
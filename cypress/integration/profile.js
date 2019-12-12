"use strict"

describe('Profile Tests', function() {
    var passwd = 'Who_uses_1234_as_their_password?'
    var uname = 'profileTestUser'
    var email = 'profileTestUser@example.com'

    //register a user
    before(function() {
        cy.request('POST', '/register', {
            username: uname,
            email: email,
            password: passwd
        })
    })
    //login
    beforeEach(function() {
        cy.request('POST', '/perform_login', {
            username: email,
            password: passwd
        })
    })

    it('create singleUseToken', function(){
        cy.visit('/profiles/profile')
        cy.get('.single_use_token')
            .find('button')
            .click()

        cy.get('.single_use_token')
            .find('.token')
            .should((token) => {
                var text = token.text()
                return text.length() == 38
            })
    })
})

"use strict"

describe('Profile Tests', function() {
    var passwd = 'Who_uses_1234_as_their_password?'
    var uname = 'profileTestUser'
    var email = 'profileTestUser@example.com'

    //register a user
    before(function() {
        cy.visit('/register');

        cy.get("#email").type(email)
        cy.get("#username").type(uname)
        cy.get("#password").type(passwd)
        cy.get("#matchingPassword").type(passwd)

        cy.get("#submitBtn").click();
    })
    //login
    beforeEach(function() {
        cy.visit('/login');

        cy.get("#username").type(email);
        cy.get("#password").type(passwd);
        cy.get("#submitBtn").click();
    })

    it('create singleUseToken', function(){
        cy.visit('/profiles/profile')
        cy.get('.single-use-token')
            .find('button')
            .click()

        cy.get('.single-use-token')
            .find('.token')
            .should((token) => {
                var text = token.text()
                assert.equal(36, text.length, "lol")
            })
    })
})

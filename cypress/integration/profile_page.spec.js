describe('test for profile page', function() {
    it('Visits the Profile Page', function() {
        cy.visit("/")

        cy.request({
            method: 'POST',
            url: '/register',
            form: true,
            body: {
                username: 'applePie',
                email: 'apple.pie@web.de',
                password: 'applePie2019!',
                matchingPassword: 'applePie2019!'
            }
        })
        cy.request({
            method: 'POST',
            url: '/perform_login',
            form: true,
            body: {
                username: 'apple.pie@web.de',
                password: 'applePie2019!'
            }
        })

        cy.get('.navbar-nav > :nth-child(5) > .nav-link').click();

        cy.get("#edit").click();
        cy.get("#game").type("Skyrim").should("have.value", "Skyrim");
        cy.get("#genre").type("rpg").should("have.value", "rpg");
        cy.get("#city").type("Berlin").should("have.value", "Berlin");
        cy.get("#hobby").type("Archery").should("have.value", "Archery");
        cy.get("#submitBtn").click();
        cy.get("#gameField").should("have.text", "Skyrim");
        cy.get("#genreField").should("have.text", "rpg");
        cy.get("#cityField").should("have.text", "Berlin");
        cy.get("#hobbyField").should("have.text", "Archery");


    })
})
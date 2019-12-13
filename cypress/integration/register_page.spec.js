describe("Given a new user wants to register", function() {
    describe("When the typed password length is lower than 8 chars", function () {
        it("Then the user cannot register", function() {
            cy.visit("/");

            cy.get('.navbar-nav > :nth-child(2) > .nav-link').click();

            cy.url().should('include', '/register');

            cy.get("#username").type("JohnDoe").should("have.value", "JohnDoe");
            cy.get("#email").type("john.doe@example.com").should("have.value", "john.doe@example.com");
            cy.get("#password").type("Test123").should("have.value", "Test123");
            cy.get("#matchingPassword").type("Test123").should("have.value", "Test123");

            cy.get("#submitBtn").should('be.disabled');
        });
    });

    describe("When the typed password contains no uppercase chars", function () {
        it("Then the user cannot register", function() {
            cy.visit("/");

            cy.get('.navbar-nav > :nth-child(2) > .nav-link').click();

            cy.url().should('include', '/register');

            cy.get("#username").type("JohnDoe").should("have.value", "JohnDoe");
            cy.get("#email").type("john.doe@example.com").should("have.value", "john.doe@example.com");
            cy.get("#password").type("password123").should("have.value", "password123");
            cy.get("#matchingPassword").type("password123").should("have.value", "password123");

            cy.get("#submitBtn").should('be.disabled');
        });
    });

    describe("When the typed password contains no lowercase chars", function () {
        it("Then the user cannot register", function() {
            cy.visit("/");

            cy.get('.navbar-nav > :nth-child(2) > .nav-link').click();

            cy.url().should('include', '/register');

            cy.get("#username").type("JohnDoe").should("have.value", "JohnDoe");
            cy.get("#email").type("john.doe@example.com").should("have.value", "john.doe@example.com");
            cy.get("#password").type("PASSWORD123").should("have.value", "PASSWORD123");
            cy.get("#matchingPassword").type("PASSWORD123").should("have.value", "PASSWORD123");

            cy.get("#submitBtn").should('be.disabled');
        });
    });

    describe("When the typed password contains no numbers", function () {
        it("Then the user cannot register", function() {
            cy.visit("/");

            cy.get('.navbar-nav > :nth-child(2) > .nav-link').click();

            cy.url().should('include', '/register');

            cy.get("#username").type("JohnDoe").should("have.value", "JohnDoe");
            cy.get("#email").type("john.doe@example.com").should("have.value", "john.doe@example.com");
            cy.get("#password").type("Password").should("have.value", "Password");
            cy.get("#matchingPassword").type("Password").should("have.value", "Password");

            cy.get("#submitBtn").should('be.disabled');
        });
    });

    describe("When the typed password contains whitespace chars", function () {
        it("Then the user cannot register", function() {
            cy.visit("/");

            cy.get('.navbar-nav > :nth-child(2) > .nav-link').click();

            cy.url().should('include', '/register');

            cy.get("#username").type("JohnDoe").should("have.value", "JohnDoe");
            cy.get("#email").type("john.doe@example.com").should("have.value", "john.doe@example.com");
            cy.get("#password").type("Password 123").should("have.value", "Password 123");
            cy.get("#matchingPassword").type("Password 123").should("have.value", "Password 123");

            cy.get("#submitBtn").should('be.disabled');
        });
    });
});

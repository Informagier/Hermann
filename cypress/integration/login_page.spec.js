describe("Given a user wants to log in", function() {
    describe("When the user is not registered", function () {
        it("Then the user cannot log in", function() {
            cy.visit("/");

            cy.get('.navbar-nav > :nth-child(3) > div > .nav-link').click();

            cy.url().should('include', '/login');

            cy.get("#username").type("jane.doe@example.com").should("have.value", "jane.doe@example.com");
            cy.get("#password").type("Password123").should("have.value", "Password123");

            cy.get("#submitBtn").click();

            cy.url().should('eq', 'http://localhost:8080/login?error');

            cy.get('.navbar-nav > :nth-child(3) > div > .nav-link').should("have.text", "Log in");
        });
    });
});

describe("Given a new user wants to register", function() {
    describe("When the user completes the registration correctly", function () {
        it("Then the user should be registered and can log in successfully", function() {
            cy.visit("/");

            cy.get('.navbar-nav > :nth-child(2) > .nav-link').click();

            cy.url().should('include', '/register');

            cy.get("#username").type("JohnDoe").should("have.value", "JohnDoe");
            cy.get("#email").type("john.doe@example.com").should("have.value", "john.doe@example.com");
            cy.get("#password").type("Password123").should("have.value", "Password123");
            cy.get("#matchingPassword").type("Password123").should("have.value", "Password123");

            cy.get("#submitBtn").click();

            cy.url().should('eq', 'http://localhost:8080/?succMessage=You+registered+successfully.+We+will+send+you+a+confirmation+message+to+your+email+account.');

            cy.visit("/enable/john.doe@example.com");
            cy.get('.navbar-nav > :nth-child(3) > div > .nav-link').click();

            cy.url().should('include', '/login');

            cy.get("#username").type("john.doe@example.com").should("have.value", "john.doe@example.com");
            cy.get("#password").type("Password123").should("have.value", "Password123");

            cy.get("#submitBtn").click();

            cy.url().should('eq', 'http://localhost:8080/');
        });
    });
});

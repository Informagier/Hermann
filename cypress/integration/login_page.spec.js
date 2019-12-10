describe("Given the Login page", function() {
    describe("When filling in the form", function () {
        it("Then should fail and redirect to the Login page", function() {
            cy.visit("/");

            cy.get('.navbar-nav > :nth-child(3) > div > .nav-link').click();

            cy.url().should('include', '/login');

            cy.get("#username").type("max.mustermann@example.com").should("have.value", "max.mustermann@example.com");
            cy.get("#password").type("Test1234").should("have.value", "Test1234");

            cy.get("#submitBtn").click();

            cy.url().should('eq', 'http://localhost:8080/login?error');

            cy.get('.navbar-nav > :nth-child(3) > div > .nav-link').should("have.text", "Log in");
        });
    });
});

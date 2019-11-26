describe("Given the Register page", function() {
    describe("When filling in the form", function () {
        it("Then should create an user account and redirect to the Home page", function() {
            cy.visit("/");

            cy.get(':nth-child(2) > .nav-link').click();

            cy.url().should('include', '/register');

            cy.get("#email").type("max.mustermann@example.com").should("have.value", "max.mustermann@example.com");
            cy.get("#password").type("Test1234").should("have.value", "Test1234");
            cy.get("#matchingPassword").type("Test1234").should("have.value", "Test1234");

            cy.get("#submitBtn").click();

            cy.url().should('include', '/')
        });
    });
});

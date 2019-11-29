const todaysDate = Cypress.moment().format('DD/MMM/YYYY HH:mm');

describe("Given the About page", function() {
    it("should load the about page and display the current date and time", function() {
        cy.visit("/");

        cy.get('.navbar-nav > :nth-child(1) > .nav-link').click();

        cy.url().should('include', '/about');

        cy.get(".date strong").should("have.text", todaysDate);
    });
});

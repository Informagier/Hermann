const todaysDate = Cypress.moment().format('DD/MMM./YYYY HH:mm');

describe('About page test', function() {
    it('should load the about page and display current date and time', function() {
        cy.visit('localhost:8080/about');
        cy.get('.date strong').should('have.text', todaysDate);
    });
});

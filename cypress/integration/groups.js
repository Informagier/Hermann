
function login(){
    cy.request({
        method: 'POST',
        url: '/register',
        form: true,
        body: {
            username: 'REEEEEEEE',
            email: 'tester@example.com',
            password: 'applePie2019!',
            matchingPassword: 'applePie2019!'
        }
    })
    cy.request({
        method: 'POST',
        url: '/perform_login',
        form: true,
        body: {
            username: 'tester@example.com',
            password: 'applePie2019!'
        }
    });
}

function login2() {
    cy.request({
        method: 'POST',
        url: '/register',
        form: true,
        body: {
            username: 'testuser',
            email: 'tester2@example.com',
            password: 'Test1234',
            matchingPassword: 'Test1234'
        }
    });
    cy.request({
        method: 'POST',
        url: '/perform_login',
        form: true,
        body: {
            username: 'tester2@example.com',
            password: 'Test1234'
        }
    });
}

describe('test for Group pages', function() {
    it('Create a new Group', function() {
        login();
        cy.visit('/');

        cy.get('.navbar-nav > :nth-child(5) > div >.nav-link').click();

        cy.get('a').contains('Create new Group').click();
        cy.get('#name').type('Dis is a Tescht Group');
        cy.get('#submitBtn').click();

        cy.url().as('currentURL').then(currentURL => {
            cy.log(currentURL);
        });

        cy.get('#edit').click();

        let about = "WE ARE NUMBER ONE";
        let game = "test123";
        let city = "Berlin";

        cy.get('#about').type(about);
        cy.get('#game').type(game);
        cy.get('#city').type(city);

        cy.get('#submitBtn').click();

        cy.get('@currentURL').then(currentURL => {
            cy.url().should('eq', currentURL);
        });
    });

    it('Adds a user to the group', function() {
        cy.get('.navbar-nav > :nth-child(3) > div > .nav-link').click();
        login2();
        cy.visit('/');

        cy.get('.navbar-nav > :nth-child(6) > .nav-link').click();

        cy.get('.single-use-token').find('button').click();

        cy.get('.single-use-token').find('.token').invoke('text').as('token');

        cy.get('.navbar-nav > :nth-child(3) > div > .nav-link').click();
        login();
        cy.visit('/');

        cy.get('.navbar-nav > :nth-child(5) > div > .nav-link').click();

        cy.get('.groupButton').contains('Dis is a Tescht Group').click();

        cy.get('@token').then(token => {
            cy.get('#token').type(token);
        });
        cy.get('#addUserBtn').click();

        cy.get('.navbar-nav > :nth-child(3) > div > .nav-link').click();
        login2();
        cy.visit('/');

        cy.get('.navbar-nav > :nth-child(5) > div >.nav-link').click();

        cy.get('.groupButton').contains('Dis is a Tescht Group').click();

        cy.get('a').contains('testuser').click();
    });
});
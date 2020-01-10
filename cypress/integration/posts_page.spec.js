function register() {
    cy.request({
        method: 'POST',
        url: '/register',
        form: true,
        body: {
            username: 'testuser',
            email: 'test@example.com',
            password: 'Test1234',
            matchingPassword: 'Test1234'
        }
    });
}

function login() {
    cy.request({
        method: 'POST',
        url: '/perform_login',
        form: true,
        body: {
            username: 'test@example.com',
            password: 'Test1234'
        }
    });
}

describe("Given a user wants to publish a post", function () {
    describe("When filling in all required fields", function () {
        it("Then should create the post and redirect the user to the post page", function () {
            cy.visit('/');

            register();
            login();

            cy.url().should('include', '/');

            cy.get('.navbar-nav > :nth-child(7) > .nav-link').click();
            cy.url().should('include', '/posts');

            cy.get('#createPostBtn').click();
            cy.url().should('include', '/posts/new');

            let title = "Test title";
            let content = "Lorem ipsum";

            cy.get('#title').type(title).should('have.value', title);
            cy.get('#content').type(content).should('have.value', content);

            cy.get('#submitBtn').click();

            cy.url().should('include', '/posts/');

            cy.get('.card-title').should('have.text', title);
            cy.get('.card-text').should('have.text', content);
        });
    });
});

describe("Given a user wants to edit a post", function () {
    describe("When user is the author", function () {
        it("Then should edit the post and redirect the user to the updated post page", function () {
            cy.visit('/');

            register();
            login();

            cy.url().should('include', '/');

            cy.get('.navbar-nav > :nth-child(7) > .nav-link').click();
            cy.url().should('include', '/posts');

            cy.get('.showPostBtn').first().click();
            cy.url().should('include', '/posts/');

            cy.get('#editPostBtn').click();
            cy.url().should('include', '/edit');

            let title = "Test title #2";
            let content = "Test 123";

            cy.get('#title').clear().type(title).should('have.value', title);
            cy.get('#content').clear().type(content).should('have.value', content);

            cy.get('#submitBtn').click();

            cy.url().should('include', '/posts/');

            cy.get('.card-title').should('have.text', title);
            cy.get('.card-text').should('have.text', content);
        });
    });
});

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
    cy.visit("/enable/test@example.com");
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

function create_group() {
    cy.request({
        method: 'POST',
        url: '/groups/new',
        form: true,
        body: {
            name: 'Test group'
        }
    });
}

describe("Given a user wants to publish a post in a group", function () {
    describe("When filling in all required fields", function () {
        it("Then should create the post and redirect the user to the group post page", function () {
            cy.visit('/');

            register();
            login();
            create_group();

            cy.visit('/');
            cy.url().should('include', '/');

            cy.get('.navbar-nav > :nth-child(5) > div > .nav-link').click();
            cy.url().should('include', '/groups');

            cy.get('.groupButton').first().click();

            cy.get('#showPostsBtn').click();
            cy.url().should('include', '/posts');

            cy.get('#createPostBtn').click();
            cy.url().should('include', '/posts/new');

            let title = "Test title";
            let content = "Lorem ipsum";

            cy.get('#title').type(title).should('have.value', title);
            cy.get('#content').type(content).should('have.value', content);

            cy.get('#submitBtn').click();

            cy.url().should('include', '/posts');
        });
    });
});

describe("Given a user wants to edit a group post", function () {
    describe("When user is the author", function () {
        it("Then should edit the post and redirect the user to the updated post page", function () {
            cy.visit('/');

            register();
            login();
            create_group();

            cy.visit('/');
            cy.url().should('include', '/');

            cy.get('.navbar-nav > :nth-child(5) > div > .nav-link').click();
            cy.url().should('include', '/groups');

            cy.get('.groupButton').first().click();

            cy.get('#showPostsBtn').click();
            cy.url().should('include', '/posts');

            cy.get('#editPostBtn').click();
            cy.url().should('include', '/edit');

            let title = "Test title 2";
            let content = "Lorem ipsum 2";

            cy.get('#title').clear().type(title).should('have.value', title);
            cy.get('#content').clear().type(content).should('have.value', content);

            cy.get('#submitBtn').click();

            cy.url().should('include', '/posts');
        });
    });
});

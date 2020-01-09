
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
    })
}

describe('test for Group pages', function() {
    it('Create a new Group', function() {
        login()
        cy.visit('/')

        cy.get('.navbar-nav > :nth-child(5) > div >.nav-link').click();

        cy.get('a').contains('Create new Group').click()
        cy.get('#name').type('Dis is a Tescht Group')
        cy.get('#submitBtn').click()
        cy.get('#edit').click()

        let about = "WE ARE NUMBER ONE"
        let name = "aNewName"
        let 

        cy.get('#about').type('some')
    })
})
# Conventions for contributing to this project

## Git commits

- Git commits should be short and precise
- They should be ordered in a way that represents their dependencies
- The first line should be capitalized
- The first line should not exceed 50 characters, the trailing lines 75 at max
- Git commits should be imperative

### Git commit message keywords

- "Add" to add a functionality
- "Remove" to remove code
- "Fix" to fix a bug
- "Update" to update a functionality
- "Move" to move something

## Git branch naming conventions

A feature branch should be named "feature/&lt;featureDescriptor&gt;".\
A branch for a bugfix should be named "bugfix/&lt;bugDescriptor&gt;".\
A branch for fixing a critical bug should be named "hotfix/&lt;bugDescriptor&gt;".

## Git merge

1. test branch
2. create pull request with description of the change this pull request does

## Tests

- Unit tests are run with gradle
- Integration tests are run with cypress via npm
- Everything possible should be tested

### Test naming conventions

- Unit tests should be named with BDD syntax e.g. "should load about page"

## Standard workflow

1. Create View
2. Create Controller - Mockup
3. Create Tests
4. Create Controller
5. Run tests

> Tests should be run regulary when making a bigger change.\
> Each step should be one commit.

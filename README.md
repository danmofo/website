# ABS - Another Blog System

> We need more blog systems
>
> --**<cite>No one ever</cite>**

This is the source code that powers my personal blog. It was written to demonstrate my experience with Spring / Hibernate.

## Prerequisites
- Java 8 - http://openjdk.java.net/
- Flyway, `brew install flyway`
- Common Java repository, `git clone https://github.com/danmofo/common`

## Setup

### Mac
- Download the source, `git clone https://github.com/danmofo/website`
- Install Java dependencies, `mvn install`
- Set up the database, `./build.sh`. Note, this will remove any existing databases and recreate the schema from scratch. This is only for development purposes whilst the database is in a state of flux.

### Windows
Same steps as above, with a few changes..
- Run flyway directly using the following: `/c/dev/java/flyway-4.2.0/flyway <command>`
- Change the migrations folder path inside `flyway.conf` to: `filesystem:c:/users/dan/git/website/src/main/resources/db/migrations`

## Development
- Use the `develop` branch for finished features ready for testing
- Use feature branches for new features

## Tests
There are a mixture of unit / integration tests available, to run them, make sure Maven is installed and run the following from the root directory: `mvn clean test`.

## Building / deployment
- todo

## TODO
- Add webpack for front-end build (see `spring-boot-webpack` for integration notes)
- Externalise all secrets / credentials
- Finish other todos littered throughout the source code
- Add common code to a git repo
- Add pagination to things which return a huge amount of items
- Rethink the fetching strategy, since pagination is super slow when eagerly fetching since Hibernate does the limit / offset entirely in memory :/
- Add sort to service methods
- Add tests for service methods that call the other methods with default values.

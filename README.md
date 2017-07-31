# ABS - Another Blog System

## *Because the world is in dire need of even more blog systems.*

This is the source code that powers my personal blog. It was written to demonstrate my experience with Spring / Hibernate.

## Prerequisites
- Java 8 - http://openjdk.java.net/
- Flyway, `brew install flyway`
- Common Java repository, `git clone https://github.com/danmofo/common`

## Setup
- Download the source, `git clone https://github.com/danmofo/website`
- Install Java dependencies, `mvn install`
- Set up the database, `./build.sh`. Note, this will remove any existing databases and recreate the schema from scratch. This is only for development purposes whilst the database is in a state of flux.

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

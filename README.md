# ABS - Another Blog System

## *Because the world is in dire need of even more blog systems.*

This is the source code that powers my personal blog. It was written to demonstrate my experience with Spring / Hibernate.

## Prerequisites
- Java 8 - http://openjdk.java.net/
- Flyway, `brew install flyway`

## Setup / development
- Download the source, `git clone https://github.com/danmofo/website`
- Install Java dependencies, `mvn install`
- Set up the database, `./build.sh`. Note, this will remove any existing databases and recreate the schema from scratch. This is only for development purposes whilst the database is in a state of flux.

## Building / deployment
- todo

## TODO
- Add webpack for front-end build (see `spring-boot-webpack` for integration notes)
- Externalise all secrets / credentials
- Finish other todos littered throughout the source code

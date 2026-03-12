# JOOQ Flyway Sandbox

A sample application demonstrating how to use [JOOQ](https://www.jooq.org/)
and [Flyway](https://github.com/flyway/flyway) to facilitate type-safe, immutable database management and interaction.
This repository serves as a
minimal, reproducible sandbox for understanding how these tools work together.

## Features

- Connects to PostgreSQL via config
- Migrates the schema in a series of immutable steps
- Generates a set of Java classes to model the database objects and metadata
- Creates and runs a local instance of PostgreSQL via [Docker Compose](https://docs.docker.com/compose/)

## Usage

### Building

Automatically starts docker compose, spins up a local PostgreSQL database, migrates the schema, and generates the JOOQ
classes.

`./gradlew build`

### Running

Automatically starts docker compose, spins up a local PostgreSQL database, migrates the schema, and generates the JOOQ
classes.

`./gradlew run`

### Starting the Local Database

Starts the local instance of PostgreSQL in isolation.

`./gradlew composeUp`

### Stopping the Local Database
`./gradlew composeDown`

### Migrating the Database Schema

Automatically starts docker compose, and spins up a local PostgreSQL database. Migrates the schema in isolation.

`./gradlew flywayMigrate`

### Generating the JOOQ Classes

Automatically starts docker compose, spins up a local PostgreSQL database, and migrates the schema. Generates the JOOQ
classes in isolation.

`./gradlew generateJooq`

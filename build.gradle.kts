buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.flywaydb:flyway-database-postgresql:${project.properties["flywayVersion"]}")
        classpath("org.postgresql:postgresql:${project.properties["postgresVersion"]}")
    }
}

plugins {
    kotlin("jvm") version "2.3.0"
    id("application")
    id("com.avast.gradle.docker-compose") version "0.17.21"
    id("org.flywaydb.flyway") version "12.1.0"
    id("nu.studer.jooq") version "10.2"
}

group = "ben.gangl.lipson"
version = "1.0-SNAPSHOT"

val postgresVersion: String = project.properties["postgresVersion"] as String
val flywayVersion: String = project.properties["flywayVersion"] as String
val jooqVersion: String = project.properties["jooqVersion"] as String

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.postgresql:postgresql:$postgresVersion")
    implementation("org.flywaydb:flyway-core:$flywayVersion")
    implementation("org.flywaydb:flyway-database-postgresql:$flywayVersion")
    implementation("org.jooq:jooq:$jooqVersion")

    jooqGenerator("org.postgresql:postgresql:$postgresVersion")
    testImplementation(kotlin("test"))
}

application {
    mainClass = "com.bengangllipson.jooqflywaysandbox.MainKt"
}

kotlin {
    jvmToolchain(25)
}

flyway {
    driver = "org.postgresql.Driver"
    url = "jdbc:postgresql://localhost:5432/sandbox_db"
    user = "sandbox_user"
    password = "sandbox_password"
    schemas = arrayOf("sandbox")
    defaultSchema = "sandbox"
    locations = arrayOf("filesystem:./flyway/migration")
    failOnMissingLocations = true
}

jooq {
    version.set(jooqVersion)
    edition = nu.studer.gradle.jooq.JooqEdition.OSS
    configurations {
        create("main") {
            jooqConfiguration.apply {
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = "jdbc:postgresql://localhost:5432/sandbox_db"
                    user = "sandbox_user"
                    password = "sandbox_password"
                }
                generator.apply {
                    name = "org.jooq.codegen.KotlinGenerator"
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = "sandbox"
                        includes = ".*"
                        excludes = ""
                    }
                    generate.apply {
                        isDeprecated = false
                        isRecords = true
                        isImmutablePojos = true
                        isFluentSetters = true
                    }
                    target.apply {
                        packageName = "org.jooq.generated"
                        directory = "src/main/java/"
                    }
                }
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.named("flywayMigrate") {
    dependsOn("composeUp")
}

tasks.named("generateJooq") {
    dependsOn("flywayMigrate")
}

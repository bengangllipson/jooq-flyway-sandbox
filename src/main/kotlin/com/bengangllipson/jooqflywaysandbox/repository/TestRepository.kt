package com.bengangllipson.jooqflywaysandbox.repository

import org.jooq.generated.tables.pojos.TestTable
import org.jooq.generated.tables.references.TEST_TABLE
import org.jooq.impl.DSL
import java.util.Properties

class TestRepository {
    private val properties = Properties()

    fun createTestTableRecords() = run {
        properties.load(TestRepository::class.java.getResourceAsStream("/config.properties"))
        DSL.using(
            properties.getProperty("postgres.url"),
            properties.getProperty("postgres.username"),
            properties.getProperty("postgres.password")
        ).use { ctx ->
            ctx.insertInto(
                TEST_TABLE,
                TEST_TABLE.NAME,
                TEST_TABLE.COOL,
                TEST_TABLE.CREATED_DATE,
                TEST_TABLE.LAST_MODIFIED_DATE,
                TEST_TABLE.OPTIONAL,
            ).values(
                "Ben",
                true,
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                12
            ).execute()
        }
    }

    fun getTestTableRecord(id: Int): List<TestTable> = run {
        properties.load(TestRepository::class.java.getResourceAsStream("/config.properties"))
        DSL.using(
            properties.getProperty("postgres.url"),
            properties.getProperty("postgres.username"),
            properties.getProperty("postgres.password")
        ).use { ctx ->
            ctx.select().from(TEST_TABLE).where(TEST_TABLE.ID.eq(id))
                .fetchInto(TestTable::class.java)
        }
    }
}

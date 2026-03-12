package com.bengangllipson.jooqflywaysandbox

import com.bengangllipson.jooqflywaysandbox.repository.TestRepository

fun main() {
    val testRepository = TestRepository()
    testRepository.createTestTableRecords()
    val res = testRepository.getTestTableRecord(1)
    println(res)
}

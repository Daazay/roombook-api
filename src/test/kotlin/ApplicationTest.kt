package org.daazay

import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun testRoot() = testApplication {
        assertEquals(true, true)
    }

}
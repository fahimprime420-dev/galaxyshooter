package com.example.galaxyshooter
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test fun packageName() {
        val ctx = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.galaxyshooter", ctx.packageName)
    }
}

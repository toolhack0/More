package com.example

import com.example.data.PresetConfigs
import com.example.ui.components.formatBytes
import com.example.ui.components.formatDuration
import com.example.ui.components.formatSpeed
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun testPresetConfigsAndServersExist() {
        assertTrue("Servers list should not be empty", PresetConfigs.servers.isNotEmpty())
        assertTrue("Default configs should not be empty", PresetConfigs.defaultConfigs.isNotEmpty())
        assertTrue("Network tweaks should not be empty", PresetConfigs.networkTweaks.isNotEmpty())

        val germany = PresetConfigs.servers.find { it.country.contains("Germany") }
        assertNotNull(germany)

        val egypt = PresetConfigs.servers.find { it.country.contains("Egypt") }
        assertNotNull(egypt)
    }

    @Test
    fun testFormatters() {
        assertEquals("00:00:00", formatDuration(0))
        assertEquals("00:01:05", formatDuration(65))
        assertEquals("01:00:00", formatDuration(3600))

        assertEquals("500 B", formatSpeed(500))
        assertEquals("50 KB", formatSpeed(50_000))
        assertEquals("2.5 MB", formatSpeed(2_500_000))

        assertEquals("500 B", formatBytes(500))
        assertEquals("50 KB", formatBytes(50_000))
        assertEquals("10.0 MB", formatBytes(10_000_000))
    }
}

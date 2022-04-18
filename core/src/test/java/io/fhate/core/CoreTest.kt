package io.fhate.core

import io.fhate.core.util.TextUtils
import org.junit.Test
import org.junit.Assert.*

class CoreTest {

    @Test
    fun textUtils_formatPrettyCount_isCorrect() {
        // for RoundingMode.UP
        assertEquals(
            "231 ",
            TextUtils.formatPrettyCount(231)
        )
        assertEquals(
            "2k",
            TextUtils.formatPrettyCount(1999)
        )
        assertEquals(
            "2.3k",
            TextUtils.formatPrettyCount(2300)
        )
        assertEquals(
            "1.7M",
            TextUtils.formatPrettyCount(1648220)
        )
        assertEquals(
            "2.1M",
            TextUtils.formatPrettyCount(2081003)
        )
        assertEquals(
            "2.5M",
            TextUtils.formatPrettyCount(2500000)
        )
        assertEquals(
            "2.1M",
            TextUtils.formatPrettyCount(2001000)
        )
        assertEquals(
            "6.8B",
            TextUtils.formatPrettyCount(6721356213)
        )
    }

}
package com.mperezc.core.utils

import com.mperezc.core.utils.CoreUtils.PATTERN_TWO_DECIMAL
import org.junit.Before
import org.junit.Test


internal class CoreUtilsTest {

    @Before
    fun setUp() {
        //Nothing to do
    }

    @Test
    fun `obtain number formatted as string`() {
        val value = 1024975631.45687
        val expected ="1.024.975.631,46"
        val strValue = CoreUtils.getFormattedNumber(value, PATTERN_TWO_DECIMAL)
        assert(expected == strValue)
    }

    @Test
    fun `obtain number rounded`() {
        val value = 1024975631.45687
        val expected = 1024975631.457
        val rounded = CoreUtils.roundDouble(value, 3)
        assert(expected == rounded )
    }
}
package de.psdev.energylogger.parser

import org.junit.Assert
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ParseUtilsTest {

    @Test
    fun testParseFileTypeInfo() {
        // Given
        val infoByte = byteArrayOf(0x49, 0x4E, 0x46, 0x4F, 0x3A)

        // When
        val fileType = infoByte.readFileType()

        // Then
        assertEquals(FileType.INFO, fileType)
    }

    @Test
    fun testParseFileTypeData() {
        // Given
        val dataBytes = byteArrayOf(0xE0.toByte(), 0xC5.toByte(), 0xEA.toByte())

        // When
        val fileType = dataBytes.readFileType()

        // Then
        assertEquals(FileType.DATA, fileType)
    }

    @Test
    fun testParseFileTypeUnknown() {
        // Given
        val dataBytes = byteArrayOf(0xB0.toByte(), 0xC5.toByte(), 0xEA.toByte())

        // When
        val fileType = dataBytes.readFileType()

        // Then
        assertEquals(FileType.UNKNOWN, fileType)
    }

    @Test
    fun testEqualsRangeSameArray() {
        // Given
        val dataBytes = byteArrayOf(0xE0.toByte(), 0xC5.toByte(), 0xEA.toByte())

        // When
        val result= dataBytes.equalsRange(dataBytes)

        // Then
        assertTrue(result)
    }

    @Test
    fun testEqualsRangeWithLargerArray() {
        // Given
        val data = byteArrayOf(0xE0.toByte(), 0xC5.toByte())
        val largerData = byteArrayOf(0xE0.toByte(), 0xC5.toByte(), 0xEA.toByte())

        // When
        val result= data.equalsRange(largerData)

        // Then
        assertFalse(result)
    }

    @Test
    fun testEqualsRangeWithOffset() {
        // Given
        val data = byteArrayOf(0xE0.toByte(), 0xC5.toByte())
        val data2 = byteArrayOf(0xE0.toByte(), 0xC5.toByte())

        // When
        val result= data.equalsRange(data2, offset = 1)

        // Then
        assertFalse(result)
    }

    @Test
    fun testEqualsRangeWithSmallerArray() {
        // Given
        val data = byteArrayOf(0xE0.toByte(), 0xC5.toByte(), 0xC5.toByte())
        val data2 = byteArrayOf(0xE0.toByte(), 0xC5.toByte())

        // When
        val result= data.equalsRange(data2)

        // Then
        assertTrue(result)
    }

    @Test
    fun testParseDate() {
        val dateByte = byteArrayOf(0x08, 0x02, 0x07, 0x10, 0x1A)
        val parseDate = dateByte.parseDate()
        val calendar = Calendar.getInstance()
        calendar.timeZone = TimeZone.getTimeZone("UTC")
        calendar.set(Calendar.YEAR, 2007)
        calendar.set(Calendar.MONTH, Calendar.AUGUST)
        calendar.set(Calendar.DAY_OF_MONTH, 2)
        calendar.set(Calendar.HOUR_OF_DAY, 16)
        calendar.set(Calendar.MINUTE, 26)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        Assert.assertEquals(calendar.time, parseDate)
    }
}
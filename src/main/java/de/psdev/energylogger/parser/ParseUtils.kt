@file:JvmName("ParseUtils")

package de.psdev.energylogger.parser

import de.psdev.energylogger.parser.EnergyLoggerDataParserImpl.Companion.START_CODE_DATA
import de.psdev.energylogger.parser.EnergyLoggerDataParserImpl.Companion.START_CODE_INFO
import java.util.*

fun ByteArray.readFileType(): FileType = when {
    equalsRange(START_CODE_DATA) -> FileType.DATA
    equalsRange(START_CODE_INFO) -> FileType.INFO
    else -> FileType.UNKNOWN
}

fun ByteArray.equalsRange(other: ByteArray, offset: Int = 0, length: Int = other.size): Boolean {
    if (this === other) {
        // Same instance
        return true
    }
    if (size < (offset + length) || other.size < length) {
        // Not enough bytes
        return false
    }
    for (i in 0 until length) {
        if (this[offset + i] != other[i]) {
            return false
        }
    }
    return true
}

fun ByteArray.parseDate(offset: Int = 0): Date {
    val month = this[offset + 0]
    val day = this[offset + 1]
    val year = this[offset + 2]
    val hour = this[offset + 3]
    val minute = this[offset + 4]
    val calendar = GregorianCalendar.getInstance().apply {
        timeZone = TimeZone.getTimeZone("UTC")
        set(Calendar.DAY_OF_MONTH, day.toInt())
        set(Calendar.MONTH, month - 1)
        set(Calendar.YEAR, 2000 + year)
        set(Calendar.HOUR_OF_DAY, hour.toInt())
        set(Calendar.MINUTE, minute.toInt())
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    return calendar.time
}
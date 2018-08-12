/*
 * Copyright 2012-2018 Philip Schiffer <admin@psdev.de>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package de.psdev.energylogger.parser

import de.psdev.energylogger.parser.FileType.DATA
import de.psdev.energylogger.parser.FileType.INFO
import de.psdev.energylogger.parser.FileType.UNKNOWN
import mu.KLogging
import java.io.IOException
import java.io.InputStream
import java.math.BigInteger
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

class EnergyLoggerDataParserImpl : EnergyLoggerDataParser {

    override fun parseZippedDataFiles(zipFile: ZipFile): List<LogEntry> {
        val startTime = System.currentTimeMillis()
        val logEntries = ArrayList<LogEntry>()
        val zipEntries = zipFile.entries()
        while (zipEntries.hasMoreElements()) {
            val zipEntry = zipEntries.nextElement()
            logEntries.addAll(handleZipEntry(zipFile, zipEntry))
        }
        val runtime = System.currentTimeMillis() - startTime
        logger.info("Parsed {} logentries in {}", logEntries.size, runtime)
        return logEntries
    }

    @Throws(IOException::class)
    override fun parseFileContents(input: InputStream): List<LogEntry> {
        val logEntries = mutableListOf<LogEntry>()
        val data = input.readBytes()
        val fileType = data.readFileType()
        when (fileType) {
            INFO -> {
                logger.warn("got INFO file - ignoring because of invalid data")
            }
            DATA -> {
                val currentDate = GregorianCalendar.getInstance()
                var counter = 0
                while (counter < data.size) {
                    if (data.equalsRange(START_CODE_DATA, offset = counter)) {
                        counter += START_CODE_DATA.size
                        currentDate.time = data.parseDate(offset = counter)
                        counter += LENGTH_DATA_DATE
                    } else if (data.equalsRange(EOF_CODE, offset = counter)) {
                        logger.debug { "EOF" }
                        break
                    } else {
                        val avgVoltage = parseInteger(data[counter + 0], data[counter + 1]) / 10.0
                        val avgCurrent = parseInteger(data[counter + 2], data[counter + 3]) / 1000.0
                        val avgPowerFactor = parseInteger(data[counter + 4]) / 100.0
                        val logEntry = LogEntry(currentDate.time, avgVoltage, avgCurrent, avgPowerFactor)
                        logEntries.add(logEntry)
                        currentDate.add(Calendar.MINUTE, 1)
                        counter += LENGTH_DATA_VALUES
                    }
                }
            }
            UNKNOWN -> throw IOException("Unknown file type")
        }
        return logEntries
    }

    private fun handleZipEntry(zipFile: ZipFile, zipEntry: ZipEntry): List<LogEntry> = zipFile.getInputStream(zipEntry).use { parseFileContents(it) }

    private fun parseInteger(vararg bytes: Byte) = BigInteger(bytes).intValueExact()

    // Inner classes

    companion object : KLogging() {
        val START_CODE_DATA = byteArrayOf(0xE0.toByte(), 0xC5.toByte(), 0xEA.toByte())
        val START_CODE_INFO = byteArrayOf(0x49.toByte(), 0x4E.toByte(), 0x46.toByte(), 0x4F.toByte(), 0x3A.toByte())

        val EOF_CODE = byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte())

        const val LENGTH_DATA_VALUES = 5
        const val LENGTH_DATA_DATE = 5
    }
}

/*
 * Copyright 2012-2013 Philip Schiffer <admin@psdev.de>
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

import mu.KLogging
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.zip.ZipFile

class EnergyLoggerDataParserTest {

    companion object : KLogging() {
        private const val EXPECTED_LOG_ENTRIES_IN_TEST_FILE = 2105
        private const val EXPECTED_LOG_ENTRIES_IN_ZIP_FILE = 260005
    }

    private val energyLoggerDataParser: EnergyLoggerDataParserImpl = EnergyLoggerDataParserImpl()

    @Test
    @Throws(IOException::class)
    fun testParseZipFile() {
        val file = File(javaClass.getResource("/data.zip").file)
        val zipFile = ZipFile(file)
        val parsedLogEntriesFromFile = energyLoggerDataParser.parseZippedDataFiles(zipFile)
        zipFile.close()
        logger.info("Parsed " + parsedLogEntriesFromFile.size + " entries from file " + file)
        assertEquals(EXPECTED_LOG_ENTRIES_IN_ZIP_FILE.toLong(), parsedLogEntriesFromFile.size.toLong())
    }

    @Test
    @Throws(IOException::class)
    fun testParseFileContents() {
        val file = File(javaClass.getResource("/a0494206.bin").file)
        val parsedLogEntriesFromFile = energyLoggerDataParser.parseFileContents(FileInputStream(file))
        logger.info("Parsed " + parsedLogEntriesFromFile.size + " entries from file " + file)
        assertEquals(EXPECTED_LOG_ENTRIES_IN_TEST_FILE.toLong(), parsedLogEntriesFromFile.size.toLong())
        val logEntry = parsedLogEntriesFromFile[0]
        assertNotNull(logEntry)
        assertEquals(226.1, logEntry.voltage, 0.001)
        assertEquals(0.508, logEntry.current, 0.001)
        assertEquals(0.76, logEntry.powerfactor, 0.001)
        assertEquals(1237257540000L, logEntry.timestamp.time)
    }

    @Test(expected = IOException::class)
    @Throws(Exception::class)
    fun testHandleWrongInput() {
        val file = File(javaClass.getResource("/badfile.bin").file)
        energyLoggerDataParser.parseFileContents(FileInputStream(file))
    }

}

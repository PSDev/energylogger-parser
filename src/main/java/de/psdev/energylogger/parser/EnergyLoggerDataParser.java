package de.psdev.energylogger.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipFile;

public interface EnergyLoggerDataParser {
	
	List<LogEntry> parseZippedDataFiles(ZipFile zipFile);
	
	List<LogEntry> parseFileContents(InputStream input) throws IOException;

	Date parseDate(byte[] dateByte);
}

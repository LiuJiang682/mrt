package au.gov.vic.ecodev.mrt.template.files;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import au.gov.vic.ecodev.mrt.constants.LogSeverity;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.services.PersistentServices;

public class LogFileGenerator {

	private static final Logger LOGGER = Logger.getLogger(LogFileGenerator.class);

	private static final String FILE_EXTENSION = ".log";

	private static final String FILE_PREFIX = "Batch_";

	private static final String PERMISSION_READ_WRITE = "rw";

	private static final String DELIM = System.getProperty("line.separator");

	private static final String SEPARATOR = Strings.COLON + Strings.SPACE;

	private final long batchId;
	private final PersistentServices persistentServices;
	private final String logFileOutputDirectory;
	private String logFileName;

	public LogFileGenerator(long batchId, PersistentServices persistentServices, String logFileOutputDirectory) {
		if (Numeral.ONE > batchId) {
			throw new IllegalArgumentException("Parameter batchId must be greater than zero!");
		}
		this.batchId = batchId;
		if (null == persistentServices) {
			throw new IllegalArgumentException("Parameter statusLogDao cannot be null!");
		}
		this.persistentServices = persistentServices;
		if (StringUtils.isEmpty(logFileOutputDirectory)) {
			throw new IllegalArgumentException("Parameter logFileOutputDirectory cannot be empty!");
		}
		this.logFileOutputDirectory = logFileOutputDirectory;
	}

	public String generateLogs() {
		String abnormalExitReason = null;
		logFileName = generateLogFileName();
		LOGGER.info("About to create file: " + logFileName);
		File file = new File(logFileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				abnormalExitReason = e.getMessage();
				LOGGER.error(abnormalExitReason, e);
				return abnormalExitReason;
			}
		}

		try (RandomAccessFile logFile = new RandomAccessFile(file, PERMISSION_READ_WRITE);
				FileChannel fileChannel = logFile.getChannel();) {
			generateLogBySeverity(fileChannel, LogSeverity.ERROR);
			generateLogBySeverity(fileChannel, LogSeverity.WARNING);
			generateLogBySeverity(fileChannel, LogSeverity.INFO);
		} catch (Exception e) {
			abnormalExitReason = e.getMessage();
			LOGGER.error(abnormalExitReason, e);
		}

		return abnormalExitReason;
	}

	private void generateLogBySeverity(FileChannel fileChannel, LogSeverity severity) throws IOException {
		List<String> errorMsgs = persistentServices.getErrorMessageByBatchId(batchId, severity);
		for (String errorMsg : errorMsgs) {
			String message = new StringBuilder(severity.name()).append(SEPARATOR).append(errorMsg).append(DELIM)
					.toString();
			LOGGER.info(message);
			byte[] strBytes = message.getBytes();
			ByteBuffer buffer = ByteBuffer.allocate(strBytes.length);
			buffer.put(strBytes);
			buffer.flip();
			fileChannel.write(buffer);
		}
	}

	public String getLogFileName() {
		return logFileName;
	}

	private String generateLogFileName() {
		return new StringBuilder(logFileOutputDirectory)
				.append(File.separator)
				.append(FILE_PREFIX)
				.append(batchId)
				.append(FILE_EXTENSION)
				.toString();
	}
}

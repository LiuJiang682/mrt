package au.gov.vic.ecodev.mrt.template.processor.file.parser;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

import au.gov.vic.ecodev.mrt.constants.LogSeverity;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.utils.file.finder.DirectoryTreeReverseTraversalZipFileFinder;

public class MessageHandler {

	private static final Logger LOGGER = Logger.getLogger(MessageHandler.class);
	
	private static final String SEPERATOR = "-";
	private static final String LINE_NUMBER = "line number ";
	private static final String COLON = ": ";
	
	private final List<String> messages;
	private final TemplateProcessorContext templateProcessorContext;
	private Template template;
	private final File file;
	private final int lineNumber;
	
	public MessageHandler(List<String> messages, TemplateProcessorContext templateProcessorContext,
			Template template, File file, int lineNumber) {
		this.messages = messages;
		this.templateProcessorContext = templateProcessorContext;
		this.template = template;
		this.file = file;
		this.lineNumber = lineNumber;
	}

	public Template doMessagesHandling() {
		messages.stream()
			.forEach(message -> {
				LOGGER.debug(message);
				if (message.startsWith(Strings.LOG_INFO_HEADER)) {
					String formattedMessage = doStringFormat(
							message.replace(Strings.LOG_INFO_HEADER, 
									au.gov.vic.ecodev.mrt.constants.Constants.Strings.EMPTY).trim());
					templateProcessorContext.saveStatusLog(LogSeverity.INFO, 
							formattedMessage);
				} else if (message.startsWith(Strings.LOG_WARNING_HEADER)) {
					String formattedMessage = doStringFormat(
							message.replace(Strings.LOG_WARNING_HEADER, 
									au.gov.vic.ecodev.mrt.constants.Constants.Strings.EMPTY).trim());
					templateProcessorContext.saveStatusLog(LogSeverity.WARNING, 
							formattedMessage);
				} else {
					if (message.startsWith(Strings.LOG_ERROR_HEADER)) {
						message = message.replace(Strings.LOG_ERROR_HEADER, 
								au.gov.vic.ecodev.mrt.constants.Constants.Strings.EMPTY).trim();
					}
					String formattedMessage = doStringFormat(message);
					templateProcessorContext.saveStatusLog(LogSeverity.ERROR, formattedMessage);
					String zipFile = new DirectoryTreeReverseTraversalZipFileFinder(file.getParent())
							.findZipFile();
					templateProcessorContext.addFailedFiles(zipFile);
					//No need to keep the data since error found
					template = null; 
				}
			});
		
		return template;
	}

	private String doStringFormat(final String message) {
		return new StringBuilder(file.getAbsolutePath())
				.append(SEPERATOR)
				.append(LINE_NUMBER)
				.append(lineNumber)
				.append(COLON)
				.append(message)
				.toString();
	}
}

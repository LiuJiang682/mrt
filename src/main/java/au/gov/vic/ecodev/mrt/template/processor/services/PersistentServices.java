package au.gov.vic.ecodev.mrt.template.processor.services;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import au.gov.vic.ecodev.mrt.api.constants.LogSeverity;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public interface PersistentServices {

	long getNextBatchId(final List<String> templateNames, List<String> fileNames);

	boolean saveStatusLog(long batchId, LogSeverity severity, String logMessage);
	
	String getTemplateClasses(final String templateName);
	String getTemplateUpdaterClass(final String templateName);
	String getTemplateContextProperty(final String templateName, final String propertyName);
	
	List<String> getErrorMessageByBatchId(final long batchId, LogSeverity severity);

	boolean saveDataBean(final JdbcTemplate jdbcTemplate, long batchId, final Template template);

}

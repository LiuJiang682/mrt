package au.gov.vic.ecodev.mrt.dao;

import java.util.List;

import au.gov.vic.ecodev.mrt.constants.LogSeverity;

public interface StatusLogDao {

	List<String> getErrorMessageByBatchId(final long batchId, final LogSeverity severity);

	boolean saveStatusLog(final long batchId, final LogSeverity severity, final String logMessage);

}

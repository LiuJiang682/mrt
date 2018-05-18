package au.gov.vic.ecodev.mrt.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import au.gov.vic.ecodev.common.util.IDGenerator;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.LogSeverity;

@Repository
public class StatusLogDaoImpl implements StatusLogDao {

	private static final String SELECT_SQL = "select error_msg from file_error_log where batch_id = ? and SEVERITY = ? order by CREATED_TIME asc";
	private static final String INSERT_SQL = "INSERT INTO file_error_log(id, batch_id, SEVERITY, error_msg, CREATED_TIME) values (?, ?, ?, ?, systimestamp)";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<String> getErrorMessageByBatchId(long batchId, LogSeverity severity) {
		List<String> errorMsgs = jdbcTemplate.queryForList(SELECT_SQL, String.class, batchId, severity.name());
		return errorMsgs;
	}

	@Override
	public boolean saveStatusLog(long batchId, LogSeverity severity, String logMessage) {
		int rows = jdbcTemplate.update(INSERT_SQL, new Object[] {IDGenerator.getUID().longValue(), 
				batchId, severity.name(), logMessage});
		return Numeral.ONE == rows;
	}

}

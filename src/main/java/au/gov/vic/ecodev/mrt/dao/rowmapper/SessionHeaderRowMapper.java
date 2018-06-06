package au.gov.vic.ecodev.mrt.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import au.gov.vic.ecodev.mrt.model.SessionHeader;

public class SessionHeaderRowMapper implements RowMapper<SessionHeader> {

	private static final String COLUMN_HEADER_CREATED = "CREATED";
	private static final String COLUMN_HEADER_REJECTED = "REJECTED";
	private static final String COLUMN_HEADER_APPROVED = "APPROVED";
	private static final String COLUMN_HEADER_FILE_NAME = "FILE_NAME";
	private static final String COLUMN_HEADER_TEMPLATE = "TEMPLATE";
	private static final String COLUMN_HEADER_EMAIL_SENT = "EMAIL_SENT";
	private static final String COLUMN_HEADER_COMMENTS = "COMMENTS";
	private static final String COLUMN_HEADER_STATUS = "STATUS";
	private static final String COLUMN_HEADER_PROJECT_NAME = "PROJECT_NAME";
	private static final String COLUMN_HEADER_REPORTING_DATE = "REPORTING_DATE";
	private static final String COLUMN_HEADER_TENEMENT_HOLDER = "TENEMENT_HOLDER";
	private static final String COLUMN_HEADER_TENEMENT = "TENEMENT";
	private static final String COLUMN_HEADER_PROCESS_DATE = "PROCESS_DATE";
	private static final String COLUMN_HEADER_ID = "ID";

	@Override
	public SessionHeader mapRow(ResultSet rs, int rowNum) throws SQLException {
		SessionHeader sessionHeader = new SessionHeader();
		sessionHeader.setSessionId(rs.getLong(COLUMN_HEADER_ID));
		sessionHeader.setTemplate(rs.getString(COLUMN_HEADER_TEMPLATE));
		sessionHeader.setFileName(rs.getString(COLUMN_HEADER_FILE_NAME));
		sessionHeader.setProcessDate(rs.getDate(COLUMN_HEADER_PROCESS_DATE));
		sessionHeader.setTenement(rs.getString(COLUMN_HEADER_TENEMENT));
		sessionHeader.setTenementHolder(rs.getString(COLUMN_HEADER_TENEMENT_HOLDER));
		sessionHeader.setReportingDate(rs.getDate(COLUMN_HEADER_REPORTING_DATE));
		sessionHeader.setProjectName(rs.getString(COLUMN_HEADER_PROJECT_NAME));
		sessionHeader.setStatus(rs.getString(COLUMN_HEADER_STATUS));
		sessionHeader.setComments(rs.getString(COLUMN_HEADER_COMMENTS));
		sessionHeader.setEmailSent(rs.getString(COLUMN_HEADER_EMAIL_SENT));
		sessionHeader.setApproved(rs.getInt(COLUMN_HEADER_APPROVED));
		sessionHeader.setRejected(rs.getInt(COLUMN_HEADER_REJECTED));
		sessionHeader.setCreated(rs.getTimestamp(COLUMN_HEADER_CREATED));
		return sessionHeader;
	}

}

package au.gov.vic.ecodev.mrt.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.model.fields.SessionStatus;

public class SessionHeader implements au.gov.vic.ecodev.mrt.template.processor.model.Entity, Serializable {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = -988540627996071390L;
	
	private static final String NOT_SENT = "N";
	
	private long sessionId;
	private String template;
	private Date processDate;
	private String tenement;
	private String tenementHolder;
	private Date reportingDate;
	private String projectName;
	private String status;
	private String comments;
	private String emailSent;
	
	public SessionHeader() {	
	}
	
	public SessionHeader(final long sessionId) {
		this.sessionId = sessionId;
		setTemplate(Strings.EMPTY);
		processDate = new Date();
		tenement = Strings.EMPTY;
		tenementHolder = Strings.EMPTY;
		reportingDate = new Date();
		projectName = Strings.EMPTY;
		status = SessionStatus.RUNNING.name();
		comments = Strings.EMPTY;
		emailSent = NOT_SENT;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	public String getTenement() {
		return tenement;
	}

	public void setTenement(String tenement) {
		this.tenement = tenement;
	}

	public String getTenementHolder() {
		return tenementHolder;
	}

	public void setTenementHolder(String tenementHolder) {
		this.tenementHolder = tenementHolder;
	}

	public Date getReportingDate() {
		return reportingDate;
	}

	public void setReportingDate(Date reportingDate) {
		this.reportingDate = reportingDate;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public SessionStatus getStatus() {
		return SessionStatus.valueOf(status);
	}

	public void setStatus(SessionStatus status) {
		this.status = status.name();
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getEmailSent() {
		return emailSent;
	}

	public void setEmailSent(String emailSent) {
		this.emailSent = emailSent;
	}

	public long getSessionId() {
		return sessionId;
	}
	
	public void setSessionId(final long sessionId) {
		this.sessionId = sessionId;
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}

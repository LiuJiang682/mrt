package au.gov.vic.ecodev.mrt.map.services.helper;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class ExceptionEmailBodyBuilder {

	private final Exception reportingException;
	
	public ExceptionEmailBodyBuilder(Exception reportingException) {
		if (null == reportingException) {
			throw new IllegalArgumentException("ExceptionEmailBodyBuilder:reportingException cannot be null!");
		}
		this.reportingException = reportingException;
	}

	public String build() {
		StringBuilder buf = new StringBuilder("Hi\n");
		buf.append("\n");
		buf.append("There are failures in the system due to: ");
		buf.append(reportingException.getMessage());
		buf.append("\n");
		buf.append("StackTrace:\n");
		buf.append(ExceptionUtils.getStackTrace(reportingException));
		return buf.toString();
	}

}

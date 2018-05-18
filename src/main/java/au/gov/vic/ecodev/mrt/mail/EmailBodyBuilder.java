package au.gov.vic.ecodev.mrt.mail;

import org.springframework.util.StringUtils;

import au.gov.vic.ecodev.mrt.template.loader.fsm.TemplateLoaderStateMachineContext;

public class EmailBodyBuilder {

	private final TemplateLoaderStateMachineContext templateLoaderStateMachineContext;

	public EmailBodyBuilder(TemplateLoaderStateMachineContext templateLoaderStateMachineContext) {
		if (null == templateLoaderStateMachineContext) {
			throw new IllegalArgumentException("Parameter templateLoaderStateMachineContext cannot be null!");
		}
		this.templateLoaderStateMachineContext = templateLoaderStateMachineContext;
	}

	public String build() {
		String directErrorMessage = templateLoaderStateMachineContext.getMessage().getDirectErrorMessage();
		if (StringUtils.isEmpty(directErrorMessage)) {
			StringBuilder buf = new StringBuilder("Hi\n");
			buf.append("\n");
			buf.append("The log file for batch: ");
			buf.append(templateLoaderStateMachineContext.getMessage().getBatchId());
			buf.append(" is available at ");
			buf.append(templateLoaderStateMachineContext.getMessage().getLogFileName());
			buf.append("\n\n");
			buf.append("The successfull processed files at ");
			buf.append(templateLoaderStateMachineContext.getMrtConfigProperties().getPassedFileDirectory());
			buf.append("\n\n");
			buf.append("The failed processed files at ");
			buf.append(templateLoaderStateMachineContext.getMrtConfigProperties().getFailedFileDirectory());
			return buf.toString();
		} else {
			StringBuilder buf = new StringBuilder("Hi\n");
			buf.append("\n");
			buf.append("The template file process is failed due to: ");
			buf.append(directErrorMessage);
			return buf.toString();
		}
	}

}

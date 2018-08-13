package au.gov.vic.ecodev.mrt.mail;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.StringUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.Message;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;

public class MrtEmailBodyBuilder implements EmailBodyBuilder {

	private TemplateProcessorContext templateLoaderStateMachineContext;

	@Override
	public String build() {
		if (null == templateLoaderStateMachineContext) {
			throw new IllegalArgumentException("Parameter templateLoaderStateMachineContext cannot be null!");
		}
		
		Message message = templateLoaderStateMachineContext.getMessage();
		String directErrorMessage = message.getDirectErrorMessage();
		if (StringUtils.isEmpty(directErrorMessage)) {
			StringBuilder buf = new StringBuilder(getHtmlPrefix(message.getEmailSubject()));
			buf.append("Hi<br/>");
			buf.append("<br/>");
			buf.append("The log file for batch: ");
			buf.append(message.getBatchId());
			buf.append(" is available at ");
			buf.append(message.getLogFileName());
			buf.append("<br/><br/>");
			buf.append("The successful processed files at ");
			buf.append(message.getPassedFileDirectory());
			buf.append("<br/><br/>");
			buf.append("The failed processed files at ");
			buf.append(message.getFailedFileDirectory());
			
			List<String> boreHoleIds = message.getBoreHoleIdsOutSideTenement();
			if (CollectionUtils.isNotEmpty(boreHoleIds)) {
				buf.append("<br/><br/>");
				buf.append("The following bore Hole Ids are outside the tenement: ");
				buf.append(String.join(Strings.COMMA, boreHoleIds));
			}
			
			List<String> sampleIds = message.getSampleIdsOutSideTenement();
			if (CollectionUtils.isNotEmpty(sampleIds)) {
				buf.append("<br/><br/>");
				buf.append("The following sample Ids are outside the tenement: ");
				buf.append(String.join(Strings.COMMA, sampleIds));
			}
			buf.append("<br/><br/>");
			String url = message.getWebUrl();
			buf.append("For more detail, please visit <a href=\"");
			buf.append(url);
			buf.append("\">MRT Loader</a>");
			buf.append(getHtmlSuffix());
			return buf.toString();
		} else {
			StringBuilder buf = new StringBuilder(getHtmlPrefix(message.getEmailSubject()));
			buf.append("Hi<br/>");
			buf.append("<br/>");
			buf.append("The template file process is failed due to: ");
			buf.append(directErrorMessage);
			buf.append(getHtmlSuffix());
			return buf.toString();
		}
	}
	
	private String getHtmlPrefix(final String subject) {
		StringBuffer sb = new StringBuffer();
		sb.append("<HTML>\n");
		sb.append("<HEAD>\n");
		sb.append("<TITLE>\n");
		sb.append(subject + "\n");
		sb.append("</TITLE>\n");
		sb.append("</HEAD>\n");
		
		sb.append("<BODY>\n");
		sb.append("<H1>" + subject + "</H1>" + "\n");
		
		return sb.toString();
	}
	
	private String getHtmlSuffix() {
		StringBuffer sb = new StringBuffer();
		sb.append("</BODY>\n");
		sb.append("</HTML>\n");
		return sb.toString();
	}

	@Override
	public void setTemplateProcessorContext(TemplateProcessorContext templateProcessorContext) {
		this.templateLoaderStateMachineContext = templateProcessorContext;
	}

}

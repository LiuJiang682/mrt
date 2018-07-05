package au.gov.vic.ecodev.mrt.mail;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.StringUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;

public class MrtEmailBodyBuilder implements EmailBodyBuilder {

	private TemplateProcessorContext templateLoaderStateMachineContext;

	@Override
	public String build() {
		if (null == templateLoaderStateMachineContext) {
			throw new IllegalArgumentException("Parameter templateLoaderStateMachineContext cannot be null!");
		}
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
			buf.append(templateLoaderStateMachineContext.getMessage().getPassedFileDirectory());
			buf.append("\n\n");
			buf.append("The failed processed files at ");
			buf.append(templateLoaderStateMachineContext.getMessage().getFailedFileDirectory());
			
			List<String> boreHoleIds = templateLoaderStateMachineContext.getMessage().getBoreHoleIdsOutSideTenement();
			if (CollectionUtils.isNotEmpty(boreHoleIds)) {
				buf.append("\n\n");
				buf.append("The following bore Hole Ids are outside the tenement: ");
				buf.append(String.join(Strings.COMMA, boreHoleIds));
			}
			
			List<String> sampleIds = templateLoaderStateMachineContext.getMessage().getSampleIdsOutSideTenement();
			if (CollectionUtils.isNotEmpty(sampleIds)) {
				buf.append("\n\n");
				buf.append("The following sample Ids are outside the tenement: ");
				buf.append(String.join(Strings.COMMA, sampleIds));
			}
			return buf.toString();
		} else {
			StringBuilder buf = new StringBuilder("Hi\n");
			buf.append("\n");
			buf.append("The template file process is failed due to: ");
			buf.append(directErrorMessage);
			return buf.toString();
		}
	}

	@Override
	public void setTemplateProcessorContext(TemplateProcessorContext templateProcessorContext) {
		this.templateLoaderStateMachineContext = templateProcessorContext;
	}

}

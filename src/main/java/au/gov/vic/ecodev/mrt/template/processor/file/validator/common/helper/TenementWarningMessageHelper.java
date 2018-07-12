package au.gov.vic.ecodev.mrt.template.processor.file.validator.common.helper;

import java.util.List;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;

public class TenementWarningMessageHelper {

	public final void contructNoTenementWarningMessage(List<String> messages) {
		String message = new StringBuilder(Strings.LOG_WARNING_HEADER)
				.append("No tenement number provided!")
				.toString();
		messages.add(message);		
	}
}

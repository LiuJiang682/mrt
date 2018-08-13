package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.util.List;
import java.util.Optional;

import org.springframework.util.StringUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;

public class AssayCodeValidator {

	private final String assayCode;
	private final List<String> assayCodeList;
	
	public AssayCodeValidator(final String assayCode, final List<String> assayCodeList) {
		this.assayCode = assayCode;
		this.assayCodeList = assayCodeList;
	}
	
	public void doAssayCodeLookUpValidation(List<String> messages) {
		if (!StringUtils.isEmpty(assayCode)) {
			final String assayCodeUC = assayCode.trim().toUpperCase();
			Optional<String> assayCodeOptional = assayCodeList.stream()
					.map(String::toUpperCase)
					.filter(code -> code.endsWith(assayCodeUC))
					.findFirst();
			if (!assayCodeOptional.isPresent()) {
				String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
						.append(assayCode)
						.append(" is NOT included in H0800")
						.toString();
				messages.add(message);
			}
		}
	}
}

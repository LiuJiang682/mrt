package au.gov.vic.ecodev.mrt.template.processor.validator.helper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0702Validator;

public class H0702HeaderChecker {
	
	private static final String HEADER_BATCH = "batch";

	private static final String HEADER_JOB = "job";

	public final void doOptionalFieldHeaderCheck(List<String> messages, Map<String, List<String>> templateParamMap,
			List<String> headers) {
		List<String> h0702List = templateParamMap.get(Strings.TITLE_PREFIX 
				+ H0702Validator.JOB_NO_TITLE);
		if (!CollectionUtils.isEmpty(h0702List)) {
			String h0702Value = h0702List.get(Numeral.ZERO);
			if (Strings.JOB_NO_MULTIPLY.equalsIgnoreCase(h0702Value)) {
				if (!matchesAny(headers, HEADER_JOB)) {
					if (!matchesAny(headers, HEADER_BATCH)) {
						String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
								.append("Missing Job_no/batch_no header when H0702 value is Multiply")
								.toString();
						messages.add(message);
					}
				}
			}
		}
	}
	
	protected final boolean matchesAny(List<String> headers, String string) {
		Optional<String> matchedStringOptional = headers.stream()
				.filter(header -> StringUtils.containsIgnoreCase(header, string))
				.findFirst();
		if (matchedStringOptional.isPresent()) {
			return true;
		} else {
			return false;
		}
	}
}

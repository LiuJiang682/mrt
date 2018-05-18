package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.util.StringUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;

public class SingleWordColumnHeaderValidator {

	private final String template;
	private final String row;
	private final String code;
	
	public SingleWordColumnHeaderValidator(final String template, final String row, final String code) {
		if (StringUtils.isEmpty(template)) {
			throw new IllegalArgumentException("SingleWordColumnHeaderValidator:template parameter cannot be null or empty!");
		}
		this.template = template;
		if (StringUtils.isEmpty(row)) {
			throw new IllegalArgumentException("SingleWordColumnHeaderValidator:row parameter cannot be null or empty!");
		}
		this.row = row;
		if (StringUtils.isEmpty(code)) {
			throw new IllegalArgumentException("SingleWordColumnHeaderValidator:code parameter cannot be null or empty!");
		}
		this.code = code;
	}

	public void validate(List<String> messages, 
			Map<String, List<String>> templateParamMap, List<String> headerList) {
		Optional<String> headerOptional = headerList.stream()
				.filter(header -> code.equalsIgnoreCase(header))
				.findFirst();
		if (headerOptional.isPresent()) {
			templateParamMap.put(code, Arrays.asList(headerOptional.get()));
		} else {
			String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
					.append("Template ")
					.append(template)
					.append(Strings.SPACE)
					.append(row)
					.append(" row requires the ")
					.append(code)
					.append(" column")
					.toString();
			messages.add(message);
		}
		
	}
}

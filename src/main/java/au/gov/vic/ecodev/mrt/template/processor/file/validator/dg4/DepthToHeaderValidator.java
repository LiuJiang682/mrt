package au.gov.vic.ecodev.mrt.template.processor.file.validator.dg4;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;

public class DepthToHeaderValidator {
	
	private static final Pattern DEPTH_TO_VARIATION_PATTERN = 
			Pattern.compile("^Depth[ |_|-]?To$", Pattern.CASE_INSENSITIVE);
	
	private final String template;
	private final String row;
	private final String code;
	
	public DepthToHeaderValidator(final String template, final String row, final String code) {
		if (StringUtils.isEmpty(template)) {
			throw new IllegalArgumentException("DepthToHeaderValidator: template parameter cannot be null or empty!");
		}
		this.template = template;
		if (StringUtils.isEmpty(row)) {
			throw new IllegalArgumentException("DepthToHeaderValidator: row parameter cannot be null or empty!");
		}
		this.row = row;
		if (StringUtils.isEmpty(code)) {
			throw new IllegalArgumentException("DepthToHeaderValidator: code parameter cannot be null or empty!");
		}
		this.code = code;
	}

	public void validate(List<String> messages, 
			Map<String, List<String>> templateParamMap, List<String> headers) {
		Optional<String> headerOptional = headers.stream()
				.filter(header -> code.equalsIgnoreCase(header))
				.findFirst();
		if (headerOptional.isPresent()) {
			templateParamMap.put(code, Arrays.asList(headerOptional.get()));
		} else {
			Optional<String> depthToVariationOptional = headers.stream()
					.filter(header -> DEPTH_TO_VARIATION_PATTERN.matcher(header).find())
					.findFirst();
			if (depthToVariationOptional.isPresent()) {
				handleColumnNameVariation(messages, templateParamMap,
						depthToVariationOptional.get());
			} else {
				handleColumnHeaderNotFound(messages);
			}
		}
	}

	private void handleColumnNameVariation(List<String> messages, 
			Map<String, List<String>> templateParamMap,
			String columnName) {
		String message = new StringBuilder(Strings.LOG_WARNING_HEADER)
				.append("Template ")
				.append(template)
				.append(Strings.SPACE)
				.append(row)
				.append(" row requires the ")
				.append(code)
				.append(" column, found ")
				.append(columnName)
				.append(" column, use it as ")
				.append(code)
				.toString();
		messages.add(message);
		templateParamMap.put(Strings.TITLE_PREFIX + code, 
				Arrays.asList(columnName));
	}

	private void handleColumnHeaderNotFound(List<String> messages) {
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

package au.gov.vic.ecodev.mrt.template.processor.file.validator.dl4;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.fields.Dl4ColumnHeaders;

public class DepthFromValidator {

	private static final String FROM_SPACE_DEPTH_ALIAS = "From Depth";
	private static final String DEPTH_FROM_ALIAS = "Depth";
	private static final Pattern DEPTH_FROM_VARIATION_PATTERN = 
			Pattern.compile("Depth[ |-]?From", Pattern.CASE_INSENSITIVE);
	private static final Pattern FROM_DEPTH_VARIATION_PATTERN = 
			Pattern.compile("From[ |-]?Depth", Pattern.CASE_INSENSITIVE);
	
	public void validate(List<String> messages, Map<String, List<String>> templateParamMap, List<String> headers) {
		Optional<String> depthFromOptional = headers.stream()
				.filter(header -> Dl4ColumnHeaders.DEPTH_FROM.getCode().equalsIgnoreCase(header))
				.findFirst();
		if (depthFromOptional.isPresent()) {
			templateParamMap.put(Dl4ColumnHeaders.DEPTH_FROM.getCode(), 
					Arrays.asList(depthFromOptional.get()));
		} else {
			Optional<String> depthAliasOptional = headers.stream()
					.filter(header -> DEPTH_FROM_ALIAS.equalsIgnoreCase(header))
					.findFirst();
			if (depthAliasOptional.isPresent()) {
				handleVarietyColumnName(depthAliasOptional.get(), 
						messages, templateParamMap);
			} else {
				Optional<String> fromSpaceDepthOptional = headers.stream()
						.filter(header -> FROM_SPACE_DEPTH_ALIAS.equalsIgnoreCase(header))
						.findFirst();
				if (fromSpaceDepthOptional.isPresent()) {
					handleVarietyColumnName(fromSpaceDepthOptional.get(), 
							messages, templateParamMap);
				} else {
					Optional<String> depthFromVariationOptional = headers.stream()
							.filter(header -> DEPTH_FROM_VARIATION_PATTERN.matcher(header).find())
							.findFirst();
					if (depthFromVariationOptional.isPresent()) {
						handleVarietyColumnName(depthFromVariationOptional.get(),
							messages, templateParamMap);	
					} else {
						Optional<String> fromDepthVariationOptional = headers.stream()
								.filter(header -> FROM_DEPTH_VARIATION_PATTERN.matcher(header).find())
								.findFirst();
						if (fromDepthVariationOptional.isPresent()) {
							handleVarietyColumnName(fromDepthVariationOptional.get(),
									messages, templateParamMap);	
						} else {
							String message = Strings.LOG_ERROR_HEADER 
									+ "Template Dl4 H1000 row requires the Depth_From column";
							messages.add(message);
						}
					}
				}
			}
		}
	}

	private void handleVarietyColumnName(final String columnName, final List<String> messages,
			final Map<String, List<String>> templateParamMap) {
		String message = new StringBuilder(Strings.LOG_WARNING_HEADER)
				.append("Template Dl4 H1000 row requires the Depth_From column, found ")
				.append(columnName)
				.append(" column, use it as Depth_From")
				.toString();
		messages.add(message);
		templateParamMap.put(Dl4ColumnHeaders.DEPTH_FROM.getCode(), 
				Arrays.asList(columnName));
	}
}

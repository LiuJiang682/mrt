package au.gov.vic.ecodev.mrt.template.processor.file.validator.sg4;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.fields.Sg4ColumnHeaders;

public class SampleTypeHeaderValidator {

	private static final Pattern SAMPLE_TYPE_VARIATION_PATTERN = Pattern.compile("Sample[ |-]?type", 
			Pattern.CASE_INSENSITIVE);
	
	public void validate(List<String> messages, 
			Map<String, List<String>> templateParamMap, List<String> headers) {
		Optional<String> sampleTypeOptional = headers.stream()
				.filter(header -> Sg4ColumnHeaders.SAMPLE_TYPE.getCode().equalsIgnoreCase(header))
				.findFirst();
		if (sampleTypeOptional.isPresent()) {
			templateParamMap.put(Sg4ColumnHeaders.SAMPLE_TYPE.getCode(), 
					Arrays.asList(sampleTypeOptional.get()));
		} else {
			Optional<String> sampleTypeVariationOptional = headers.stream()
					.filter(header -> SAMPLE_TYPE_VARIATION_PATTERN.matcher(header).find())
					.findFirst();
			if (sampleTypeVariationOptional.isPresent()) {
				String sampleTypeVariation = sampleTypeVariationOptional.get();
				String message = new StringBuilder(Strings.LOG_WARNING_HEADER)
						.append("H1000 requires the Sample_type header, found ")
						.append(sampleTypeVariation)
						.append(", use it as Sample_type")
						.toString();
				messages.add(message);
				templateParamMap.put(Sg4ColumnHeaders.SAMPLE_TYPE.getCode(), 
						Arrays.asList(sampleTypeVariation));
			} else {
				String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
						.append("H1000 requires the Sample_type column")
						.toString();
				messages.add(message);
			}
		}
	}

}

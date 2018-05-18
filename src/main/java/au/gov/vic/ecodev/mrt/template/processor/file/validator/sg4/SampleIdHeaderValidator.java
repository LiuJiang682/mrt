package au.gov.vic.ecodev.mrt.template.processor.file.validator.sg4;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.fields.Sg4ColumnHeaders;

public class SampleIdHeaderValidator {

	private static final Pattern SAMPLE_ID_VARIATION_PATTERN = Pattern.compile("Sample[_|-]?ID", 
			Pattern.CASE_INSENSITIVE);
	
	public void validate(List<String> messages, 
			Map<String, List<String>> templateParamMap, List<String> headers) {
		Optional<String> sampleIdOptional = headers.stream()
				.filter(header -> Sg4ColumnHeaders.SAMPLE_ID.getCode().equalsIgnoreCase(header))
				.findFirst();
		if (sampleIdOptional.isPresent()) {
			templateParamMap.put(Sg4ColumnHeaders.SAMPLE_ID.getCode(), 
					Arrays.asList(sampleIdOptional.get()));
		} else {
			Optional<String> sampleIdVariationOptional = headers.stream()
						.filter(header -> SAMPLE_ID_VARIATION_PATTERN.matcher(header).find())
						.findFirst();
			if (sampleIdVariationOptional.isPresent()) {
				String sampleIdVariation = sampleIdVariationOptional.get();
				String message = new StringBuilder(Strings.LOG_WARNING_HEADER)
						.append("H1000 requires the Sample ID header, found ")
						.append(sampleIdVariation)
						.append(", use it as Sample ID")
						.toString();
				messages.add(message);
				templateParamMap.put(Sg4ColumnHeaders.SAMPLE_ID.getCode(), 
						Arrays.asList(sampleIdVariation));
			} else {
				String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
						.append("H1000 requires the Sample ID column")
						.toString();
				messages.add(message);
			}
		}
	}

}

package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;

public class AzimuthTrueHeaderValidator {

	public static final String MISSING_AZIMUTH_TRUE_COLUMN_MESSAGE = Strings.LOG_ERROR_HEADER 
			+ "H1000 requires the Azimuth_TRUE column";
	private static final String AZIMUTH_TRUE_ALIAS = "TRUE_Azimuth";
	private static final Pattern AZIMUTH_TRUE_VARIATION_PATTERN = 
			Pattern.compile("Azimuth[ |-]?TRUE", Pattern.CASE_INSENSITIVE);
	private static final Pattern TRUE_AZIMUTH_VARIATION_PATTERN = 
			Pattern.compile("TRUE[ |-]?Azimuth", Pattern.CASE_INSENSITIVE);

	public void validate(List<String> messages, Map<String, List<String>> params,
			List<String> headerList) {
		Optional<String> AzimuthTrueOptional = headerList.stream()
				.filter(header -> Strings.AZIMUTH_TRUE.equalsIgnoreCase(header))
				.findFirst();
		if (AzimuthTrueOptional.isPresent()) {
			params.put(Strings.AZIMUTH_TRUE, Arrays.asList(AzimuthTrueOptional.get()));
		} else {
			Optional<String> AzimuthTrueAliasOptional = headerList.stream()
					.filter(header -> AZIMUTH_TRUE_ALIAS.equalsIgnoreCase(header))
					.findFirst();
			if (AzimuthTrueAliasOptional.isPresent()) {
				String message = Strings.LOG_WARNING_HEADER
						+ "H1000 requires the Azimuth_TRUE column, found TRUE_Azimuth column, use it as Azimuth_TRUE";
				messages.add(message);
				params.put(Strings.AZIMUTH_TRUE, Arrays.asList(AzimuthTrueAliasOptional.get()));
			} else {
				Optional<String> azimuthTrueVariation = headerList.stream()
						.filter(header -> AZIMUTH_TRUE_VARIATION_PATTERN.matcher(header).find())
						.findFirst();
				if (azimuthTrueVariation.isPresent()) {
					String variation = azimuthTrueVariation.get();
					String message = Strings.LOG_WARNING_HEADER 
							+ "H1000 requires the Azimuth_TRUE column, found "
							+ variation + " column, use it as Azimuth_TRUE";
					messages.add(message);
					params.put(Strings.AZIMUTH_TRUE, Arrays.asList(variation));
				} else {
					Optional<String> trueAzimuthVariation = headerList.stream()
							.filter(header -> TRUE_AZIMUTH_VARIATION_PATTERN.matcher(header).find())
							.findFirst();
					if (trueAzimuthVariation.isPresent()) {
						String variation = trueAzimuthVariation.get();
						String message = Strings.LOG_WARNING_HEADER 
								+ "H1000 requires the Azimuth_TRUE column, found "
								+ variation + " column, use it as Azimuth_TRUE";
						messages.add(message);
						params.put(Strings.AZIMUTH_TRUE, Arrays.asList(variation));
					} else {
						messages.add(MISSING_AZIMUTH_TRUE_COLUMN_MESSAGE);
					}
				}
			}
		}
	}

}

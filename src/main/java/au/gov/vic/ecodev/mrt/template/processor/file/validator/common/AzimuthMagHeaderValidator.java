package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.fields.SL4ColumnHeaders;

public class AzimuthMagHeaderValidator {

	public static final String MISSING_AZIMUTH_MAG_COLUMN_MESSAGE = Strings.LOG_ERROR_HEADER 
			+ "H1000 requires the Azimuth_MAG column";
	private static final String AZIMUTH_MAG_ALIAS = "MAG_Azimuth";
	private static final Pattern AZIMUTH_MAG_VARIATION_PATTERN = 
			Pattern.compile("Azimuth[ |-]?MAG", Pattern.CASE_INSENSITIVE);
	private static final Pattern MAG_AZIMUTH_VARIATION_PATTERN = 
			Pattern.compile("MAG[ |-]?Azimuth", Pattern.CASE_INSENSITIVE);

	public void validate(List<String> messages, Map<String, List<String>> params, 
			List<String> headerList) {
		Optional<String> azimuthMagOptional =  headerList.stream()
				.filter(header -> SL4ColumnHeaders.AZIMUTH_MAG.getCode().equalsIgnoreCase(header))
				.findFirst();
		if (azimuthMagOptional.isPresent()) {
			params.put(SL4ColumnHeaders.AZIMUTH_MAG.getCode(), 
					Arrays.asList(azimuthMagOptional.get()));
		} else {
			Optional<String> azimuthMagAliasOptional = headerList.stream()
					.filter(header -> AZIMUTH_MAG_ALIAS.equalsIgnoreCase(header))
					.findFirst();
			if (azimuthMagAliasOptional.isPresent()) {
				String message = Strings.LOG_WARNING_HEADER
						+ "H1000 requires the Azimuth_MAG column, found MAG_Azimuth column, use it as Azimuth_MAG";
				messages.add(message);
				params.put(SL4ColumnHeaders.AZIMUTH_MAG.getCode(), 
						Arrays.asList(azimuthMagAliasOptional.get()));
			}  else {
				Optional<String> azimuthMagVariation = headerList.stream()
						.filter(header -> AZIMUTH_MAG_VARIATION_PATTERN.matcher(header).find())
						.findFirst();
				if (azimuthMagVariation.isPresent()) {
					String variation = azimuthMagVariation.get();
					String message = Strings.LOG_WARNING_HEADER 
							+ "H1000 requires the Azimuth_MAG column, found "
							+ variation + " column, use it as Azimuth_MAG";
					messages.add(message);
					params.put(SL4ColumnHeaders.AZIMUTH_MAG.getCode(),
							Arrays.asList(variation));
				} else {
					Optional<String> magAzimuthVariation = headerList.stream()
							.filter(header -> MAG_AZIMUTH_VARIATION_PATTERN.matcher(header).find())
							.findFirst();
					if (magAzimuthVariation.isPresent()) {
						String variation = magAzimuthVariation.get();
						String message = Strings.LOG_WARNING_HEADER 
								+ "H1000 requires the Azimuth_MAG column, found "
								+ variation + " column, use it as Azimuth_MAG";
						messages.add(message);
						params.put(SL4ColumnHeaders.AZIMUTH_MAG.getCode(),
								Arrays.asList(variation));
					} else {
						messages.add(MISSING_AZIMUTH_MAG_COLUMN_MESSAGE);
					}
				}
			}
		}
	}
}

package au.gov.vic.ecodev.mrt.template.processor.file.validator.ds4;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.fields.Ds4ColumnHeaders;

public class SurveyedDepthValidator {

	private static final String DEPTH_SURVEYED_ALIAS = "DepthSurveyed";
	private static final String DEPTH_SPACE_SURVEYED_ALIAS = "Depth Surveyed";
	private static final String SURVEYED_DEPTH_ALIAS = "Depth";

	public void validate(List<String> messages, Map<String, List<String>> templateParamMap, List<String> headers) {
		Optional<String> surveyedDepthOptional = headers.stream()
				.filter(header -> Ds4ColumnHeaders.SURVEYED_DEPTH.getCode().equalsIgnoreCase(header))
				.findFirst();
		if (surveyedDepthOptional.isPresent()) {
			templateParamMap.put(Ds4ColumnHeaders.SURVEYED_DEPTH.getCode(),
					Arrays.asList(surveyedDepthOptional.get()));
		} else {
			Optional<String> surveyedDepthAliasOptional = headers.stream()
					.filter(header -> SURVEYED_DEPTH_ALIAS.equalsIgnoreCase(header))
					.findFirst();
			if (surveyedDepthAliasOptional.isPresent()) {
				String message = Strings.LOG_WARNING_HEADER
						+ "H1000 requires the Surveyed_Depth column, found Depth column, use it as Surveyed_Depth";
				messages.add(message);
				templateParamMap.put(Ds4ColumnHeaders.SURVEYED_DEPTH.getCode(), 
						Arrays.asList(surveyedDepthAliasOptional.get()));
			} else {
				Optional<String> depthSpaceSurveyedAliasOptional = headers.stream()
						.filter(header -> DEPTH_SPACE_SURVEYED_ALIAS.equalsIgnoreCase(header))
						.findFirst();
				if (depthSpaceSurveyedAliasOptional.isPresent()) {
					String message = Strings.LOG_WARNING_HEADER
							+ "H1000 requires the Surveyed_Depth column, found Depth Surveyed column, use it as Surveyed_Depth";
					messages.add(message);
					templateParamMap.put(Ds4ColumnHeaders.SURVEYED_DEPTH.getCode(), 
							Arrays.asList(depthSpaceSurveyedAliasOptional.get()));
				} else {
					Optional<String> depthSurveyedAliasOptional = headers.stream()
							.filter(header -> DEPTH_SURVEYED_ALIAS.equalsIgnoreCase(header))
							.findFirst();
					if (depthSurveyedAliasOptional.isPresent()) {
						String message = Strings.LOG_WARNING_HEADER
								+ "H1000 requires the Surveyed_Depth column, found DepthSurveyed column, use it as Surveyed_Depth";
						messages.add(message);
						templateParamMap.put(Ds4ColumnHeaders.SURVEYED_DEPTH.getCode(), 
								Arrays.asList(depthSurveyedAliasOptional.get()));
					} else {
						Optional<String> surveyedHeaderVariation = headers.stream()
								.filter(header -> header.endsWith(SURVEYED_DEPTH_ALIAS))
								.findFirst();
						if (surveyedHeaderVariation.isPresent()) {
							String variation = surveyedHeaderVariation.get();
							String message = Strings.LOG_WARNING_HEADER 
									+ "H1000 requires the Surveyed_Depth column, found "
									+ variation + " column, use it as Surveyed_Depth";
							messages.add(message);
							templateParamMap.put(Ds4ColumnHeaders.SURVEYED_DEPTH.getCode(),
									Arrays.asList(variation));
						} else {
							String message = Strings.LOG_ERROR_HEADER 
									+ "H1000 requires the Surveyed_Depth column";
							messages.add(message);
						}
					}
				}
			}
		}
	}
}

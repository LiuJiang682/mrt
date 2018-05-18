package au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4;

import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.template.fields.SL4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.AzimuthDataValidator;

public class AzimuthMagValidator {

	private final String[] strs;
	private final int lineNumber;
	private final List<String> columnHeaders;
	private final Map<String, List<String>> templateParamMap;
	
	public AzimuthMagValidator(final String[] strs, int lineNumber, 
			final List<String> columnHeaders, final Map<String, List<String>> templateParamMap) {
		this.strs = strs;
		this.lineNumber = lineNumber;
		this.columnHeaders = columnHeaders;
		if (null == templateParamMap) {
			throw new IllegalArgumentException("AzimuthMagValidator:Parameter templateParamMap cannot be null!");
		}
		this.templateParamMap = templateParamMap;
	}
	
	public void validate(List<String> messages) {
		List<String> azimuthMagLabels = templateParamMap.get(SL4ColumnHeaders.AZIMUTH_MAG.getCode());
		if (CollectionUtils.isEmpty(azimuthMagLabels)) {
			String message = new StringBuilder("Line ")
					.append(lineNumber)
					.append(": Missing Azimuth_MAG from templateParamMap")
					.toString();
			messages.add(message);
			return;
		}
		int azimuthMagIndex = columnHeaders.indexOf(azimuthMagLabels.get(Numeral.ZERO));
		if (Numeral.NOT_FOUND == azimuthMagIndex) {
			int latitudeIndex = columnHeaders.indexOf(SL4ColumnHeaders.LATITUDE.getCode());
			int longitudeIndex = columnHeaders.indexOf(SL4ColumnHeaders.LONGITUDE.getCode());
			if ((Numeral.NOT_FOUND == latitudeIndex) 
					|| (Numeral.NOT_FOUND == longitudeIndex)) {
				messages.add(constructMissingDataErrorMessage(lineNumber, 
						(Numeral.NOT_FOUND == latitudeIndex)));
			}
			return;
		}
		new AzimuthDataValidator(strs, lineNumber, templateParamMap, 
				azimuthMagIndex, SL4ColumnHeaders.AZIMUTH_MAG.getCode())
			.validate(messages);
	}

	private String constructMissingDataErrorMessage(int lineNumber, boolean missingLatitude) {
		String message = new StringBuilder("Line ")
				.append(lineNumber)
				.append(": Missing either Azimuth_MAG or ")
				.append((missingLatitude) ? "Latitude" : "Longitude")
				.toString();
		return message;
	}
}

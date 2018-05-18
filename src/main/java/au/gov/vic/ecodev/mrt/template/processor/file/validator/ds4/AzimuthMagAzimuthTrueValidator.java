package au.gov.vic.ecodev.mrt.template.processor.file.validator.ds4;

import java.util.List;
import java.util.Map;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.fields.Ds4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.AzimuthMagHeaderValidator;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.AzimuthTrueHeaderValidator;

public class AzimuthMagAzimuthTrueValidator {

	public static final String MISSING_AZIMUTH_COLUMN = Strings.LOG_ERROR_HEADER 
			+ "H1000 requires the Azimuth column";

	public void validate(List<String> messages, Map<String, List<String>> params, 
			List<String> headerList) {
		new AzimuthMagHeaderValidator().validate(messages, params, headerList);
		boolean foundAzimuthMag = 
				(null == params.get(Ds4ColumnHeaders.AZIMUTH_MAG.getCode())) ? false : true;
		new AzimuthTrueHeaderValidator().validate(messages, params, headerList);
		if (null == params.get(Strings.AZIMUTH_TRUE)) {
			messages.remove(AzimuthTrueHeaderValidator.MISSING_AZIMUTH_TRUE_COLUMN_MESSAGE);
			if (!foundAzimuthMag) {
				messages.remove(AzimuthMagHeaderValidator.MISSING_AZIMUTH_MAG_COLUMN_MESSAGE);
				messages.add(MISSING_AZIMUTH_COLUMN);
			}
		} else {
			if (!foundAzimuthMag) {
				messages.remove(AzimuthMagHeaderValidator.MISSING_AZIMUTH_MAG_COLUMN_MESSAGE);
			}
		}
	}

}

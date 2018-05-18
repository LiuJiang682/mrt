package au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4;

import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.MandatoryRowValidator;

public class H0532Validator extends MandatoryRowValidator {

	private static final String FIELD_NAME = "H0532";
	private static final String SURFACE_LOCATION_SURVEY_INSTRUMENT_TITLE = "Surface_Location_Survey_Instrument";
	
	@Override
	protected String getTitle() {
		return SURFACE_LOCATION_SURVEY_INSTRUMENT_TITLE;
	}
	@Override
	protected String getFieldName() {
		return FIELD_NAME;
	}
}

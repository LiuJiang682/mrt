package au.gov.vic.ecodev.mrt.template.processor.file.validator.ds4;

import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.MandatoryRowValidator;

public class H0535Validator extends MandatoryRowValidator {

	private static final String FIELD_NAME = "H0535";
	private static final String DOWNHOLE_DIRECTION_SURVEY_COMPANY_TITLE = "Downhole_Direction_Survey_Company";
	
	@Override
	protected String getTitle() {
		return DOWNHOLE_DIRECTION_SURVEY_COMPANY_TITLE;
	}
	@Override
	protected String getFieldName() {
		return FIELD_NAME;
	}
}

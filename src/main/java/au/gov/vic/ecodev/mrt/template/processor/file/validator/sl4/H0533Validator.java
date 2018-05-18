package au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4;

import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.MandatoryRowValidator;

public class H0533Validator extends MandatoryRowValidator {

	@Override
	protected String getTitle() {
		return "Surface_Location_Survey_Company";
	}

	@Override
	protected String getFieldName() {
		return "H0533";
	}

}

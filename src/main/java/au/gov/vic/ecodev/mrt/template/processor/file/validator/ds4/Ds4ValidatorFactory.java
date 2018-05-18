package au.gov.vic.ecodev.mrt.template.processor.file.validator.ds4;

import java.util.List;

import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.MandatoryFieldValidatorFactory;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;

public class Ds4ValidatorFactory {
	
	private static final String TEMPLATE_NAME_DS4 = "DS4:";
	private final TemplateProcessorContext context;
	private final List<String> mandatoryFields;

	public Ds4ValidatorFactory(TemplateProcessorContext context, List<String> mandatoryFields) {
		if (null == context) {
			throw new IllegalArgumentException("Ds4ValidatorFactory: Parameter context cannot be null!");
		}
		this.context = context;
		this.mandatoryFields = mandatoryFields;
	}

	public Validator getLineValidator(final String line) throws Exception {
		Validator validator = new Ds4DefaultValidator();
		
		return new MandatoryFieldValidatorFactory(context, mandatoryFields)
				.getValidator(validator, line, TEMPLATE_NAME_DS4);
	}

}

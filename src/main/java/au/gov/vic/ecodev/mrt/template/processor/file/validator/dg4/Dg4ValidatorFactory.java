package au.gov.vic.ecodev.mrt.template.processor.file.validator.dg4;

import java.util.List;

import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.MandatoryFieldValidatorFactory;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;

public class Dg4ValidatorFactory {

	private static final String TEMPLATE_NAME_DG4 = "DG4:";
	
	private final TemplateProcessorContext context;
	private final List<String> mandatoryFields;
	
	public Dg4ValidatorFactory(TemplateProcessorContext context, List<String> mandatoryFields) {
		if (null == context) {
			throw new IllegalArgumentException("Dg4ValidatorFactory:context parameter cannot be null!");
		}
		this.context = context;
		this.mandatoryFields = mandatoryFields;
	}

	public Validator getLineValidator(String line) throws Exception {
		Validator validator = new Dg4DefaultValidator();
		return new MandatoryFieldValidatorFactory(context, mandatoryFields)
				.getValidator(validator, line, TEMPLATE_NAME_DG4);
	}

}

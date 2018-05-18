/**
 * 
 */
package au.gov.vic.ecodev.mrt.template.processor.file.validator.sg4;

import java.util.List;

import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.MandatoryFieldValidatorFactory;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;

/**
 * @author vicey3n
 *
 */
public class Sg4ValidatorFactory {

	private static final String TEMPLATE_NAME_SG4 = "SG4:";
	private final TemplateProcessorContext context;
	private final List<String> mandatoryFields;
	
	public Sg4ValidatorFactory(TemplateProcessorContext context, List<String> mandatoryFields) {
		if (null == context) {
			throw new IllegalArgumentException("Sg4ValidatorFactory:context parameter cannot be null!");
		}
		this.context = context;
		this.mandatoryFields = mandatoryFields;
	}

	public Validator getLineValidator(String line) throws Exception {
		Validator validator = new Sg4DefaultValidator();
		return new MandatoryFieldValidatorFactory(context, mandatoryFields)
				.getValidator(validator, line, TEMPLATE_NAME_SG4);
	}

}

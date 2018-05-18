package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.context.properties.StringTemplateProperties;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;

public class MandatoryFieldValidatorFactory {

	private final TemplateProcessorContext context;
	private final List<String> mandatoryFields;
	
	public MandatoryFieldValidatorFactory(TemplateProcessorContext context, List<String> mandatoryFields) {
		this.context = context;
		this.mandatoryFields = mandatoryFields;
	}
	
	public Validator getValidator(Validator defaultValidator, 
			final String line, final String templateName) throws Exception {
		if (StringUtils.isNotBlank(line)) {
			String[] strs = line.split(Strings.TAB);
			if ((null != mandatoryFields) 
					&& (mandatoryFields.contains(strs[Numeral.ZERO]))) {
				String propertName = templateName + strs[Numeral.ZERO];
				String className = ((StringTemplateProperties)context
						.getTemplateContextProperty(propertName)).getValue();
				Class<?> cls = Class.forName(className);
				defaultValidator = (Validator) cls.newInstance();
			}
			defaultValidator.init(strs);
		}
		return defaultValidator;
	}
}

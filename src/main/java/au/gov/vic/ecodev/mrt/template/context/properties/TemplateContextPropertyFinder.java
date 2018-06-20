package au.gov.vic.ecodev.mrt.template.context.properties;

import org.springframework.util.StringUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.services.PersistentServices;
import au.gov.vic.ecodev.mrt.template.properties.TemplateProperties;

public class TemplateContextPropertyFinder {

	private final PersistentServices persistentServices;
	private final String templatePropertyName;
	
	public TemplateContextPropertyFinder(final PersistentServices persistentServices,
			final String templatePropertyName) {
		if (null == persistentServices) {
			throw new IllegalArgumentException("Parameter templateContextPropertiesDao cannot be null!");
		}
		if (StringUtils.isEmpty(templatePropertyName)) {
			throw new IllegalArgumentException("Parameter templatePropertyName cannot be null!");
		}
		this.persistentServices = persistentServices;
		this.templatePropertyName = templatePropertyName;
	}

	public TemplateProperties find() throws TemplateProcessorException {
		String[] templateAndProperty = templatePropertyName.split(Strings.COLON);
		if (Numeral.TWO != templateAndProperty.length) {
			throw new TemplateProcessorException("Cannot split templatePropertyName! The templatePropertyName must be in templateName:propertyName format!");
		}
		
		String propertyValue = persistentServices.getTemplateContextProperty(templateAndProperty[au.gov.vic.ecodev.mrt.api.constants.Constants.Numeral.ZERO], 
				templateAndProperty[au.gov.vic.ecodev.mrt.api.constants.Constants.Numeral.ONE]);
		return new DefaultStringTemplateProperties(propertyValue);
	}

	
}

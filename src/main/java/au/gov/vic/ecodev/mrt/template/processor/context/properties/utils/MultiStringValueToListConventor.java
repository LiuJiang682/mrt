package au.gov.vic.ecodev.mrt.template.processor.context.properties.utils;

import java.util.List;

import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.properties.TemplateProperties;
import au.gov.vic.ecodev.template.processor.context.properties.StringToListTemplatePropertiesParser;

public class MultiStringValueToListConventor {

	private final TemplateProcessorContext templateProcessorContext;
	
	public MultiStringValueToListConventor(TemplateProcessorContext templateProcessorContext) {
		this.templateProcessorContext = templateProcessorContext;
	}

	public List<String> getContextProperties(final String propertName, final String delim) throws TemplateProcessorException {
		TemplateProperties multiValueTemplateProperties = templateProcessorContext
				.getTemplateContextProperty(propertName);
		List<String> mandatoryFields = new StringToListTemplatePropertiesParser(multiValueTemplateProperties, 
				delim).parse();
		return mandatoryFields;
	}

}

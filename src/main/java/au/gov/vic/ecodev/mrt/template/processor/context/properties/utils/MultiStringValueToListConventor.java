package au.gov.vic.ecodev.mrt.template.processor.context.properties.utils;

import java.util.List;

import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.context.properties.sl4.Sl4StringToListTemplatePropertiesParser;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.properties.TemplateProperties;

public class MultiStringValueToListConventor {

	private final TemplateProcessorContext templateProcessorContext;
	
	public MultiStringValueToListConventor(TemplateProcessorContext templateProcessorContext) {
		this.templateProcessorContext = templateProcessorContext;
	}

	public List<String> getContextProperties(final String propertName, final String delim) throws TemplateProcessorException {
		TemplateProperties multiValueTemplateProperties = templateProcessorContext
				.getTemplateContextProperty(propertName);
		List<String> mandatoryFields = new Sl4StringToListTemplatePropertiesParser(multiValueTemplateProperties, 
				delim).parse();
		return mandatoryFields;
	}

}

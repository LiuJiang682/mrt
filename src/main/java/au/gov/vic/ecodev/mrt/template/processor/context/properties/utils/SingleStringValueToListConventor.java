package au.gov.vic.ecodev.mrt.template.processor.context.properties.utils;

import java.util.Arrays;
import java.util.List;

import au.gov.vic.ecodev.mrt.template.context.properties.StringTemplateProperties;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.properties.TemplateProperties;

public class SingleStringValueToListConventor {

	private final TemplateProcessorContext templateProcessorContext;
	
	public SingleStringValueToListConventor(TemplateProcessorContext templateProcessorContext) {
		this.templateProcessorContext = templateProcessorContext;
	}

	public List<String> getContextProperties(final String propertName) throws TemplateProcessorException {
		TemplateProperties templateProperties = templateProcessorContext
				.getTemplateContextProperty(propertName);
		return Arrays.asList(((StringTemplateProperties)templateProperties).getValue());
	}

}

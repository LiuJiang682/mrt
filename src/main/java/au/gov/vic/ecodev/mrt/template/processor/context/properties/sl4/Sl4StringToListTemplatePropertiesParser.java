package au.gov.vic.ecodev.mrt.template.processor.context.properties.sl4;

import java.util.Arrays;
import java.util.List;

import org.springframework.util.StringUtils;

import au.gov.vic.ecodev.mrt.template.context.properties.StringTemplateProperties;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.properties.TemplateProperties;

public class Sl4StringToListTemplatePropertiesParser {

	private final TemplateProperties property;
	private final String delim;
	
	public Sl4StringToListTemplatePropertiesParser(TemplateProperties property, String delim) {
		if (null == property) {
			throw new IllegalArgumentException("Parameter property cannot be null!");
		}
		if (StringUtils.isEmpty(delim)) {
			throw new IllegalArgumentException("Parameter delim cannot be null!");
		}
		this.property = property;
		this.delim = delim;
	}

	public List<String> parse() throws TemplateProcessorException {
		if (!(property instanceof StringTemplateProperties)) {
			throw new TemplateProcessorException("Field property is not String property! It is: " + property.getClass().getName());
		}
		String value = ((StringTemplateProperties)property).getValue();
		String[] valueArray = value.split(delim);
		return Arrays.asList(valueArray);
	}

}

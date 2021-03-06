package au.gov.vic.ecodev.mrt.template.context.properties;

import org.springframework.util.StringUtils;

import au.gov.vic.ecodev.template.processor.context.properties.StringTemplateProperties;

public class DefaultStringTemplateProperties implements StringTemplateProperties {

	private final String value;
	
	public DefaultStringTemplateProperties(final String value) {
		if (StringUtils.isEmpty(value)) {
			throw new IllegalArgumentException("Parameter value cannot be empty!");
		}
		this.value = value;
	}
	
	@Override
	public String getValue() {
		return value;
	}

}

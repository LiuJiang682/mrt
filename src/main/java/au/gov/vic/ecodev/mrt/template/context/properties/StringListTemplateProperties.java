package au.gov.vic.ecodev.mrt.template.context.properties;

import java.util.List;

public class StringListTemplateProperties implements ListTemplateProperties<String> {

	private final List<String> values;
	
	public StringListTemplateProperties(List<String> values) {
		this.values = values;
	}

	@Override
	public List<String> getValue() {
		return values;
	}

}

package au.gov.vic.ecodev.mrt.template.context.properties;

import java.util.List;

import au.gov.vic.ecodev.mrt.template.properties.TemplateProperties;

public interface ListTemplateProperties<T> extends TemplateProperties {

	List<T> getValue();

}

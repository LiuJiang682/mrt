package au.gov.vic.ecodev.mrt.template.context.properties;

import au.gov.vic.ecodev.mrt.template.criteria.TemplateCriteria;

public class SqlCriteria implements TemplateCriteria {

	private String searcherClassName;
	private String templateName;
	private Object key;
	
	public SqlCriteria(final String searcherClassName, 
			final String templateName, 
			final Object key) {
		this.searcherClassName = searcherClassName;
		this.templateName = templateName;
		this.key = key;
	}
	
	@Override
	public String getSearcherClassName() {
		return searcherClassName;
	}

	@Override
	public String getTemplateName() {
		return templateName;
	}
	
	public Object getKey() {
		return key;
	}
}

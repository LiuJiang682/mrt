package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import au.gov.vic.ecodev.mrt.dao.TemplateDisplayPropertiesDao;

@Component
public class TemplateDisplayPropertiesHelper {

	private final TemplateDisplayPropertiesDao templateDisplayPropertiesDao;
	
	@Autowired
	public TemplateDisplayPropertiesHelper(final TemplateDisplayPropertiesDao 
			templateDisplayPropertiesDao) {
		if (null == templateDisplayPropertiesDao) {
			throw new IllegalArgumentException("TemplateDisplayPropertiesHelper:templateDisplayPropertiesDao cannot be null!");
		}
		this.templateDisplayPropertiesDao = templateDisplayPropertiesDao;
	}
	
	public Map<String, Object> getTemplateDisplayProperties(final String template) throws Exception {
		String displayProperties = 
				templateDisplayPropertiesDao.getDisplayProperties(template);
		@SuppressWarnings("unchecked")
		Map<String, Object> response = new ObjectMapper().readValue(displayProperties, 
				HashMap.class);
		return response;
	}
}

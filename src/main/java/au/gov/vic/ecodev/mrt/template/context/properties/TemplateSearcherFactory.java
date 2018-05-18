package au.gov.vic.ecodev.mrt.template.context.properties;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.criteria.TemplateCriteria;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.services.PersistentServices;
import au.gov.vic.ecodev.mrt.template.searcher.TemplateSearcher;

public class TemplateSearcherFactory {

	private static final String GET_METHOD_PREFIX = "get";

	private static final String JDBC_TEMPLATE = "jdbcTemplate";

	private static final String PERSISTENT_SERVICES = "persistentServices";

	private static final String PROPERTIES_SUFFIX = ".INIT.FIELDS";

	private static final Logger LOGGER = Logger.getLogger(TemplateSearcherFactory.class);

	private static final String SET_METHOD_PREFIX = "set";
	
	private final PersistentServices persistentServices;
	private final JdbcTemplate jdbcTemplate;
	
	public TemplateSearcherFactory(final PersistentServices persistentServices, 
			final JdbcTemplate jdbcTemplate) {
		if (null == persistentServices) {
			throw new IllegalArgumentException("TemplateSearcherFactory:Parameter persistentServices cannot be null!");
		}
		if (null == jdbcTemplate) {
			throw new IllegalArgumentException("TemplateSearcherFactory:Parameter jdbcTemplate cannot be null!");
		}
		this.persistentServices = persistentServices;
		this.jdbcTemplate = jdbcTemplate;
	}

	public TemplateSearcher getSearcher(TemplateCriteria criteria) throws TemplateProcessorException {
		TemplateSearcher searcher = null;
		String clsName = criteria.getSearcherClassName();
		String templateName = criteria.getTemplateName();
		List<String> fields = getInitFields(templateName, clsName);
		try {
			Map<String, Object> params = getAllParameters(persistentServices, jdbcTemplate, criteria);
			Class<?> searcherClass = Class.forName(clsName);
			searcher = (TemplateSearcher) searcherClass.newInstance();
			doSearcherInitialise(searcher, fields, params);
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException 
				| IllegalArgumentException | NoSuchMethodException | SecurityException 
				| InvocationTargetException e) {
			LOGGER.error(e.getMessage(), e);
			throw new TemplateProcessorException(e.getMessage(), e);
		}
		
		return searcher;
	}

	protected final void doSearcherInitialise(TemplateSearcher searcher, List<String> fields, 
			Map<String, Object> params) throws NoSuchMethodException, 
		SecurityException, IllegalAccessException, IllegalArgumentException, 
		InvocationTargetException {
		Class<?> searcherClass = searcher.getClass();
		Field[] declaredFields = searcherClass.getDeclaredFields();
		List<Field> declaredFieldList = Arrays.asList(declaredFields);
		for (String fieldName : fields) {
			Optional<Field> fieldOptional = declaredFieldList.stream()
					.filter(f -> f.getName().equalsIgnoreCase(fieldName))
					.findFirst();
			if (fieldOptional.isPresent()) {
				Class<?> paramType = fieldOptional.get().getType();
				String methodName = SET_METHOD_PREFIX + StringUtils.capitalize(fieldName);
				Method method = searcherClass.getMethod(methodName, paramType);
				method.invoke(searcher, params.get(fieldName));
			}
		}
	}

	protected final Map<String, Object> getAllParameters(PersistentServices persistentServices, 
			JdbcTemplate jdbcTemplate,
			TemplateCriteria criteria) throws IllegalArgumentException, 
			IllegalAccessException, NoSuchMethodException, SecurityException, 
			InvocationTargetException {
		Map<String, Object> params = new HashMap<>();
		params.put(PERSISTENT_SERVICES, persistentServices);
		params.put(JDBC_TEMPLATE, jdbcTemplate);
		Class<?> cls = criteria.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			LOGGER.debug(field.getName());
			String methodName = GET_METHOD_PREFIX + StringUtils.capitalize(field.getName());
			Method method = cls.getMethod(methodName);
			params.put(field.getName(), method.invoke(criteria));
		}
		return params;
	}

	protected final List<String> getInitFields(final String templateName, final String clsName) {
		List<String> fields = new ArrayList<>();
		String propertyName = clsName + PROPERTIES_SUFFIX;
		String fieldsString = this.persistentServices.getTemplateContextProperty(templateName, 
				propertyName);
		String[] fieldArray = fieldsString.split(Strings.COMMA);
		fields = Arrays.asList(fieldArray);
		return fields;
	}

}

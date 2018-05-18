package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.dao.TemplateConfigDao;

@Component
public class TemplateClassesListHelper {

	private final TemplateConfigDao templateConfigDao;
	
	@Autowired
	public TemplateClassesListHelper(final TemplateConfigDao templateConfigDao) {
		if (null == templateConfigDao) {
			throw new IllegalArgumentException("TemplateClassesListHelper:templateConfigDao cannot be null!");
		}
		this.templateConfigDao = templateConfigDao;
	}

	public List<String> getTemplateClassesList(String template) {
		String classesNames = templateConfigDao.getTemplateClasses(template);
		String[] classesArray = classesNames.split(Strings.COMMA);
		List<String> classesList = Arrays.asList(classesArray);
		List<String> templateClassList = new ArrayList<>();
		classesList.stream()
			.forEach(cls -> {
				String[] templateClasses = cls.split(Strings.COLON);
				templateClassList.add(templateClasses[Numeral.ZERO]);
			});
		return templateClassList;
	}
}

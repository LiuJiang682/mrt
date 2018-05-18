package au.gov.vic.ecodev.mrt.template.processor.updater;

import org.apache.log4j.Logger;

import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.services.PersistentServices;
import au.gov.vic.ecodev.mrt.template.processor.update.TemplateUpdater;

public class TemplateUpdaterFactory {

	private static final Logger LOGGER = Logger.getLogger(TemplateUpdaterFactory.class);
	
	public static TemplateUpdater getTemplateUpdater(Template template, PersistentServices persistentServices) {
		TemplateUpdater templateUpdater = null;
		
		String className = persistentServices.getTemplateUpdaterClass(template.getClass().getSimpleName());
		try {
			Class<?> cls = Class.forName(className);
			templateUpdater = (TemplateUpdater) cls.newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NullPointerException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return templateUpdater;
	}

}

package au.gov.vic.ecodev.mrt.template.files;

import au.gov.vic.ecodev.mrt.template.file.TemplateFileSelector;

public class TemplateFileSelectorFactory {

	public static TemplateFileSelector getTemplateFileSelector(final String fileSelectorClassName) throws Exception {
		Class<?> cls = Class.forName(fileSelectorClassName);
		return (TemplateFileSelector) cls.newInstance();
	}

}

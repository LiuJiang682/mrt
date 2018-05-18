package au.gov.vic.ecodev.mrt.template.processor;

public class TemplateProcessorFactory {

	public static TemplateProcessor getProcessor(final String fullClassName) throws Exception {
		
		Class<?> cls = Class.forName(fullClassName);
		return (TemplateProcessor) cls.newInstance();
	}

}

package au.gov.vic.ecodev.mrt.rest.service.template;

import java.util.List;
import java.util.Map;

public interface TemplateDataServices {

	public Map<String, List<Map<String, Object>>> getAllTemplateData(final String batchId) throws Exception;

	public List<String> getSingleTemplateData(final String templateName, final String batchId);
}

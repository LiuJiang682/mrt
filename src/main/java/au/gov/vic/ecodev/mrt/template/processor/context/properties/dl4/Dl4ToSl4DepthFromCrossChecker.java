package au.gov.vic.ecodev.mrt.template.processor.context.properties.dl4;

import java.util.List;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.api.constants.LogSeverity;
import au.gov.vic.ecodev.mrt.template.context.properties.SqlCriteria;
import au.gov.vic.ecodev.mrt.template.context.properties.StringListTemplateProperties;
import au.gov.vic.ecodev.mrt.template.criteria.TemplateCriteria;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;

public class Dl4ToSl4DepthFromCrossChecker {

	private static final String TEMPLATE_NAME_DL4 = "DL4";
	private final TemplateProcessorContext context;
	
	public Dl4ToSl4DepthFromCrossChecker(TemplateProcessorContext context) {
		if (null == context) {
			throw new IllegalArgumentException("Ds4ToSl4HoleIdCrossChecker:context parameter cannot be null!");
		}
		this.context = context;
	}

	public boolean doDepthFromCrossCheck(long sessionId) throws TemplateProcessorException {
		boolean passCrossCheck = false;
		TemplateCriteria depthFromCriteria = new SqlCriteria(LithologyDepthFromGtTotalDepthSearcher.class.getName(),
				TEMPLATE_NAME_DL4, sessionId);
		List<String> holeIDs = ((StringListTemplateProperties)context
				.search(depthFromCriteria)).getValue();
		if (CollectionUtils.isEmpty(holeIDs)) {
			passCrossCheck = true;
		} else {
			holeIDs.stream()
				.forEach(holeId -> {
					String logMessage = new StringBuilder("Hole_id: ")
							.append(holeId)
							.append(" Depth_from is greater than total depth!")
							.toString();
					context.saveStatusLog(LogSeverity.ERROR, logMessage);
				});
		}
		return passCrossCheck;
	}

}

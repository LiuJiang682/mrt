package au.gov.vic.ecodev.mrt.template.processor.context.properties.dg4;

import java.util.List;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.LogSeverity;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.context.properties.SqlCriteria;
import au.gov.vic.ecodev.mrt.template.context.properties.StringListTemplateProperties;
import au.gov.vic.ecodev.mrt.template.criteria.TemplateCriteria;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;

public class Dg4ToSl4ToCrossChecker {

	private final TemplateProcessorContext context;
	
	public Dg4ToSl4ToCrossChecker(TemplateProcessorContext context) {
		if (null == context) {
			throw new IllegalArgumentException("Dg4ToSl4ToCrossChecker:context parameter cannot be null!");
		}
		this.context = context;
	}

	public boolean doDepthCrossCheck(long sessionId) throws TemplateProcessorException {
		boolean passCrossCheck = false;
		TemplateCriteria toCriteria = new SqlCriteria(GeoChemistryToGtTotalDepthSearcher.class.getName(),
				Strings.TEMPLATE_NAME_DG4, sessionId);
		List<String> holeIDs = ((StringListTemplateProperties)context
				.search(toCriteria)).getValue();
		if (CollectionUtils.isEmpty(holeIDs)) {
			passCrossCheck = true;
		} else {
			holeIDs.stream()
				.forEach(holeId -> {
					String logMessage = new StringBuilder("Hole_id: ")
							.append(holeId)
							.append(" From is greater than total depth!")
							.toString();
					context.saveStatusLog(LogSeverity.ERROR, logMessage);
				});
		}
		return passCrossCheck;
	}

}

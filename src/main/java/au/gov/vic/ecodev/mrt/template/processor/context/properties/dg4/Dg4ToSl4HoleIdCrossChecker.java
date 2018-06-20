package au.gov.vic.ecodev.mrt.template.processor.context.properties.dg4;

import java.util.List;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.api.constants.LogSeverity;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.context.properties.SqlCriteria;
import au.gov.vic.ecodev.mrt.template.context.properties.StringListTemplateProperties;
import au.gov.vic.ecodev.mrt.template.criteria.TemplateCriteria;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;

public class Dg4ToSl4HoleIdCrossChecker {
	
	private final TemplateProcessorContext context;
	
	public Dg4ToSl4HoleIdCrossChecker(TemplateProcessorContext context) {
		if (null == context) {
			throw new IllegalArgumentException("Dg4ToSl4HoleIdCrossChecker:context parameter cannot be null!");
		}
		this.context = context;
	}

	public boolean doHoleIdCrossCheck(long sessionId) throws TemplateProcessorException {
		boolean passCrossCheck = false;
		TemplateCriteria holdIdNotInBoreHoleCriteria = 
				new SqlCriteria(GeoChemistryHoleIdNotInBoreHoleSearcher.class.getName(), 
				Strings.TEMPLATE_NAME_DG4, sessionId);
		List<String> holeIDs = ((StringListTemplateProperties)context
				.search(holdIdNotInBoreHoleCriteria)).getValue();
		if (CollectionUtils.isEmpty(holeIDs)) {
			passCrossCheck = true;
		} else {
			holeIDs.stream()
				.forEach(holeId -> {
					String logMessage = new StringBuilder("Hole_id: ")
							.append(holeId)
							.append(" exist in DG4 but missing in SL4")
							.toString();
					context.saveStatusLog(LogSeverity.ERROR, logMessage);
				});
		}
		return passCrossCheck;
	}

}

package au.gov.vic.ecodev.mrt.template.processor.updater.helper;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class ProjectionZoneExtractionHelper {

	private static final String KEY_H0531 = "H0531";
	
	private final Template template;
	
	public ProjectionZoneExtractionHelper(final Template template) {
		if (null == template) {
			throw new IllegalArgumentException("ProjectionZoneExtractionHelper: template parameter cannot be null!");
		}
		this.template = template;
	}
	
	public final BigDecimal extractAmgZoneFromTemplate() {
		BigDecimal amgZone = null;
		List<String> projectZoneList = template.get(KEY_H0531);
		if ((!CollectionUtils.isEmpty(projectZoneList)) 
				&& (Numeral.ONE < projectZoneList.size())) {
			String projectZone = projectZoneList.get(Numeral.ONE);
			try {
				amgZone = new BigDecimal(projectZone);
			} catch (NumberFormatException e) {
				//Ignore exception
			}
		}
		return amgZone;
	}
}

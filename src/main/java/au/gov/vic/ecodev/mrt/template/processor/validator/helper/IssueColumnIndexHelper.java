package au.gov.vic.ecodev.mrt.template.processor.validator.helper;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;

public class IssueColumnIndexHelper {

	private static final Logger LOGGER = Logger.getLogger(IssueColumnIndexHelper.class);
	
	public final int getIssueColumnIndex(List<String> list) {
		int issueColumnIndex = Numeral.NOT_FOUND;
		
		if (CollectionUtils.isNotEmpty(list)) {
			try {
				issueColumnIndex = Integer.parseInt(list.get(Numeral.ZERO));
			} catch (NumberFormatException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		return issueColumnIndex;
	}
}

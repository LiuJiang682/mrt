package au.gov.vic.ecodev.mrt.template.processor.updater;

import java.util.List;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.api.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class NumberOfRecordsTemplateExtractor {

	private static final String KEY_H0203 = "H0203";
	
	public int extractNumOfRecordsFromTemplate(Template template) {
		int numOfRecords = Numeral.ZERO;
		List<String> numOfDataRecordsList = template.get(KEY_H0203);
		if ((!CollectionUtils.isEmpty(numOfDataRecordsList)) 
				&& (Numeral.ONE < numOfDataRecordsList.size())) {
			String numOfDataRecordsString = numOfDataRecordsList.get(Numeral.ONE);
			try {
				numOfRecords = Integer.parseInt(numOfDataRecordsString);
			} catch (NumberFormatException e) {
				//Ignore exception
			}
		}
		return numOfRecords;
	}

}

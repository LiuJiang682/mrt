package au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4;

import java.util.List;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;

public class DataRecordNumberValidator {

	private final List<String> expectedRecordsList;
	private final List<String> actualRecordsList;
	
	public DataRecordNumberValidator(final List<String> expectedRecordsList, 
			final List<String> actualRecordsList) {
		this.expectedRecordsList = expectedRecordsList;
		this.actualRecordsList = actualRecordsList;
	}
	
	public void validate(final List<String> messages) {
		if (CollectionUtils.isEmpty(expectedRecordsList)) {
			String Message = new StringBuilder("No ")
					.append(Strings.NUMBER_OF_DATA_RECORDS_TITLE)
					.append(" in the templateParamMap")
					.toString();
			messages.add(Message);
		} else if (CollectionUtils.isEmpty(actualRecordsList)) {
			String Message = new StringBuilder("No ")
					.append(Strings.NUMBER_OF_DATA_RECORDS_ADDED)
					.append(" in the templateParamMap")
					.toString();
			messages.add(Message);
		} else if (!expectedRecordsList.get(Numeral.ZERO).equals(actualRecordsList.get(Numeral.ZERO))) {
			String Message = new StringBuilder("Number_of_data_records in the templateParamMap is ")
					.append(expectedRecordsList.get(Numeral.ZERO))
					.append(" and Number_of_records_added is ")
					.append(actualRecordsList.get(Numeral.ZERO))
					.toString();
			messages.add(Message);
		}
	}
}

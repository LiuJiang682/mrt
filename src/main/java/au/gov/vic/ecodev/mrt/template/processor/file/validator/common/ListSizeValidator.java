package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.util.List;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;

public class ListSizeValidator {
	
	private final List<String> headers;
	
	public ListSizeValidator(List<String> headers) {
		this.headers = headers;
	}

	public int validate(List<String> messages) {
		int count = Numeral.INVALID_COLUMN_COUNT;
		if (!CollectionUtils.isEmpty(headers)) {
			count = headers.size();
		}
		
		if (Numeral.INVALID_COLUMN_COUNT == count) {
			String message = "No column header has been passing down";
			messages.add(message);
		}
		return count;
	}

}

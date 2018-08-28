package au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.helper.H0100FieldHelper;

public class H0100FieldLookupValidator {

	private final String[] strs;
	private final int lineNumber;
	private final Map<String, List<String>> templateParamMap;
	
	public H0100FieldLookupValidator(String[] strs, int lineNumber, 
			Map<String, List<String>> templateParamMap) {
		this.strs = strs;
		this.lineNumber = lineNumber;
		if (null == templateParamMap) {
			throw new IllegalArgumentException("H0100FieldLookupValidator:templateParamMap parameter cannot be null!");
		}
		this.templateParamMap = templateParamMap;
	}

	public void validate(List<String> messages) {
		List<String> h0100List = templateParamMap.get(Strings.KEY_H0100);
		if (CollectionUtils.isNotEmpty(h0100List) 
				&& (Numeral.ONE < h0100List.size())) {
			List<String> h1000List = templateParamMap.get(Strings.COLUMN_HEADERS);
			if (CollectionUtils.isNotEmpty(h1000List)) {
				int index = new H0100FieldHelper().getHeaderIndex(h1000List);
				if (Numeral.NOT_FOUND != index) {
					++index;
					if (strs.length <= index) {
						String message =  new StringBuilder(Strings.LOG_ERROR_HEADER)
								.append("Line ")
								.append(lineNumber)
								.append(": Missing Tenement_no data while h0100 has multiply values.")
								.toString();
						messages.add(message);
					} else {
						String tenementNo = strs[index];
						if (!h0100List.contains(tenementNo)) {
							String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
									.append("Line ")
									.append(lineNumber)
									.append(": Expected Tenement_no ")
									.append(String.join(Strings.COMMA, h0100List))
									.append(", but got: ")
									.append(tenementNo)
									.toString();
							messages.add(message);
						}
					}
				}
			}
		}
	}

}

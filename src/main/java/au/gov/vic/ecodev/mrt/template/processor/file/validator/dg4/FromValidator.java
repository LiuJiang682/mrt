package au.gov.vic.ecodev.mrt.template.processor.file.validator.dg4;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;

import au.gov.vic.ecodev.mrt.common.db.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.fields.Dg4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.NumberStringValidator;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ErrorMessageChecker;

public class FromValidator {

	private final String[] strs;
	private final int lineNumber;
	private final List<String> columnHeaders;
	private Map<String, List<String>> templateParamMap;
	
	
	public FromValidator(String[] strs, int lineNumber, List<String> columnHeaders, 
			Map<String, List<String>> templateParamMap) {
		this.strs = strs;
		this.lineNumber = lineNumber;
		if (null == columnHeaders) {
			throw new IllegalArgumentException("FromValidator:columnHeaders cannot not be null!");
		}
		this.columnHeaders = columnHeaders;
		if (null == templateParamMap) {
			throw new IllegalArgumentException("FromValidator:templateParamMap cannot not be null!");
		}
		this.templateParamMap = templateParamMap;
	}

	public void validate(List<String> messages) {
		Optional<String> fromOptional = columnHeaders.stream()
				.filter(column -> Dg4ColumnHeaders.FROM.getCode().equalsIgnoreCase(column))
				.findFirst();
		if (fromOptional.isPresent()) {
			String fromHeader = fromOptional.get();
			doFromValueCheck(messages, fromHeader);
		} else {
			List<String> fromVariations = templateParamMap.get(Strings.TITLE_PREFIX + Dg4ColumnHeaders.FROM.getCode());
			if (CollectionUtils.isEmpty(fromVariations)) {
				String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
					.append("Line ")
					.append(lineNumber)
					.append(": Template DG4 missing From column")
					.toString();
				messages.add(message);
			} else {
				String fromHeader = fromVariations.get(Numeral.ZERO);
				doFromValueCheck(messages, fromHeader);
			}
		}
		
	}

	protected final void doFromValueCheck(List<String> messages, String fromHeader) {
		int index = columnHeaders.indexOf(fromHeader);
		String dataString = strs[++index];
		new NumberStringValidator(dataString, Strings.TEMPLATE_NAME_DG4, fromHeader,
				lineNumber).validate(messages);
		boolean hasErrorMessage = new ErrorMessageChecker(messages).isContainsErrorMessages();
		if (!hasErrorMessage) {
			templateParamMap.put(Dg4ColumnHeaders.FROM.getCode(), Arrays.asList(dataString));
		}
	}

}

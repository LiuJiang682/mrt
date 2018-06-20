package au.gov.vic.ecodev.mrt.template.processor.file.validator.dg4;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.fields.Dg4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.NumberStringValidator;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ErrorMessageChecker;

public class ToValidator {

	private final String[] strs;
	private final int lineNumber;
	private final List<String> columnHeaders;
	private Map<String, List<String>> templateParamMap;
	
	public ToValidator(String[] strs, int lineNumber, List<String> columnHeaders,
			Map<String, List<String>> templateParamMap) {
		this.strs = strs;
		this.lineNumber = lineNumber;
		if (null == columnHeaders) {
			throw new IllegalArgumentException("ToValidator:columnHeaders cannot not be null!");
		}
		this.columnHeaders = columnHeaders;
		if (null == templateParamMap) {
			throw new IllegalArgumentException("ToValidator:templateParamMap cannot not be null!");
		}
		this.templateParamMap = templateParamMap;
	}

	public void validate(List<String> messages) {
		Optional<String> toOptional = columnHeaders.stream()
				.filter(to -> Dg4ColumnHeaders.TO.getCode().equalsIgnoreCase(to))
				.findFirst();
		if (toOptional.isPresent()) {
			int index = columnHeaders.indexOf(toOptional.get());
			String dataString = strs[++index];
			new NumberStringValidator(dataString, Strings.TEMPLATE_NAME_DG4, toOptional.get(),
					lineNumber).validate(messages);
			boolean hasErrorMessage = new ErrorMessageChecker(messages).isContainsErrorMessages();
			if (!hasErrorMessage) {
				List<String> fromList = templateParamMap.get(Dg4ColumnHeaders.FROM.getCode());
				if (CollectionUtils.isEmpty(fromList)) {
					String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
							.append("Line ")
							.append(lineNumber)
							.append(": Template DG4 missing From column data")
							.toString();
					messages.add(message); 
				} else {
					doFromToValudeComparision(messages, dataString, fromList.get(Numeral.ZERO));
				}
			}
		} else {
			String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
					.append("Line ")
					.append(lineNumber)
					.append(": Template DG4 missing To column")
					.toString();
			messages.add(message);
		}
		
	}

	private void doFromToValudeComparision(List<String> messages, String dataString, 
			String fromString) {
		BigDecimal from = new BigDecimal(fromString);
		BigDecimal to = new BigDecimal(dataString);
		if (au.gov.vic.ecodev.mrt.api.constants.Constants.Numeral.NEGATIVE_ONE == to.compareTo(from)) {
			String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
					.append("Line ")
					.append(lineNumber)
					.append(": Template DG4 To column is expected less than From, but it is greater. From: ")
					.append(fromString)
					.append(", To: ")
					.append(dataString)
					.toString();
			messages.add(message);
		}
	}

}

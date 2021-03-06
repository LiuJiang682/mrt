package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.StringUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.updater.helper.VariationHelper;

public class MandatoryStringDataValidator {

	private final String[] strs;
	private final int lineNumber;
	private final List<String> columnHeaders;
	private final String code;
	private final String templateName;
	
	public MandatoryStringDataValidator(String[] strs, int lineNumber, List<String> columnHeaders,
			final String code, final String templateName) {
		this.strs = strs;
		this.lineNumber = lineNumber;
		this.columnHeaders = columnHeaders;
		this.code = code;
		this.templateName = templateName;
	}

	public void validate(List<String> messages) {
		int index = columnHeaders.stream()
				.map(String::toUpperCase)
				.collect(Collectors.toList())
				.indexOf(code.toUpperCase());
		if (Numeral.NOT_FOUND == index) {
			if ((Stream.of(Strings.SPACE, Strings.UNDER_LINE, Strings.HYPHEN)
					.anyMatch(code::contains))
					&& (CollectionUtils.isNotEmpty(columnHeaders))) {
				index = new VariationHelper(columnHeaders, code).findIndex();
				if (Numeral.NOT_FOUND == index)  {
					messages.add(constructMissingHoleIdMessage(lineNumber));
					return;
				}
			} else {
				messages.add(constructMissingHoleIdMessage(lineNumber));
				return;
			}
		}
	
		++index;
		String string = null;
		if (index < strs.length) {
			string = strs[index];
		}
		if (StringUtils.isEmpty(string)) {
			String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
					.append("Line ")
					.append(lineNumber)
					.append(": Template ")
					.append(templateName)
					.append(" column ")
					.append(code)
					.append(" cannot be null or empty")
					.toString();
			messages.add(message);
		}
	}

	private String constructMissingHoleIdMessage(int currentLineNumber) {
		String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
				.append("Line ")
				.append(currentLineNumber)
				.append(": Template ")
				.append(templateName)
				.append(" missing ")
				.append(code)
				.append(" column")
				.toString();
		return message;
	}
}

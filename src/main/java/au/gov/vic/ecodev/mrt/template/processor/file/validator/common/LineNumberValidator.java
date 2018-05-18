package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.util.List;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;

public class LineNumberValidator {

	private final List<String> lineNumberStrings;
	
	public LineNumberValidator(List<String> lineNumberStrings) {
		this.lineNumberStrings = lineNumberStrings;
	}

	public int validate(List<String> messages) {
		int lineNumber = Numeral.INVALID_LINE_NUMBER;
		if (CollectionUtils.isEmpty(lineNumberStrings)) {
			String message = "Not current line number has been passing down!";
			messages.add(message);
		} else {
			try {
				lineNumber = Integer.parseInt(lineNumberStrings.get(Numeral.ZERO));
			} catch (Exception e) {
				String message = "Expected line number is a number, but got: " + lineNumberStrings.get(Numeral.ZERO);
				messages.add(message);
			}
		}
		
		return lineNumber;
	}

}

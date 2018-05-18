package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.util.List;

import org.springframework.util.StringUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;

public class EastingValidator {

	private final String[] strs;
	private final int lineNumber;
	private final List<String> columnHeaders;
	private final String mgaCode;
	private final String amgCode;
	
	public EastingValidator(String[] strs, int lineNumber, List<String> columnHeaders, 
			String mgaCode, String amgCode) {
		this.strs = strs;
		this.lineNumber = lineNumber;
		this.columnHeaders = columnHeaders;
		if (StringUtils.isEmpty(mgaCode)) {
			throw new IllegalArgumentException("EastingValidator:mgaCode parameter cannot be null or empty!");
		}
		this.mgaCode = mgaCode;
		if (StringUtils.isEmpty(amgCode)) {
			throw new IllegalArgumentException("EastingValidator:amgCode parameter cannot be null or empty!");
		}
		this.amgCode = amgCode;
	}

	public void validate(List<String> errorMessages) {
		boolean useAmgE = false;
		int mgaEIndex = columnHeaders.indexOf(mgaCode);
		if (Numeral.NOT_FOUND == mgaEIndex) {
			useAmgE = true;
			mgaEIndex = columnHeaders.indexOf(amgCode);
		}
		
		new GeodeticDataFormateValidator(strs, lineNumber, 
				useAmgE ? amgCode:mgaCode, mgaEIndex)
			.validate(errorMessages);
	}

}

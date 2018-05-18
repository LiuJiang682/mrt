package au.gov.vic.ecodev.mrt.template.processor.file.validator.ds4;

import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.fields.Ds4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.AzimuthDataValidator;

public class AzimuthValidator {
	
	private final String[] strs;
	private final int lineNumber;
	private final List<String> headerList;
	private final Map<String, List<String>> params;

	public AzimuthValidator(String[] strs, int lineNumber, List<String> headerList, 
			Map<String, List<String>> params) {
		this.strs = strs;
		this.lineNumber = lineNumber;
		this.headerList = headerList;
		if (null == params) {
			throw new IllegalArgumentException("AzimuthValidator: Parameter params cannot be null!");
		}
		this.params = params;
	}

	public void validate(List<String> messages) {
		List<String> azimuthMagLabels = params.get(Ds4ColumnHeaders.AZIMUTH_MAG.getCode());
		List<String> azimuthTrueLabels = params.get(Strings.AZIMUTH_TRUE);
		
		if (CollectionUtils.isEmpty(azimuthMagLabels) 
				&& (CollectionUtils.isEmpty(azimuthTrueLabels))) {
			messages.add(constructMissingDataErrorMessage(lineNumber, 
					"either Azimuth_MAG or Azimuth_TRUE"));
		} else {
			if (!CollectionUtils.isEmpty(azimuthMagLabels)) {
				doIndividualFieldValidation(messages, azimuthMagLabels, Ds4ColumnHeaders.AZIMUTH_MAG.getCode());
			}
			if (!CollectionUtils.isEmpty(azimuthTrueLabels)) {
				doIndividualFieldValidation(messages, azimuthTrueLabels, Strings.AZIMUTH_TRUE);
			}
		}
	}

	private void doIndividualFieldValidation(List<String> messages, 
			List<String> azimuthLabels, String columnName) {
		int azimuthIndex = headerList.indexOf(azimuthLabels.get(Numeral.ZERO));
		if (Numeral.NOT_FOUND == azimuthIndex) {
			messages.add(constructMissingDataErrorMessage(lineNumber, 
					columnName));
			return;
		}
		new AzimuthDataValidator(strs, lineNumber, params, azimuthIndex, columnName)
			.validate(messages);
	}
	

	private String constructMissingDataErrorMessage(int lineNumber, String columnName) {
		String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
				.append("Line ")
				.append(lineNumber)
				.append(":  Missing ")
				.append(columnName)
				.toString();
		return message;
	}
}

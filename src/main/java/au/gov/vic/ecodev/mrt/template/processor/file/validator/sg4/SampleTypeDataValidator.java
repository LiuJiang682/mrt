package au.gov.vic.ecodev.mrt.template.processor.file.validator.sg4;

import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.fields.Sg4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.MandatoryStringDataValidator;

public class SampleTypeDataValidator {

	private final String[] strs;
	private final int lineNumber;
	private final List<String> columnHeaders;
	private Map<String, List<String>> templateParamMap;
	
	public SampleTypeDataValidator(String[] strs, int lineNumber, List<String> columnHeaders,
			Map<String, List<String>> templateParamMap) {
		this.strs = strs;
		this.lineNumber = lineNumber;
		if (null == columnHeaders) {
			throw new IllegalArgumentException("SampleIdDataValidator:columnHeaders cannot not be null!");
		}
		this.columnHeaders = columnHeaders;
		if (null == templateParamMap) {
			throw new IllegalArgumentException("SampleIdDataValidator:templateParamMap cannot not be null!");
		}
		this.templateParamMap = templateParamMap;
	}

	public void validate(List<String> messages) {
		List<String> sampleIdLableList = templateParamMap.get(Sg4ColumnHeaders.SAMPLE_TYPE.getCode());
		if (CollectionUtils.isEmpty(sampleIdLableList)) {
			String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
					.append("Line ")
					.append(lineNumber)
					.append(": Template SG4 missing ")
					.append(Sg4ColumnHeaders.SAMPLE_TYPE.getCode())
					.append(" column label from parameter")
					.toString();
			messages.add(message);
		} else {
			new MandatoryStringDataValidator(strs, lineNumber, columnHeaders,
					sampleIdLableList.get(Numeral.ZERO), Strings.TEMPLATE_NAME_SG4).validate(messages);
		}
	}

}

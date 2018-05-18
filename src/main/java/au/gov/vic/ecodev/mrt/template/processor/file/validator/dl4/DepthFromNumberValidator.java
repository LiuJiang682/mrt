package au.gov.vic.ecodev.mrt.template.processor.file.validator.dl4;

import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.fields.Dl4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.NumberStringValidator;

public class DepthFromNumberValidator  {

	private static final String TEMPLATE_NAME_DL4 = "Dl4";
	private final String[] strs;
	private final int lineNumber;
	private final List<String> columnHeaders;
	private final Map<String, List<String>> templateParamMap;
	
	public DepthFromNumberValidator(String[] strs, int lineNumber, List<String> columnHeaders,
			Map<String, List<String>> templateParamMap) {
		this.strs = strs;
		this.lineNumber = lineNumber;
		this.columnHeaders = columnHeaders;
		if (null == templateParamMap) {
			throw new IllegalArgumentException("DepthFromNumberValidator:Parameter params cannot be null!");
		}
		this.templateParamMap = templateParamMap;
	}

	public void validate(List<String> messages) {
		List<String> depthFromLabel = templateParamMap.get(Dl4ColumnHeaders.DEPTH_FROM.getCode());
		if (CollectionUtils.isEmpty(depthFromLabel)) {
			String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
					.append("Line ")
					.append(lineNumber)
					.append(": Template Dl4 missing ")
					.append(Dl4ColumnHeaders.DEPTH_FROM.getCode())
					.append(" column label from parameter")
					.toString();
			messages.add(message);
		} else {
			int index = columnHeaders.indexOf(depthFromLabel.get(Numeral.ZERO));
			if (Numeral.NOT_FOUND == index) {
				String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
						.append("Line ")
						.append(lineNumber)
						.append(": Template Dl4 missing ")
						.append(Dl4ColumnHeaders.DEPTH_FROM.getCode())
						.append(" column")
						.toString();
				messages.add(message);
			} else {
				new NumberStringValidator(strs[++index], TEMPLATE_NAME_DL4, 
						Dl4ColumnHeaders.DEPTH_FROM.getCode(),
						lineNumber).validate(messages);
			}
		}
		
	}

}

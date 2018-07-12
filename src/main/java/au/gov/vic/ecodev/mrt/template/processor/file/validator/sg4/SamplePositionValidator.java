package au.gov.vic.ecodev.mrt.template.processor.file.validator.sg4;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.fields.Sg4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.PositionValidatorBase;

public class SamplePositionValidator extends PositionValidatorBase {
	
	public SamplePositionValidator(String[] strs, long lineNumber, Map<String, List<String>> templateParamMap, 
			TemplateProcessorContext context) {
		super(strs, lineNumber, templateParamMap, context);
	}

	protected final void handleNoTenementFound(List<String> messages, long lineNumber, 
			String sample, TemplateProcessorContext context,
			BigDecimal easting, BigDecimal northing, List<String> tenementNoList) {
		contructNoTenementFoundWarningMessage(messages, lineNumber, sample, easting, northing, 
				tenementNoList);
		List<String> sampleIds = context.getMessage().getSampleIdsOutSideTenement();
		sampleIds.add(sample);
		getTemplateParamMap().put(Strings.ISSUE_COLUMN_INDEX, Arrays.asList(String.valueOf(Numeral.ZERO)));
	}
	
	protected final void contructErrorMessage(List<String> messages, long lineNumber, BigDecimal easting, 
			BigDecimal northing) {
		String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
				.append("Line number ")
				.append(lineNumber)
				.append("'s sample coordinate ")
				.append(easting.doubleValue())
				.append(Strings.COMMA)
				.append(Strings.SPACE)
				.append(northing.doubleValue())
				.append(" are not inside Victoria")
				.toString();
		messages.add(message);
	}
	
	protected final String getIdCode() {
		return Sg4ColumnHeaders.SAMPLE_ID.getCode();
	}
	
	private void contructNoTenementFoundWarningMessage(List<String> messages, long lineNumber, String sampleId,
			BigDecimal x, BigDecimal y, List<String> tenementNoList) {
		String message = new StringBuilder(Strings.LOG_WARNING_HEADER)
				.append("Line number ")
				.append(lineNumber)
				.append(" Sample ID: ")
				.append(sampleId)
				.append(", coordinate: ")
				.append(x)
				.append(", ")
				.append(y)
				.append(" is not inside the tenement: ")
				.append(String.join(Strings.COMMA, tenementNoList))
				.toString();
		messages.add(message);
		
	}
}

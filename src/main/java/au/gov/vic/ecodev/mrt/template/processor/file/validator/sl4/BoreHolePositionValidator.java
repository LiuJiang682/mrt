package au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.fields.SL4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.PositionValidatorBase;

public class BoreHolePositionValidator extends PositionValidatorBase {
	
	public BoreHolePositionValidator(String[] strs, long lineNumber, Map<String, List<String>> templateParamMap, 
			TemplateProcessorContext context) {
		super(strs, lineNumber, templateParamMap, context);
	}

	protected final String getIdCode() {
		return SL4ColumnHeaders.HOLE_ID.getCode();
	}

	protected final void handleNoTenementFound(List<String> messages, long lineNumber, 
			String boreHoleId, TemplateProcessorContext context,
			BigDecimal easting, BigDecimal northing, List<String> tenementNoList) {
		contructNoTenementFoundWarningMessage(messages, lineNumber, boreHoleId, easting, northing, 
				tenementNoList);
		List<String> boreHoleIds = context.getMessage().getBoreHoleIdsOutSideTenement();
		boreHoleIds.add(boreHoleId);
		//Set the warning column for display.
		getTemplateParamMap().put(Strings.ISSUE_COLUMN_INDEX, Arrays.asList(String.valueOf(Numeral.ZERO)));
	}
	
	protected void contructErrorMessage(List<String> messages, long lineNumber, BigDecimal easting, 
			BigDecimal northing) {
		String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
				.append("Line number ")
				.append(lineNumber)
				.append("'s bore hole coordinate ")
				.append(easting.doubleValue())
				.append(Strings.COMMA)
				.append(Strings.SPACE)
				.append(northing.doubleValue())
				.append(" are not inside Victoria")
				.toString();
		messages.add(message);
	}
	
	protected void contructNoTenementFoundWarningMessage(List<String> messages, long lineNumber, String boreHoleId,
			BigDecimal x, BigDecimal y, List<String> tenementNoList) {
		String message = new StringBuilder(Strings.LOG_WARNING_HEADER)
				.append("Line number ")
				.append(lineNumber)
				.append(" bore hole ID: ")
				.append(boreHoleId)
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

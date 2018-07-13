package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import au.gov.vic.ecodev.common.util.NullSafeCollectionsMapUtils;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4.DataRecordNumberValidator;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ErrorMessageChecker;
import au.gov.vic.ecodev.utils.file.helper.MessageHandler;

public class H0203DataIntegrityValidator {

	public final Template doFileDataIntegrityCheck(final Map<String, List<String>> templateParamMap, 
			Template dataBean, final TemplateProcessorContext context, final File file) {
		List<String> messages = new ArrayList<>();
		int lineNumber = Numeral.NOT_FOUND;
		
		if (null == templateParamMap) {
			String Message = "Parameter templateParamMap cannot be null!";
			messages.add(Message);
		} else {
			List<String> expectedRecordsList = templateParamMap.get(Strings.NUMBER_OF_DATA_RECORDS_TITLE);
			List<String> actualRecordsList = templateParamMap.get(Strings.NUMBER_OF_DATA_RECORDS_ADDED);
			lineNumber = new NullSafeCollectionsMapUtils().parseInt(templateParamMap, Strings.CURRENT_LINE);
			
			new DataRecordNumberValidator(expectedRecordsList, 
					actualRecordsList).validate(messages);
		}
		
		boolean hasErrorMessage = new ErrorMessageChecker(messages).isContainsErrorMessages();
		if (hasErrorMessage) {
			dataBean = new MessageHandler(messages, context, dataBean, 
					file, lineNumber).doMessagesHandling();
		}
		
		return dataBean;
	}
}

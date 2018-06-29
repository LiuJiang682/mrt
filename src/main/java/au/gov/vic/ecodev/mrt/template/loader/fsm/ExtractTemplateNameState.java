package au.gov.vic.ecodev.mrt.template.loader.fsm;

import java.util.List;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.loader.fsm.helpers.TemplateNameExtractor;
import au.gov.vic.ecodev.mrt.template.loader.fsm.helpers.TemplateOwnerEmailHelper;
import au.gov.vic.ecodev.mrt.template.loader.fsm.helpers.ZipFileFilter;

public class ExtractTemplateNameState implements LoaderState {

	private static final long SAVING_FAILED = Numeral.NEGATIVE_ONE;
	
	@Override
	public void on(TemplateLoaderStateMachineContext templateLoaderStateMachineContext) {
		List<String> fileNames = templateLoaderStateMachineContext.getMessage().getFileNames();
		if (CollectionUtils.isEmpty(fileNames)) {
			templateLoaderStateMachineContext.setNextStepToEnd();
		} else {
			List<String> zipFileNames = new ZipFileFilter().filterZipFile(fileNames);
			List<String> templateNames = new TemplateNameExtractor().extractTemplateName(zipFileNames);
			if (CollectionUtils.isEmpty(templateNames)) {
				templateLoaderStateMachineContext.getMessage()
					.setDirectErrorMessage("No template name found in file: " 
						+ String.join(Strings.COMMA, fileNames));
				templateLoaderStateMachineContext.setNextStepToNotifyUser();
			} else {
				templateLoaderStateMachineContext.getMessage().setFileNames(templateNames);
				long batchId = templateLoaderStateMachineContext
						.getPersistentServcies().getNextBatchId(templateNames, fileNames);
				if (SAVING_FAILED == batchId) {
					String message = "Unable to create session!";
					templateLoaderStateMachineContext.getMessage().setDirectErrorMessage(message);
					templateLoaderStateMachineContext.setNextStepToNotifyUser();
				} else {
					String templateOwnerEmails = new TemplateOwnerEmailHelper(templateLoaderStateMachineContext
							.getPersistentServcies())
							.extractTemplateOwnerEmails(templateNames);  
					templateLoaderStateMachineContext.getMessage()
						.setTemplateOwnerEmail(templateOwnerEmails);
					templateLoaderStateMachineContext.getMessage().setBatchId(batchId);
				}
			}
		}
	}
}

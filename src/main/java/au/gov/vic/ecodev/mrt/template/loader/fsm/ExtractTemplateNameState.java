package au.gov.vic.ecodev.mrt.template.loader.fsm;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;

import au.gov.vic.ecodev.common.util.StringListToFileListConvertor;
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
				String message = "No template name found in file: " + String.join(Strings.COMMA, fileNames);
				handleError(templateLoaderStateMachineContext, message);
			} else {
				templateLoaderStateMachineContext.getMessage().setFileNames(templateNames);
				long batchId = templateLoaderStateMachineContext
						.getPersistentServcies().getNextBatchId(templateNames, fileNames);
				if (SAVING_FAILED == batchId) {
					String message = "Unable to create session!";
					handleError(templateLoaderStateMachineContext, message);
				} else {
					List<Map<String, Object>> templateOwnerEmails = new TemplateOwnerEmailHelper(templateLoaderStateMachineContext
							.getPersistentServcies())
							.extractTemplateOwnerEmails(templateNames);  
					templateLoaderStateMachineContext.getMessage()
						.setTemplateOwnerEmail(templateOwnerEmails);
					templateLoaderStateMachineContext.getMessage().setFailedFileDirectory(
							templateLoaderStateMachineContext.getMrtConfigProperties().getFailedFileDirectory());
					templateLoaderStateMachineContext.getMessage().setPassedFileDirectory(
							templateLoaderStateMachineContext.getMrtConfigProperties().getPassedFileDirectory());
					templateLoaderStateMachineContext.getMessage().setBatchId(batchId);
					templateLoaderStateMachineContext.getMessage().setEmailSubject(String.join(Strings.COMMA, zipFileNames));
				}
			}
		}
	}
	
	protected final void handleError(final TemplateLoaderStateMachineContext templateLoaderStateMachineContext, 
			final String errorMessage) {
		templateLoaderStateMachineContext.getMessage()
			.setDirectErrorMessage(errorMessage);
		List<File> files = new StringListToFileListConvertor()
				.convertToFile(templateLoaderStateMachineContext.getMessage().getAbsoluteFileNames());
		templateLoaderStateMachineContext.getMessage().setFailedFiles(files);
		templateLoaderStateMachineContext.setNextStepToMoveFileToNextStage();
	}
	
}

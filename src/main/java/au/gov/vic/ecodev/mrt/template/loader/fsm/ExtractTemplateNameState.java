package au.gov.vic.ecodev.mrt.template.loader.fsm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.loader.fsm.helpers.TemplateOwnerEmailHelper;

public class ExtractTemplateNameState implements LoaderState {

	private static final String FILE_NAME_PATTERN = "^\\p{Alnum}+[_\\p{Alnum}]+\\.zip$";
	private static final long SAVING_FAILED = Numeral.NEGATIVE_ONE;
	
	@Override
	public void on(TemplateLoaderStateMachineContext templateLoaderStateMachineContext) {
		List<String> fileNames = templateLoaderStateMachineContext.getMessage().getFileNames();
		if (CollectionUtils.isEmpty(fileNames)) {
			templateLoaderStateMachineContext.setNextStepToEnd();
		} else {
			List<String> zipFileNames = filterZipFile(fileNames);
			List<String> templateNames = extractTemplateName(zipFileNames);
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

	protected final  List<String> filterZipFile(List<String> fileNames) {
		return fileNames.stream()
			.filter(fileName -> fileName.matches(FILE_NAME_PATTERN))
			.collect(Collectors.toList());
	}
	
	protected final List<String> extractTemplateName(List<String> fileNames) {
		List<String> templateNames = new ArrayList<>();
		fileNames.stream()
			.forEach(fileName -> {
				String[] names = fileName.split(Strings.UNDER_LINE);
				templateNames.add(names[Numeral.ZERO]);
			});
		return templateNames;
	}
}

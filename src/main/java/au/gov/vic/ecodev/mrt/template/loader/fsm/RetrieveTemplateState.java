package au.gov.vic.ecodev.mrt.template.loader.fsm;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import au.gov.vic.ecodev.mrt.constants.LogSeverity;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.Message;
import au.gov.vic.ecodev.mrt.template.processor.services.PersistentServices;

public class RetrieveTemplateState implements LoaderState {
	
	@Override
	public void on(TemplateLoaderStateMachineContext templateLoaderStateMachineContext) {
		PersistentServices persistentServices = templateLoaderStateMachineContext.getPersistentServcies();
		List<String> templateNames = templateLoaderStateMachineContext.getMessage().getFileNames();
		List<String> templateClasses = new ArrayList<>();
		int index = Numeral.ZERO;
		for (String templateName : templateNames) {
			String templateClass = persistentServices.getTemplateClasses(templateName);
			if (StringUtils.isBlank(templateClass)) {
				handleTemplateNotFound(templateLoaderStateMachineContext, index);
			} else {
				templateClasses.add(templateClass);
			}
			++index;
		}
	
		templateLoaderStateMachineContext.getMessage().setTemplateClasses(templateClasses);
	}

	protected final void handleTemplateNotFound(TemplateLoaderStateMachineContext templateLoaderStateMachineContext, 
			int index) {
		Message message = templateLoaderStateMachineContext.getMessage();
		long batchId = message.getBatchId();
		String errorMessage = "Not template class found for file: " + message.getFileList().get(index).getAbsolutePath();
		templateLoaderStateMachineContext.getPersistentServcies().saveStatusLog(batchId, 
				LogSeverity.ERROR, errorMessage);
		message.getFileList().remove(index);
	}
}

package au.gov.vic.ecodev.mrt.template.loader.fsm.helpers;

import java.io.File;
import java.util.List;

import au.gov.vic.ecodev.mrt.constants.LogSeverity;
import au.gov.vic.ecodev.mrt.template.loader.fsm.TemplateLoaderStateMachineContext;

public class ProcessorContextExceptionHelper {

	public final void handleProcessorException(
			final TemplateLoaderStateMachineContext templateLoaderStateMachineContext,
			List<File> extractedFileDirs, int len, File currentFile, String abnormalExitReason) {	
		templateLoaderStateMachineContext.saveStatusLog(LogSeverity.ERROR, abnormalExitReason);
		if ((null != currentFile) 
				&& (null != extractedFileDirs)) {
			int index = extractedFileDirs.indexOf(currentFile);
			for (; index < len; index++) {
				File file = extractedFileDirs.get(index);
				templateLoaderStateMachineContext.addFailedFiles(file.getAbsolutePath());
			}
		}
	}
}

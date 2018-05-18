package au.gov.vic.ecodev.mrt.template.loader.fsm;

import org.springframework.util.StringUtils;

import au.gov.vic.ecodev.mrt.template.files.LogFileGenerator;

public class GenerateStatusLogState implements LoaderState {

	@Override
	public void on(TemplateLoaderStateMachineContext templateLoaderStateMachineContext) {
		long batchId = templateLoaderStateMachineContext.getMessage().getBatchId();
		LogFileGenerator logFileGenerator = new LogFileGenerator(batchId, 
				templateLoaderStateMachineContext.getPersistentServcies(),
				templateLoaderStateMachineContext.getMrtConfigProperties().getLogFileOutputDirectory());
		String abnormalExitReason = logFileGenerator.generateLogs();
		if (StringUtils.isEmpty(abnormalExitReason)) {
			templateLoaderStateMachineContext.getMessage()
				.setLogFileName(logFileGenerator.getLogFileName());
		} else {
			templateLoaderStateMachineContext.getMessage().setDirectErrorMessage(abnormalExitReason);
			templateLoaderStateMachineContext.setNextStepToNotifyUser();
		}
	}
}

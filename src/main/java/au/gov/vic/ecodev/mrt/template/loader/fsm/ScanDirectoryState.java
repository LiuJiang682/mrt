package au.gov.vic.ecodev.mrt.template.loader.fsm;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.template.files.DirectoryFilesScanner;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.DefaultMessage;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.Message;

public class ScanDirectoryState implements LoaderState {

	private static final Logger LOGGER = Logger.getLogger(ScanDirectoryState.class);
	
	@Override
	public void on(TemplateLoaderStateMachineContext templateLoaderStateMachineContext) {
		String directoryToScan = templateLoaderStateMachineContext.getMrtConfigProperties().getDirectoryToScan();
		LOGGER.debug("Start to scan directory " + directoryToScan);
		try {
			List<File> files = new DirectoryFilesScanner(directoryToScan).scan();
			List<String> fileNames = files.stream()
				.map(File::getName)
				.collect(Collectors.toList());
			if (CollectionUtils.isEmpty(fileNames)) {
				templateLoaderStateMachineContext.setNextStepToEnd();
			} else {
				Message message = new DefaultMessage();
				message.setFileList(files);
				message.setFileNames(fileNames);
				templateLoaderStateMachineContext.setMessage(message);
			}
		} catch (Exception e) {
			String abnormalExistReason = e.getMessage();
			Message message = new DefaultMessage();
			message.setDirectErrorMessage(abnormalExistReason);
			templateLoaderStateMachineContext.setMessage(message);
			templateLoaderStateMachineContext.setNextStepToGenerateStatusLogState();
			LOGGER.error(abnormalExistReason, e);
		}
	}
}

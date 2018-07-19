package au.gov.vic.ecodev.mrt.template.loader.fsm;

import java.io.File;
import java.util.List;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.config.MrtConfigProperties;
import au.gov.vic.ecodev.mrt.template.files.FileMover;

public class MoveFileToNextStageState implements LoaderState {

	@Override
	public void on(TemplateLoaderStateMachineContext templateLoaderStateMachineContext) {
		List<File> passedFiles = templateLoaderStateMachineContext.getMessage().getPassedFiles();
		MrtConfigProperties mrtConfigProperties = templateLoaderStateMachineContext.getMrtConfigProperties();
		String destination = mrtConfigProperties.getPassedFileDirectory();
		moveFile(passedFiles, destination, templateLoaderStateMachineContext);
		
		List<File> failedFiles = templateLoaderStateMachineContext.getMessage().getFailedFiles();
		String failedFileDestination = mrtConfigProperties.getFailedFileDirectory();
		moveFile(failedFiles, failedFileDestination, templateLoaderStateMachineContext);
	}

	protected final void moveFile(final List<File> files, final String destination, 
			final TemplateLoaderStateMachineContext templateLoaderStateMachineContext) {
		if (!CollectionUtils.isEmpty(files)) {
			FileMover fileMover = new FileMover(files);
			String exactDestination = destination + File.separator + templateLoaderStateMachineContext.getBatchId();
			List<File> newFiles = fileMover.moveFile(exactDestination);
			if (newFiles.size() != files.size()) {
				StringBuilder messageBuilder = new StringBuilder("Failed move file to: ")
						.append(destination)
						.append(", expected move ")
						.append(files.size())
						.append(" but only moved: ");
				for (File file : newFiles) {
					messageBuilder.append(file.getAbsolutePath());
					messageBuilder.append(", ");
				}
				String message = messageBuilder.toString();
				message = message.substring(au.gov.vic.ecodev.mrt.constants.Constants.Numeral.ZERO, 
						message.length() - Numeral.TWO);
				templateLoaderStateMachineContext.getMessage().setDirectErrorMessage(message);
			}
		}
	}
}

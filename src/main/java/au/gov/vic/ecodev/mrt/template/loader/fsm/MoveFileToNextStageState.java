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
		boolean passedFileOverwritten = mrtConfigProperties.getPassedFileOverwritten();
		moveFile(passedFiles, destination, passedFileOverwritten, templateLoaderStateMachineContext);
		
		List<File> failedFiles = templateLoaderStateMachineContext.getMessage().getFailedFiles();
		String failedFileDestination = mrtConfigProperties.getFailedFileDirectory();
		boolean failedFileOverwritten = mrtConfigProperties.getFailedFileOverwritten();
		moveFile(failedFiles, failedFileDestination, failedFileOverwritten, templateLoaderStateMachineContext);
	}

	protected final void moveFile(final List<File> files, 
			final String destination, boolean overwritten, 
			final TemplateLoaderStateMachineContext templateLoaderStateMachineContext) {
		if (!CollectionUtils.isEmpty(files)) {
			FileMover fileMover = new FileMover(files);
			List<File> newFiles = fileMover.moveFile(destination, overwritten);
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

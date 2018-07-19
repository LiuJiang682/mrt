package au.gov.vic.ecodev.mrt.template.loader.fsm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.config.MrtConfigProperties;
import au.gov.vic.ecodev.mrt.template.files.FileMover;
import au.gov.vic.ecodev.mrt.template.loader.fsm.helpers.ProcessorContextExceptionHelper;
import au.gov.vic.ecodev.utils.file.zip.extract.ZipFileExtractor;

public class UnzipZipFileState implements LoaderState {
	
	private static final Logger LOGGER = Logger.getLogger(UnzipZipFileState.class);

	@Override
	public void on(TemplateLoaderStateMachineContext templateLoaderStateMachineContext) {
		List<File> zipFiles = templateLoaderStateMachineContext.getMessage().getFileList();
		if (CollectionUtils.isEmpty(zipFiles)) {
			handleEmpatyZipFile(templateLoaderStateMachineContext);
		} else {
			FileMover fileMover = new FileMover(zipFiles);
			MrtConfigProperties mrtConfigProperties = templateLoaderStateMachineContext.getMrtConfigProperties();
			String destination = mrtConfigProperties.getZipFileExtractDestination();
			LOGGER.info("File move to: " + destination);
			List<File> newFiles = fileMover.moveFile(destination);
			if (CollectionUtils.isEmpty(newFiles)) {
				handleEmptyFileList(templateLoaderStateMachineContext, zipFiles, destination);
			} else {
				List<File> extractedDirs = new ArrayList<>();
				File currentFile = null;
				try {
					for (File file : newFiles) {
						currentFile = file;
						ZipFileExtractor zipFileExtractor = new ZipFileExtractor(currentFile);
						File dir = zipFileExtractor.extract();
						extractedDirs.add(dir);
					}
					templateLoaderStateMachineContext.getMessage().setFileList(extractedDirs);	
				} catch (IOException e) {
					handleIOException(templateLoaderStateMachineContext, newFiles, currentFile, e);
				}
			}
		}
	}

	protected void handleEmpatyZipFile(TemplateLoaderStateMachineContext templateLoaderStateMachineContext) {
		String abnormalExitReason = "Empty zip file list!";
		new ProcessorContextExceptionHelper().handleProcessorException(templateLoaderStateMachineContext,
				null, 0, null, abnormalExitReason);
		templateLoaderStateMachineContext.setNextStepToGenerateStatusLogState();
	}

	private void handleEmptyFileList(TemplateLoaderStateMachineContext templateLoaderStateMachineContext,
			List<File> zipFiles, String destination) {
		String abnormalExitReason = "Failed to move files to destination: " + destination;
		new ProcessorContextExceptionHelper().handleProcessorException(templateLoaderStateMachineContext,
				zipFiles, zipFiles.size(), null, abnormalExitReason);
		templateLoaderStateMachineContext.setNextStepToGenerateStatusLogState();
	}

	private void handleIOException(TemplateLoaderStateMachineContext templateLoaderStateMachineContext,
			List<File> newFiles, File currentFile, IOException e) {
		String abnormalExitReason = e.getMessage();
		new ProcessorContextExceptionHelper().handleProcessorException(templateLoaderStateMachineContext,
				newFiles, newFiles.size(), currentFile, abnormalExitReason);
		templateLoaderStateMachineContext.setNextStepToGenerateStatusLogState();
		LOGGER.error(abnormalExitReason, e);
	}
}

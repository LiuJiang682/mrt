package au.gov.vic.ecodev.mrt.template.loader.fsm;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.files.TemplateFileSelector;
import au.gov.vic.ecodev.mrt.template.loader.fsm.helpers.ProcessorContextExceptionHelper;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.Message;
import au.gov.vic.ecodev.mrt.template.processor.TemplateProcessor;
import au.gov.vic.ecodev.mrt.template.processor.TemplateProcessorFactory;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;

public class ProcessTemplateFileState implements LoaderState {

	private static final Logger LOGGER = Logger.getLogger(ProcessTemplateFileState.class);
	
	@Override
	public void on(final TemplateLoaderStateMachineContext templateLoaderStateMachineContext) {
		Message message = templateLoaderStateMachineContext.getMessage();
		List<String> classes = message.getTemplateClasses();
		List<File> extractedFileDirs = message.getFileList();
		int len = classes.size();
		File currentFile = null;
		try {
			for (int i = Numeral.ZERO; i < len; i++) {
				currentFile = extractedFileDirs.get(i);
				processingTemplate(classes.get(i), currentFile,
						templateLoaderStateMachineContext);
			}
		} catch (Exception e) {
			String abnormalExitReason = e.getMessage(); 
			new ProcessorContextExceptionHelper()
				.handleProcessorException(templateLoaderStateMachineContext, 
						extractedFileDirs, len, currentFile, abnormalExitReason);
			LOGGER.error(abnormalExitReason, e);
		}
	}

	protected final void processingTemplate(final String className, final File file,
			final TemplateLoaderStateMachineContext templateLoaderStateMachineContext) throws Exception {
		String[] classes = className.split(Strings.COMMA);
		List<String> templateList = new ArrayList<>(Arrays.asList(classes));
		
		for (String template : templateList) {
			String[] templateClass = template.split(Strings.COLON);
			if (Numeral.TWO == templateClass.length) {
				TemplateProcessor templateProcessor = TemplateProcessorFactory
						.getProcessor(templateClass[Numeral.ONE]);
				if (null == templateProcessor) {
					handleNoTemplateProcessor(file, templateLoaderStateMachineContext, templateClass);
					break;
				} else {
					TemplateFileSelector templateFileSelector = 
							new TemplateFileSelector(file.getAbsolutePath());
					List<String> templateNames = new ArrayList<>();
					templateNames.add(templateClass[Numeral.ZERO]);
					Optional<String> templateAndFileName = templateFileSelector
							.getTemplateFileInDirectory(templateNames);
					if (templateAndFileName.isPresent()) {
						doTemplateFileProcessing(templateLoaderStateMachineContext, 
								templateProcessor,
								templateAndFileName.get());
					} else {
						handleNoTemplateFound(file, templateLoaderStateMachineContext, 
								templateClass[Numeral.ZERO]);
					}
				}
			} else {
				handleTemplateMetaDataError(file, templateLoaderStateMachineContext, template);
				break;
			}
		}
	}
	
	protected final void handleTemplateMetaDataError(final File file,
			final TemplateLoaderStateMachineContext templateLoaderStateMachineContext,
			String template) {
		String logMessage = new StringBuilder("No template processor class name provided from ")
				.append(template)
				.append(" when processing ")
				.append(file.getAbsolutePath())
				.toString();
		handleProcessorException(templateLoaderStateMachineContext, 
				file, logMessage);
	}

	protected final void handleNoTemplateProcessor(final File file,
			final TemplateLoaderStateMachineContext templateLoaderStateMachineContext, 
			String[] templateClass) {
		String logMessage = new StringBuilder("No template processor object created for ")
				.append(templateClass[Numeral.ONE])
				.append(" when processing ")
				.append(file.getAbsolutePath())
				.toString();
		handleProcessorException(templateLoaderStateMachineContext, 
				file, logMessage);
	}
	
	protected final void handleProcessorException(
			final TemplateLoaderStateMachineContext templateLoaderStateMachineContext,
			File file, String abnormalExitReason) {	
		Message message = templateLoaderStateMachineContext.getMessage();
		List<String> classes = message.getTemplateClasses();
		int len = (null == classes) ? Numeral.ZERO : classes.size();
		List<File> extractedFileDirs = message.getFileList();
		new ProcessorContextExceptionHelper()
			.handleProcessorException(templateLoaderStateMachineContext, 
				extractedFileDirs, len, file, abnormalExitReason);
	}
	
	protected final void doTemplateFileProcessing(
			final TemplateLoaderStateMachineContext templateLoaderStateMachineContext,
			final TemplateProcessor templateProcessor, final String templateAndFileName)
			throws TemplateProcessorException {
		String str = templateAndFileName;
		String[] strs = str.split(Strings.SPACE);
		List<File> files = new ArrayList<>();
		String fileName = strs[Numeral.ONE];
		File fileToProcess = new File(fileName);
		files.add(fileToProcess);
		templateProcessor.setFileList(files);
		templateProcessor.setTemplateProcessorContent(templateLoaderStateMachineContext);
		templateProcessor.processFile();
	}

	private void handleNoTemplateFound(final File file,
			final TemplateLoaderStateMachineContext templateLoaderStateMachineContext, 
			final String templateClass) {
		String errorMessage = new StringBuilder("File: ")
				.append(file.getAbsolutePath())
				.append(" is not a ")
				.append(templateClass)
				.append(" template file.")
				.toString();
		handleProcessorException(templateLoaderStateMachineContext, 
				file, errorMessage);
	}
}

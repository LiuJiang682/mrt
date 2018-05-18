package au.gov.vic.ecodev.mrt.template.processor.sl4;

import java.io.File;
import java.util.List;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.template.processor.TemplateProcessor;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.file.parser.sl4.Sl4TemplateFileParser;

public class Sl4TemplateProcessor implements TemplateProcessor {

	private List<File> fileToProcess;
	private TemplateProcessorContext context;

	@Override
	public void processFile() throws TemplateProcessorException {
		if (CollectionUtils.isEmpty(fileToProcess)) {
			throw new TemplateProcessorException("No file to process!");
		}

		if (null == context) {
			throw new TemplateProcessorException("No context present!");
		}

		try {
			for (File file : fileToProcess) {
				Sl4TemplateFileParser sl4TemplateFileParser = new Sl4TemplateFileParser(file, context);
				sl4TemplateFileParser.parse();
			}
		} catch (Exception e) {
			throw new TemplateProcessorException(e.getMessage(), e);
		}
	}

	@Override
	public void setFileList(List<File> files) {
		this.fileToProcess = files;
	}

	@Override
	public void setTemplateProcessorContent(TemplateProcessorContext context) {
		this.context = context;
	}

}

package au.gov.vic.ecodev.mrt.template.processor.dl4;

import java.io.File;
import java.util.List;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.template.processor.TemplateProcessor;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.file.parser.dl4.Dl4TemplateFileParser;

public class Dl4TemplateProcessor implements TemplateProcessor {

	private List<File> fileToProcess;
	private TemplateProcessorContext context;
	
	@Override
	public void setFileList(List<File> files) {
		this.fileToProcess = files;
	}

	@Override
	public void setTemplateProcessorContent(TemplateProcessorContext context) {
		this.context = context;
	}

	@Override
	public void processFile() throws TemplateProcessorException {
		if (CollectionUtils.isEmpty(fileToProcess)) {
			throw new TemplateProcessorException("Dl4TemplateProcessor:No file to process!");
		}
		if (null == context) {
			throw new TemplateProcessorException("Dl4TemplateProcessor:No context present!");
		}

		try {
			for (File file : fileToProcess) {
				Dl4TemplateFileParser dl4TemplateFileParser = new Dl4TemplateFileParser(file, context);
				dl4TemplateFileParser.parse();
			}
		} catch (Exception e) {
			throw new TemplateProcessorException(e.getMessage(), e);
		}
	}

}

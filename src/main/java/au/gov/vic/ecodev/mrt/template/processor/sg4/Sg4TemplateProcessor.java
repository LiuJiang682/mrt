package au.gov.vic.ecodev.mrt.template.processor.sg4;

import java.io.File;
import java.util.List;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.template.processor.TemplateProcessor;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.file.parser.sg4.Sg4TemplateFileParser;

public class Sg4TemplateProcessor implements TemplateProcessor {

	private List<File> fileToProcess;
	private TemplateProcessorContext context;
	
	@Override
	public void setFileList(List<File> files) {
		fileToProcess = files;
	}

	@Override
	public void setTemplateProcessorContent(TemplateProcessorContext context) {
		this.context = context;
	}

	@Override
	public void processFile() throws TemplateProcessorException {
		if (CollectionUtils.isEmpty(fileToProcess)) {
			throw new TemplateProcessorException("Sg4TemplateProcessor:No file to process!");
		}
		if (null == context) {
			throw new TemplateProcessorException("No context present!");
		}
		
		try {
			for (File file : fileToProcess) {
				Sg4TemplateFileParser sg4TemplateFileParser = new Sg4TemplateFileParser(file, context);
				sg4TemplateFileParser.parse();
			}
		} catch (Exception e) {
			throw new TemplateProcessorException(e.getMessage(), e);
		}
	}

}

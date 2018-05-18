package au.gov.vic.ecodev.mrt.template.processor.file.parser.sg4;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.files.DirectoryTreeReverseTraversalZipFileFinder;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.context.properties.utils.MultiStringValueToListConventor;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.file.parser.MessageHandler;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.sg4.Sg4ValidatorFactory;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.model.sg4.Sg4Template;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;

public class Sg4TemplateFileParser {

	private static final Logger LOGGER = Logger.getLogger(Sg4TemplateFileParser.class);
	
	private static final String TEMPLATE_PROP_SG4_MANDATORY_VALIDATE_FIELDS = "SG4:MANDATORY.VALIDATE.FIELDS";
	
	private final File file;
	private final TemplateProcessorContext context;
	
	public Sg4TemplateFileParser(File file, TemplateProcessorContext context) {
		if (null == file) {
			throw new IllegalArgumentException("Parameter file cannot be null!");
		}
		if (null == context) {
			throw new IllegalArgumentException("Parameter context cannot be null!");
		}
		this.file = file;
		this.context = context;
	}

	public void parse() throws Exception {
		Map<String, List<String>> templateParamMap = new HashMap<>();
		Template dataBean = new Sg4Template();
		LineNumberReader lineNumberReader = getLineNumberReader();
		final List<String> mandatoryFields = new MultiStringValueToListConventor(context)
				.getContextProperties(TEMPLATE_PROP_SG4_MANDATORY_VALIDATE_FIELDS, Strings.COMMA);
		Sg4ValidatorFactory sg4ValidatorFactory = new Sg4ValidatorFactory(context, mandatoryFields);
		String line;
		
		while(null != (line = lineNumberReader.readLine())) {
			LOGGER.info(line);
			int lineNumber = lineNumberReader.getLineNumber();
			templateParamMap.put(Strings.CURRENT_LINE, Arrays.asList(String.valueOf(lineNumber)));
			
			Validator validator = sg4ValidatorFactory.getLineValidator(line);
			Optional<List<String>> errorMessage = validator.validate(templateParamMap, 
					dataBean);
			if (errorMessage.isPresent()) {
				dataBean = new MessageHandler(errorMessage.get(), context, dataBean, 
						file, lineNumber).doMessagesHandling();
			}
		}
		
		if (null != dataBean) {
			if (context.saveDataBean(dataBean)) {
				String zipFile = new DirectoryTreeReverseTraversalZipFileFinder(file.getParent()).findZipFile();
				context.addPassedFiles(zipFile);
			} else {
				throw new TemplateProcessorException("Unable to save the data bean!");
			}
		}
	}

	private LineNumberReader getLineNumberReader() throws Exception {
		return new LineNumberReader(new FileReader(file.getAbsolutePath()));
	}
}

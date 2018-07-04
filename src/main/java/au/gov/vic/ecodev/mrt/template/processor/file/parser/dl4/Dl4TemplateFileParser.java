package au.gov.vic.ecodev.mrt.template.processor.file.parser.dl4;

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
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.context.properties.dl4.Dl4ToSl4DepthFromCrossChecker;
import au.gov.vic.ecodev.mrt.template.processor.context.properties.dl4.Dl4ToSl4HoleIdCrossChecker;
import au.gov.vic.ecodev.mrt.template.processor.context.properties.utils.MultiStringValueToListConventor;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.dl4.Dl4ValidatorFactory;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.model.dl4.Dl4Template;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;
import au.gov.vic.ecodev.utils.file.finder.DirectoryTreeReverseTraversalZipFileFinder;
import au.gov.vic.ecodev.utils.file.helper.MessageHandler;

public class Dl4TemplateFileParser {
	
	private static final Logger LOGGER = Logger.getLogger(Dl4TemplateFileParser.class);

	private static final String TEMPLATE_PROP_DL4_MANDATORY_VALIDATE_FIELDS = "DL4:MANDATORY.VALIDATE.FIELDS";
	private final File file;
	private final TemplateProcessorContext context;
	
	public Dl4TemplateFileParser(File file, TemplateProcessorContext context) {
		if (null == file) {
			throw new IllegalArgumentException("Dl4TemplateFileParser: Parameter file cannot be null!");
		}
		if (null == context) {
			throw new IllegalArgumentException("Dl4TemplateFileParser: Parameter context cannot be null!");
		}
		this.file = file;
		this.context = context;
	}

	public void parse() throws Exception {
		Map<String, List<String>> templateParamMap = new HashMap<>();
		Template template = new Dl4Template();
		LineNumberReader lineNumberReader = getLineNumberReader();
		final List<String> mandatoryFields = new MultiStringValueToListConventor(context)
				.getContextProperties(TEMPLATE_PROP_DL4_MANDATORY_VALIDATE_FIELDS, Strings.COMMA);
		Dl4ValidatorFactory dl4ValidatorFactory = new Dl4ValidatorFactory(context, 
				mandatoryFields);
		String line;
		
		while(null != (line = lineNumberReader.readLine())) {
			LOGGER.info(line);
			int lineNumber = lineNumberReader.getLineNumber();
			templateParamMap.put(Strings.CURRENT_LINE, Arrays.asList(String.valueOf(lineNumber)));
			Validator validator = dl4ValidatorFactory.getLineValidator(line);
			Optional<List<String>> errorMessage = validator.validate(templateParamMap, 
					template);
			if (errorMessage.isPresent()) {
				template = new MessageHandler(errorMessage.get(), context, template, 
						file, lineNumber).doMessagesHandling();
			}
		}
		
		doTemplateSavingAndIntegrityCheck(template);
	}

	protected void doTemplateSavingAndIntegrityCheck(Template template) throws TemplateProcessorException {
		if (null != template) {
			if (context.saveDataBean(template)) {
				String zipFile = new DirectoryTreeReverseTraversalZipFileFinder(file.getParent()).findZipFile();
				if (doDl4ToSl4DataIntegrityCheck()) {
					context.addPassedFiles(zipFile);
				} else {
					context.addFailedFiles(zipFile);
				}
			} else {
				throw new TemplateProcessorException("Unable to save the data bean!");
			}
		}
	}

	protected final boolean doDl4ToSl4DataIntegrityCheck() throws TemplateProcessorException {
		final long sessionId = context.getBatchId();
		boolean passedHoleIdCheck = new Dl4ToSl4HoleIdCrossChecker(context)
				.doHoleIdCrossCheck(sessionId);
		boolean passedDepthFromCheck = new Dl4ToSl4DepthFromCrossChecker(context)
				.doDepthFromCrossCheck(sessionId);
		return passedHoleIdCheck && passedDepthFromCheck;
	}

	private LineNumberReader getLineNumberReader() throws Exception {
		return new LineNumberReader(new FileReader(file.getAbsolutePath()));
	}
}

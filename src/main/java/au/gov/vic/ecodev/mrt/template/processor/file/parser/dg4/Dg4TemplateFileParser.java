package au.gov.vic.ecodev.mrt.template.processor.file.parser.dg4;

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
import au.gov.vic.ecodev.mrt.template.processor.context.properties.dg4.Dg4ToSl4FromCrossChecker;
import au.gov.vic.ecodev.mrt.template.processor.context.properties.dg4.Dg4ToSl4HoleIdCrossChecker;
import au.gov.vic.ecodev.mrt.template.processor.context.properties.dg4.Dg4ToSl4ToCrossChecker;
import au.gov.vic.ecodev.mrt.template.processor.context.properties.utils.MultiStringValueToListConventor;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0203DataIntegrityValidator;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.dg4.Dg4ValidatorFactory;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.model.dg4.Dg4Template;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;
import au.gov.vic.ecodev.utils.file.finder.DirectoryTreeReverseTraversalZipFileFinder;
import au.gov.vic.ecodev.utils.file.helper.MessageHandler;

public class Dg4TemplateFileParser {

	private static final Logger LOGGER = Logger.getLogger(Dg4TemplateFileParser.class);
	
	private static final String TEMPLATE_PROP_DG4_MANDATORY_VALIDATE_FIELDS = "DG4:MANDATORY.VALIDATE.FIELDS";

	private final File file;
	private final TemplateProcessorContext context;
	
	public Dg4TemplateFileParser(File file, TemplateProcessorContext context) {
		if (null == file) {
			throw new IllegalArgumentException("Dg4TemplateFileParser:file parameter cannot be null!");
		}
		if (null == context) {
			throw new IllegalArgumentException("Dg4TemplateFileParser:context parameter cannot be null!");
		}
		this.file = file;
		this.context = context;
	}

	public void parse() throws Exception {
		Map<String, List<String>> templateParamMap = new HashMap<>();
		Template template = new Dg4Template();
		LineNumberReader lineNumberReader = getLineNumberReader();
		final List<String> mandatoryFields = new MultiStringValueToListConventor(context)
				.getContextProperties(TEMPLATE_PROP_DG4_MANDATORY_VALIDATE_FIELDS, Strings.COMMA);
		Dg4ValidatorFactory dg4ValidatorFactory = new Dg4ValidatorFactory(context, 
				mandatoryFields);
		doContextPropertiesLoad(templateParamMap);
		String line;
		
		while(null != (line = lineNumberReader.readLine())) {
			LOGGER.info(line);
			int lineNumber = lineNumberReader.getLineNumber();
			templateParamMap.put(Strings.CURRENT_LINE, Arrays.asList(String.valueOf(lineNumber)));
			Validator validator = dg4ValidatorFactory.getLineValidator(line);
			Optional<List<String>> errorMessage = validator.validate(templateParamMap, 
					template);
			if (errorMessage.isPresent()) {
				template = new MessageHandler(errorMessage.get(), context, template, 
						file, lineNumber).doMessagesHandling();
			}
		}
		
		template = new H0203DataIntegrityValidator().doFileDataIntegrityCheck(templateParamMap, template, 
				context, file);
		doTemplateSavingAndIntegrityCheck(template);
	}
	
	protected final void doContextPropertiesLoad(Map<String, List<String>> templateParamMap) throws TemplateProcessorException {
		final List<String> H1001MandatoryFields = new MultiStringValueToListConventor(context)
				.getContextProperties(Strings.TEMPLATE_PROP_DG4_H1001_MANDATORY_FIELDS_HEADER, Strings.COMMA);
		templateParamMap.put(Strings.TEMPLATE_PROP_DG4_H1001_MANDATORY_FIELDS_HEADER, H1001MandatoryFields);
		final List<String> H1002MandatoryFields = new MultiStringValueToListConventor(context)
				.getContextProperties(Strings.TEMPLATE_PROP_DG4_H1002_MANDATORY_FIELDS_HEADER, Strings.COMMA);
		templateParamMap.put(Strings.TEMPLATE_PROP_DG4_H1002_MANDATORY_FIELDS_HEADER, H1002MandatoryFields);
		final List<String> H1003MandatoryFields = new MultiStringValueToListConventor(context)
				.getContextProperties(Strings.TEMPLATE_PROP_DG4_H1003_MANDATORY_FIELDS_HEADER, Strings.COMMA);
		templateParamMap.put(Strings.TEMPLATE_PROP_DG4_H1003_MANDATORY_FIELDS_HEADER, H1003MandatoryFields);
	}

	protected void doTemplateSavingAndIntegrityCheck(Template template) throws TemplateProcessorException {
		if (null != template) {
			if (context.saveDataBean(template)) {
				String zipFile = new DirectoryTreeReverseTraversalZipFileFinder(file.getParent()).findZipFile();
				if (doDg4ToSl4DataIntegrityCheck()) {
					context.addPassedFiles(zipFile);
				} else {
					context.addFailedFiles(zipFile);
				}
			} else {
				throw new TemplateProcessorException("Unable to save the data bean!");
			}
		}
	}

	private boolean doDg4ToSl4DataIntegrityCheck() throws TemplateProcessorException {
		final long sessionId = context.getBatchId();
		boolean passedHoleIdCheck = new Dg4ToSl4HoleIdCrossChecker(context)
				.doHoleIdCrossCheck(sessionId);
		boolean passedFromCheck = new Dg4ToSl4FromCrossChecker(context)
				.doDepthCrossCheck(sessionId);
		boolean passedToCheck = new Dg4ToSl4ToCrossChecker(context)
				.doDepthCrossCheck(sessionId);
		return passedHoleIdCheck && passedFromCheck && passedToCheck;
	}

	private LineNumberReader getLineNumberReader() throws Exception {
		return new LineNumberReader(new FileReader(file.getAbsolutePath()));
	}
}

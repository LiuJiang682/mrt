package au.gov.vic.ecodev.mrt.template.processor.file.parser.ds4;

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
import au.gov.vic.ecodev.mrt.template.processor.context.properties.ds4.Ds4ToSl4HoleIdCrossChecker;
import au.gov.vic.ecodev.mrt.template.processor.context.properties.ds4.Ds4ToSl4SurveyedDepthCrossChecker;
import au.gov.vic.ecodev.mrt.template.processor.context.properties.utils.MultiStringValueToListConventor;
import au.gov.vic.ecodev.mrt.template.processor.context.properties.utils.SingleStringValueToListConventor;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0203H0534H0535DataIntegrityValidator;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.ds4.Ds4ValidatorFactory;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.model.ds4.Ds4Template;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;
import au.gov.vic.ecodev.utils.file.finder.DirectoryTreeReverseTraversalZipFileFinder;
import au.gov.vic.ecodev.utils.file.helper.MessageHandler;

public class Ds4TemplateFileParser {

	private static final Logger LOGGER = Logger.getLogger(Ds4TemplateFileParser.class);
	
	private static final String TEMPLATE_PROP_DS4_MANDATORY_VALIDATE_FIELDS = "DS4:MANDATORY.VALIDATE.FIELDS";
	private static final String TEMPLATE_PROP_DS4_AZIMUTHMAG_PRECISION = "DS4:AZIMUTHMAG.PRECISION";
	private static final String TEMPLATE_PROP_DS4_DIP_PRECISION = "DS4:DIP.PRECISION";
	private final File file;
	private final TemplateProcessorContext context;
	
	public Ds4TemplateFileParser(File file, TemplateProcessorContext context) {
		if (null == file) {
			throw new IllegalArgumentException("Ds4TemplateFileParser: Parameter file cannot be null!");
		}
		if (null == context) {
			throw new IllegalArgumentException("Ds4TemplateFileParser: Parameter context cannot be null!");
		}
		this.file = file;
		this.context = context;
	}

	public void parse() throws Exception {
		Map<String, List<String>> templateParamMap = new HashMap<>();
		templateParamMap.put(Strings.AZIMUTH_MAG_PRECISION, 
				new SingleStringValueToListConventor(context)
					.getContextProperties(TEMPLATE_PROP_DS4_AZIMUTHMAG_PRECISION));
		templateParamMap.put(Strings.DIP_PRECISION, 
				new SingleStringValueToListConventor(context)
					.getContextProperties(TEMPLATE_PROP_DS4_DIP_PRECISION));
		Template template = new Ds4Template();
		LineNumberReader lineNumberReader = getLineNumberReader();
		final List<String> mandatoryFields = new MultiStringValueToListConventor(context)
				.getContextProperties(TEMPLATE_PROP_DS4_MANDATORY_VALIDATE_FIELDS, Strings.COMMA);
		Ds4ValidatorFactory ds4ValidatorFactory = new Ds4ValidatorFactory(context, mandatoryFields);
		String line;
		
		while(null != (line = lineNumberReader.readLine())) {
			LOGGER.info(line);
			int lineNumber = lineNumberReader.getLineNumber();
			templateParamMap.put(Strings.CURRENT_LINE, Arrays.asList(String.valueOf(lineNumber)));
			Validator validator = ds4ValidatorFactory.getLineValidator(line);
			Optional<List<String>> errorMessage = validator.validate(templateParamMap, 
					template);
			if (errorMessage.isPresent()) {
				template = new MessageHandler(errorMessage.get(), context, template, 
						file, lineNumber).doMessagesHandling();
			}
		}
		 
		template = new H0203H0534H0535DataIntegrityValidator().doFileDataIntegrityCheck(templateParamMap, template, 
				context, file);
		doTemplateSavingAndIntegrityCheck(template);
	}

	protected final void doTemplateSavingAndIntegrityCheck(Template template) throws TemplateProcessorException {
		if (null != template) {
			template.put(Strings.CURRENT_FILE_NAME, Arrays.asList(file.getName()));
			if (context.saveDataBean(template)) {
				String zipFile = new DirectoryTreeReverseTraversalZipFileFinder(file.getParent()).findZipFile();
				if (doDs4ToSl4DataIntegrityCheck()) {
					context.addPassedFiles(zipFile);
				} else {
					context.addFailedFiles(zipFile);
				}
			} else {
				throw new TemplateProcessorException("Unable to save the data bean!");
			}
		}
	}
	
	protected final boolean doDs4ToSl4DataIntegrityCheck() throws TemplateProcessorException {
		final long sessionId = context.getBatchId();
		boolean passHoleIdCrossCheck = new Ds4ToSl4HoleIdCrossChecker(context)
				.doHoleIdCrossCheck(sessionId);
		boolean passSurveyedDepthCrossCheck = new Ds4ToSl4SurveyedDepthCrossChecker(context)
				.doSurvyedDepthCrossCheck(sessionId);
		return passHoleIdCrossCheck && passSurveyedDepthCrossCheck;
	}

	private LineNumberReader getLineNumberReader() throws Exception {
		return new LineNumberReader(new FileReader(file.getAbsolutePath()));
	}
}

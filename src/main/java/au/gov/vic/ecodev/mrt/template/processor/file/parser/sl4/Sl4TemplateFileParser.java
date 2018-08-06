package au.gov.vic.ecodev.mrt.template.processor.file.parser.sl4;

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
import au.gov.vic.ecodev.mrt.template.processor.context.properties.utils.SingleStringValueToListConventor;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0203H0532H0533DataIntegrityValidator;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.helper.FileNameHelper;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4.DValidator;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4.Sl4ValidatorFactory;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.model.sl4.Sl4Template;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;
import au.gov.vic.ecodev.mrt.template.properties.TemplateProperties;
import au.gov.vic.ecodev.template.processor.context.properties.StringToListTemplatePropertiesParser;
import au.gov.vic.ecodev.utils.file.finder.DirectoryTreeReverseTraversalZipFileFinder;
import au.gov.vic.ecodev.utils.file.helper.MessageHandler;

public class Sl4TemplateFileParser {

	private static final Logger LOGGER = Logger.getLogger(Sl4TemplateFileParser.class);
	
	private static final String TEMPLATE_PROP_SL4_MANDATORY_VALIDATE = "SL4:MANDATORY.VALIDATE.FIELDS";
	
	private static final String TEMPLATE_PROP_SL4_AZIMUTHMAG_PRECISION = "SL4:AZIMUTHMAG.PRECISION";

	private static final String TEMPLATE_PROP_SL4_DIP_PRECISION = "SL4:DIP.PRECISION";
	
	private final File file;
	private final TemplateProcessorContext context;
	
	public Sl4TemplateFileParser(final File file, final TemplateProcessorContext context) {
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
		templateParamMap.put(Strings.AZIMUTH_MAG_PRECISION, 
				new SingleStringValueToListConventor(context)
					.getContextProperties(TEMPLATE_PROP_SL4_AZIMUTHMAG_PRECISION));
		templateParamMap.put(Strings.DIP_PRECISION, 
				new SingleStringValueToListConventor(context)
					.getContextProperties(TEMPLATE_PROP_SL4_DIP_PRECISION));
		Template dataBean = new Sl4Template();
		LineNumberReader lineNumberReader = getLineNumberReader();
		final List<String> mandatoryFields = getMandatoryValidateFields();
		Sl4ValidatorFactory sl4ValidatorFactory = new Sl4ValidatorFactory(context, mandatoryFields);
		String zipFile = new DirectoryTreeReverseTraversalZipFileFinder(file.getParent()).findZipFile();
		boolean isPartialFileName = new FileNameHelper(zipFile).isPartailFileName();
		templateParamMap.put(Strings.PARTIAL_FILE_NAME_KEY, Arrays.asList(String.valueOf(isPartialFileName)));
		String line;
		
		while(null != (line = lineNumberReader.readLine())) {
			LOGGER.info(line);
			int lineNumber = lineNumberReader.getLineNumber();
			templateParamMap.put(Strings.CURRENT_LINE, Arrays.asList(String.valueOf(lineNumber)));
			
			Validator validator = sl4ValidatorFactory.getLineValidator(line);
			if (validator instanceof DValidator) {
				((DValidator) validator).setTemplateProcessorContext(context);
				((DValidator) validator).setZipFileName(zipFile);
			}
			Optional<List<String>> errorMessage = validator.validate(templateParamMap, 
					dataBean);
			if (errorMessage.isPresent()) {
				dataBean = new MessageHandler(errorMessage.get(), context, dataBean, 
						file, lineNumber).doMessagesHandling();
			}
		}
		
		dataBean = new H0203H0532H0533DataIntegrityValidator()
				.doFileDataIntegrityCheck(templateParamMap, dataBean, context, file);
		
		if (null != dataBean) {
			dataBean.put(Strings.CURRENT_FILE_NAME, Arrays.asList(file.getName()));
			if (context.saveDataBean(dataBean)) {
				context.addPassedFiles(zipFile);
			} else {
				throw new TemplateProcessorException("Unable to save the data bean!");
			}
		}
	}

	private LineNumberReader getLineNumberReader() throws Exception {
		return new LineNumberReader(new FileReader(file.getAbsolutePath()));
	}
	
	protected final List<String> getMandatoryValidateFields() throws TemplateProcessorException {
		TemplateProperties sl4MandatoryFieldTemplateProperties = context.getTemplateContextProperty(TEMPLATE_PROP_SL4_MANDATORY_VALIDATE);
		List<String> mandatoryFields = new StringToListTemplatePropertiesParser(sl4MandatoryFieldTemplateProperties, 
				Strings.COMMA).parse();
		return mandatoryFields;
	}
}

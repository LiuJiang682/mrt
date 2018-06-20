package au.gov.vic.ecodev.mrt.template.loader.fsm;

import org.springframework.jdbc.core.JdbcTemplate;

import au.gov.vic.ecodev.mrt.api.constants.LogSeverity;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.config.MrtConfigProperties;
import au.gov.vic.ecodev.mrt.map.services.MapServices;
import au.gov.vic.ecodev.mrt.map.services.VictoriaMapServices;
import au.gov.vic.ecodev.mrt.template.context.properties.TemplateContextPropertyFinder;
import au.gov.vic.ecodev.mrt.template.context.properties.TemplateSearcherFactory;
import au.gov.vic.ecodev.mrt.template.criteria.TemplateCriteria;
import au.gov.vic.ecodev.mrt.template.loader.fsm.helpers.ContextMessageHelper;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.Message;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.services.PersistentServices;
import au.gov.vic.ecodev.mrt.template.properties.TemplateProperties;

public class TemplateLoaderStateMachineContext implements TemplateProcessorContext {
	
	private MrtConfigProperties mrtConfigProperties;
	private LoadingStep currentStep;
	private Message message;
	private PersistentServices persistentServices;
	private final JdbcTemplate jdbcTemplate;
	private final VictoriaMapServices victoriaMapServices;
	
	public TemplateLoaderStateMachineContext(final MrtConfigProperties mrtConfigProperties, 
			final PersistentServices persistentServcies, final JdbcTemplate jdbcTemplate,
			final VictoriaMapServices victoriaMapServices) {
		if (null == mrtConfigProperties) {
			throw new IllegalArgumentException("TemplateLoaderStateMachineContext:mrtConfigProperties cannot be null!");
		}
		this.mrtConfigProperties = mrtConfigProperties;
		if (null == persistentServcies) {
			throw new IllegalArgumentException("TemplateLoaderStateMachineContext:persistentServcies cannot be null!");
		}
		this.persistentServices = persistentServcies;
		if (null == jdbcTemplate) {
			throw new IllegalArgumentException("TemplateLoaderStateMachineContext:jdbcTemplate cannot be null!");
		}
		this.jdbcTemplate = jdbcTemplate;
		if (null == victoriaMapServices) {
			throw new IllegalArgumentException("TemplateLoaderStateMachineContext:victoriaMapServices cannot be null!");
		}
		this.victoriaMapServices = victoriaMapServices;
		
		resetCurrentState();
	}

	public LoaderState getNextState() throws Exception {
		if (null == currentStep) {
			return null;
		}
 		LoaderState LoaderState = currentStep.getState();
		currentStep = currentStep.getNextStep();
		return LoaderState;
	}

	public LoadingStep getCurrentStep() {
		return currentStep;
	}

	public Message getMessage() {
		return message;
	}
		
	public void setMessage(final Message message) {
		this.message = message;
	}

	public MrtConfigProperties getMrtConfigProperties() {
		return mrtConfigProperties;
	}

	public PersistentServices getPersistentServcies() {
		return persistentServices;
	}
	
	@Override
	public boolean saveStatusLog(LogSeverity severity, String logMessage) {
		return persistentServices.saveStatusLog(message.getBatchId(), severity, logMessage);
	}

	@Override
	public TemplateProperties getTemplateContextProperty(final String templatePropertyName) throws TemplateProcessorException {
		return new TemplateContextPropertyFinder(persistentServices, templatePropertyName).find();
	}

	@Override
	public boolean saveDataBean(Template template) {
		return persistentServices.saveDataBean(jdbcTemplate, message.getBatchId(), template);
	}

	@Override
	public boolean addFailedFiles(String fileName) {
		return new ContextMessageHelper(message).addFailedFiles(fileName);
	}

	@Override
	public boolean addPassedFiles(String fileName) {
		return new ContextMessageHelper(message).addSuccessFiles(fileName);
	}

	public void resetCurrentState() {
		currentStep = LoadingStep.SCAN_DIR;
	}
	
	public void setNextStepToNotifyUser() {
		currentStep = LoadingStep.MOVE_FILE_TO_NEXT_STAGE;
	}
	
	public void setNextStepToGenerateStatusLogState() {
		currentStep = LoadingStep.PROCESS_TEMPLATE_FILE;	
	}
	
	public void setNextStepToEnd() {
		currentStep = null;
	}

	@Override
	public TemplateProperties search(TemplateCriteria criteria) throws TemplateProcessorException {
		return new TemplateSearcherFactory(persistentServices, jdbcTemplate)
				.getSearcher(criteria).search();
	}

	@Override
	public long getBatchId() {
		long batchId = Numeral.NOT_FOUND;
		if (null != message) {
			batchId = message.getBatchId();
		}
		return batchId;
	}

	@Override
	public MapServices getMapServices() {
		return victoriaMapServices;
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}
}

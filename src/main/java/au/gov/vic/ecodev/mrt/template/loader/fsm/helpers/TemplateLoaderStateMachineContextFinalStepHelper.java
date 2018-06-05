package au.gov.vic.ecodev.mrt.template.loader.fsm.helpers;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.dao.SessionHeaderDaoImpl;
import au.gov.vic.ecodev.mrt.model.SessionHeader;
import au.gov.vic.ecodev.mrt.model.fields.SessionStatus;
import au.gov.vic.ecodev.mrt.template.loader.fsm.TemplateLoaderStateMachineContext;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.Message;

public class TemplateLoaderStateMachineContextFinalStepHelper {

	private final TemplateLoaderStateMachineContext templateLoaderStateMachineContext;
	
	public TemplateLoaderStateMachineContextFinalStepHelper(
			TemplateLoaderStateMachineContext templateLoaderStateMachineContext) {
		if (null == templateLoaderStateMachineContext) {
			throw new IllegalArgumentException("TemplateLoaderStateMachineContextFinalStepHelper: templateLoaderStateMachineContext parameter cannot be null!");
		}
		this.templateLoaderStateMachineContext = templateLoaderStateMachineContext;
	}

	protected final void doSessionStatusUpdate() {
		long batchId = templateLoaderStateMachineContext.getBatchId();
		SessionHeaderDaoImpl sessionHeaderDaoImpl = new SessionHeaderDaoImpl();
		sessionHeaderDaoImpl.setJdbcTemplate(templateLoaderStateMachineContext.getJdbcTemplate());
		SessionHeader session = (SessionHeader) sessionHeaderDaoImpl.get(batchId);
		Message message = templateLoaderStateMachineContext.getMessage();
		if (CollectionUtils.isEmpty(message.getFailedFiles())) {
			session.setStatus(SessionStatus.COMPLETED);
		} else {
			session.setStatus(SessionStatus.FAILED);
		}
		sessionHeaderDaoImpl.updateOrSave(session);
	}

	public void doFinalCleanUp() {
		doSessionStatusUpdate();
		templateLoaderStateMachineContext.setMessage(null);
	}

}

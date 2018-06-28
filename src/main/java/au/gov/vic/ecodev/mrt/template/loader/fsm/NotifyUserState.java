package au.gov.vic.ecodev.mrt.template.loader.fsm;

import org.apache.log4j.Logger;

import au.gov.vic.ecodev.mrt.config.MrtConfigProperties;
import au.gov.vic.ecodev.mrt.mail.EmailBodyBuilder;
import au.gov.vic.ecodev.mrt.mail.Mailer;
import au.gov.vic.ecodev.mrt.template.loader.fsm.helpers.TemplateLoaderStateMachineContextFinalStepHelper;

public class NotifyUserState implements LoaderState {

	private static final Logger LOGGER = Logger.getLogger(NotifyUserState.class);
	
	@Override
	public void on(TemplateLoaderStateMachineContext templateLoaderStateMachineContext) {
		MrtConfigProperties mrtConfigProperties = templateLoaderStateMachineContext.getMrtConfigProperties();
		Mailer mailer = new Mailer(mrtConfigProperties);
		mailer.createSession();
		String body = new EmailBodyBuilder(templateLoaderStateMachineContext).build();
		boolean emailSent = false;
		try {
			mailer.send(templateLoaderStateMachineContext.getMessage().getTemplateOwnerEmail(), 
					mrtConfigProperties.getEmailSubject(), body , mrtConfigProperties.getEmailUser(), 
					mrtConfigProperties.getEmailUserPwd());
			emailSent = true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		
		//Clean up
		new TemplateLoaderStateMachineContextFinalStepHelper(templateLoaderStateMachineContext, emailSent).doFinalCleanUp();
	}
}

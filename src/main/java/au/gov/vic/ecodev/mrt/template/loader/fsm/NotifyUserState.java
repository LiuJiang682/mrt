package au.gov.vic.ecodev.mrt.template.loader.fsm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.config.MrtConfigProperties;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.mail.EmailBodyBuilder;
import au.gov.vic.ecodev.mrt.mail.Mailer;
import au.gov.vic.ecodev.mrt.template.loader.fsm.helpers.TemplateLoaderStateMachineContextFinalStepHelper;

public class NotifyUserState implements LoaderState {

	private static final Logger LOGGER = Logger.getLogger(NotifyUserState.class);
	
	private static final String DEFAULT_EMAIL_BODY_BUILDER = "au.gov.vic.ecodev.mrt.mail.MrtEmailBodyBuilder";

	private static final String EMAILS_BUILDER = "EMAILS_BUILDER";
	private static final String OWNER_EMAILS = "OWNER_EMAILS";
	
	@Override
	public void on(TemplateLoaderStateMachineContext templateLoaderStateMachineContext) {
		
		List<Map<String, Object>> ownerEmails = getTemplateOwnerEmail(templateLoaderStateMachineContext);
		Map<String, Set<String>> emailBuildersMap = getEmailBuilderMap(ownerEmails);
		 
		boolean emailSent = doEmailDispatch(templateLoaderStateMachineContext, 
				emailBuildersMap);
		//Clean up
		new TemplateLoaderStateMachineContextFinalStepHelper(templateLoaderStateMachineContext, emailSent)
			.doFinalCleanUp();
	}

	protected final boolean doEmailDispatch(TemplateLoaderStateMachineContext templateLoaderStateMachineContext,
			Map<String, Set<String>> emailBuildersMap) {
		MrtConfigProperties mrtConfigProperties = templateLoaderStateMachineContext.getMrtConfigProperties();
		Mailer mailer = new Mailer(mrtConfigProperties);
		mailer.createSession();
		String subject = templateLoaderStateMachineContext.getMessage()
				.getEmailSubject();
		String webUrl = getWebUrl(mrtConfigProperties);
		templateLoaderStateMachineContext.getMessage().setWebUrl(webUrl);
		AtomicInteger count = new AtomicInteger(Numeral.ZERO);
		emailBuildersMap.forEach((emailBuilderClass, emailList) -> {
			try {
				Class<?> cls = Class.forName(emailBuilderClass);
				EmailBodyBuilder emailBodyBuilder = (EmailBodyBuilder) cls.newInstance();
				emailBodyBuilder.setTemplateProcessorContext(templateLoaderStateMachineContext);
				String body = emailBodyBuilder.build();
				String toEmail = String.join(Strings.COMMA, emailList);
			
				mailer.send(toEmail, subject, body, mrtConfigProperties.getEmailUser(),
						mrtConfigProperties.getEmailUserPwd());
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				count.incrementAndGet();
			}
		});
		
		boolean emailSent = (Numeral.ZERO == count.get()) ? true : false;
		return emailSent;
	}
	
	protected final String getWebUrl(MrtConfigProperties mrtConfigProperties) {
		String webUrl = mrtConfigProperties.getEmailWebUrl();
		Map<String, String> env = System.getenv();
		String envWebUrl = env.get(Strings.EMAIL_WEB_URL);
		if (StringUtils.isNotBlank(envWebUrl)) {
			webUrl = envWebUrl;
		}
		return webUrl;
	}

	protected final Map<String, Set<String>> getEmailBuilderMap(final List<Map<String, Object>> ownerEmails) {
		Map<String, Set<String>> emailBuildersMap = new HashMap<>();
		ownerEmails.stream()
			.forEach(ownerEmail -> {
				String toEmail = (String) ownerEmail.get(OWNER_EMAILS);
				String builderClass = (String) ownerEmail.get(EMAILS_BUILDER);
				Set<String> emailList = emailBuildersMap.get(builderClass);
				if (CollectionUtils.isEmpty(emailList)) {
					emailList = new HashSet<>();
					emailBuildersMap.put(builderClass, emailList);
				}
				emailList.add(toEmail);
			});
		return emailBuildersMap;
	}

	protected final List<Map<String, Object>> getTemplateOwnerEmail(final TemplateLoaderStateMachineContext 
			templateLoaderStateMachineContext) {
		List<Map<String, Object>> ownerEmails = templateLoaderStateMachineContext.getMessage().getTemplateOwnerEmail();
		if (CollectionUtils.isEmpty(ownerEmails)) {
			Map<String, Object> templateOwnerEmailProperties = new HashMap<>();
			String toEmail = templateLoaderStateMachineContext.getMrtConfigProperties().getToEmail();
			templateOwnerEmailProperties.put(OWNER_EMAILS, toEmail);
			String builderClass = DEFAULT_EMAIL_BODY_BUILDER;
			templateOwnerEmailProperties.put(EMAILS_BUILDER, builderClass);
			ownerEmails = new ArrayList<>();
			ownerEmails.add(templateOwnerEmailProperties);
		}
		return ownerEmails;
	}
}

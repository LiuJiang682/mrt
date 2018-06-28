package au.gov.vic.ecodev.mrt.template.loader.fsm.helpers;

import java.util.ArrayList;
import java.util.List;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.services.PersistentServices;

public class TemplateOwnerEmailHelper {

	private final PersistentServices persistentServices;
	
	public TemplateOwnerEmailHelper(final PersistentServices persistentServices) {
		this.persistentServices = persistentServices;
	}
	
	public final String extractTemplateOwnerEmails(final List<String> templateNames) {
		List<String> ownerEmails = new ArrayList<>();
		templateNames.stream()
				.forEach(templateName -> {
					String ownerEmail =  persistentServices.getTemplateOwnerEmail(templateName);
					ownerEmails.add(ownerEmail);
				});
		return String.join(Strings.COMMA, ownerEmails);
	}
}

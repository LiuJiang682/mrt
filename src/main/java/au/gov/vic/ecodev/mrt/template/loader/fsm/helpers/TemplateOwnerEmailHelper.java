package au.gov.vic.ecodev.mrt.template.loader.fsm.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

import au.gov.vic.ecodev.mrt.template.processor.services.PersistentServices;

public class TemplateOwnerEmailHelper {

	private final PersistentServices persistentServices;
	
	public TemplateOwnerEmailHelper(final PersistentServices persistentServices) {
		this.persistentServices = persistentServices;
	}
	
	public final List<Map<String, Object>> extractTemplateOwnerEmails(final List<String> templateNames) {
		List<Map<String, Object>> ownerEmails = new ArrayList<>();
		templateNames.stream()
				.forEach(templateName -> {
					Map<String, Object> ownerEmail =  persistentServices.getTemplateOwnerEmail(templateName);
					if (MapUtils.isNotEmpty(ownerEmail)) {
						ownerEmails.add(ownerEmail);
					}
				});
		return ownerEmails;
	}
}

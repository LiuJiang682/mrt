package au.gov.vic.ecodev.mrt.data.record.cleaner.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;

import au.gov.vic.ecodev.mrt.data.record.cleaner.persistent.model.DefaultSession;
import au.gov.vic.ecodev.mrt.data.record.cleaner.persistent.model.Session;
import au.gov.vic.ecodev.utils.strings.StringToUniqueListHelper;

public class SessionHelper {

	private final List<Map<String, Object>> result;
	
	public SessionHelper(List<Map<String, Object>> result) {
		if (CollectionUtils.isEmpty(result)) {
			throw new IllegalArgumentException("SessionHelper:result parameter cannot be null or empty!");
		}
		this.result = result;
	}

	public List<Session> getSession() {
		List<Session> sessionsToClean = new ArrayList<>();
		result.stream()
		.forEach(map -> {
			Object object = map.get("ID");
			long sessionId;
			if (object instanceof BigDecimal) {
				sessionId = ((BigDecimal)object).longValue();
			} else if (object instanceof Long) {
				sessionId = (long) object;
			} else {
				sessionId = Long.parseLong((String)object);
			}
			
			String template = (String) map.get("TEMPLATE");
			List<String> uniqueTemplateList = StringToUniqueListHelper
					.extractUniqueTemplate(template);
			Session session = new DefaultSession(sessionId, uniqueTemplateList);
			sessionsToClean.add(session);
		});
		return sessionsToClean;
	}

}

package au.gov.vic.ecodev.mrt.data.record.cleaner.persistent.model;

import java.util.List;

import org.springframework.util.CollectionUtils;

public class DefaultSession implements Session {

	private final long sessionId;
	private final List<String> templateList;
	
	public DefaultSession(final long sessionId, final List<String> templateList) {
		this.sessionId = sessionId;
		if (CollectionUtils.isEmpty(templateList)) {
			throw new IllegalArgumentException("DefaultSession:templateList parameter cannot be null or empty!");
		}
		this.templateList = templateList;
	}
	
	@Override
	public long getSessionId() {
		return sessionId;
	}

	public List<String> getTemplateList() {
		return templateList;
	}

}

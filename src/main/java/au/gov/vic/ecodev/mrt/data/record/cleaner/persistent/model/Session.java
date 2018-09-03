package au.gov.vic.ecodev.mrt.data.record.cleaner.persistent.model;

import java.util.List;

public interface Session {

	public long getSessionId();
	public List<String> getTemplateList();
}

package au.gov.vic.ecodev.mrt.data.record.cleaner.persistent;

import java.util.List;
import java.util.Map;

public interface PersistentService {

	public List<Map<String, Object>> getSessions();

	public String getDisplayProperties(String template);

	public void deleteByTableNameAndSessionId(String table, long sessionId);
}

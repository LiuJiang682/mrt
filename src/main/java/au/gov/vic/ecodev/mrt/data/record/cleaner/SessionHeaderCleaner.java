package au.gov.vic.ecodev.mrt.data.record.cleaner;

import au.gov.vic.ecodev.mrt.data.record.cleaner.persistent.PersistentService;
import au.gov.vic.ecodev.mrt.data.record.cleaner.persistent.model.Session;

public class SessionHeaderCleaner {

	private final Session session;
	private final PersistentService persistentService;
	
	public SessionHeaderCleaner(Session session, PersistentService persistenService) {
		if (null == session) {
			throw new IllegalArgumentException("SessionHeaderCleaner:session parameter cannot be null!");
		}
		this.session = session;
		if (null == persistenService) {
			throw new IllegalArgumentException("SessionHeaderCleaner:persistenService parameter cannot be null!");
		}
		this.persistentService = persistenService;
	}

	public void clean() {
		persistentService.deleteSessionHeaderById(session.getSessionId());
	}

}

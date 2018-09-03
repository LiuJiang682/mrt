package au.gov.vic.ecodev.mrt.data.record.cleaner;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.gov.vic.ecodev.mrt.data.record.cleaner.helper.SessionHelper;
import au.gov.vic.ecodev.mrt.data.record.cleaner.persistent.PersistentService;
import au.gov.vic.ecodev.mrt.data.record.cleaner.persistent.model.Session;

@Service("dataRecordCleaner")
public class DataRecordCleanerImpl implements DataRecordCleaner {

	private static final Logger LOGGER = Logger.getLogger(DataRecordCleanerImpl.class);
	
	@Autowired
	private PersistentService persistenService;

	
	@Override
	public void clean() {
		LOGGER.info("Starting the data cleanning...");

		List<Map<String, Object>> result = persistenService.getSessions();
		List<Session> sessionsToClean = new SessionHelper(result).getSession();
		sessionsToClean.stream()
			.forEach(session -> {
				new DbSessionCleaner(session, persistenService).clean();
			});
	}

}

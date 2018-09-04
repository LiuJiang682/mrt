package au.gov.vic.ecodev.mrt.data.record.cleaner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.gov.vic.ecodev.mrt.config.MrtConfigProperties;
import au.gov.vic.ecodev.mrt.data.record.cleaner.helper.SessionHelper;
import au.gov.vic.ecodev.mrt.data.record.cleaner.persistent.PersistentService;
import au.gov.vic.ecodev.mrt.data.record.cleaner.persistent.model.Session;

@Service("dataRecordCleaner")
public class DataRecordCleanerImpl implements DataRecordCleaner {

	private static final Logger LOGGER = Logger.getLogger(DataRecordCleanerImpl.class);
	
	@Autowired
	private PersistentService persistenService;
	
	@Autowired
	private MrtConfigProperties mrtConfigProperties;

	
	@Override
	public void clean() {
		LOGGER.info("Starting the data cleanning...");

		List<Map<String, Object>> result = persistenService.getSessions();
		List<Session> sessionsToClean = new SessionHelper(result).getSession();
		Map<String, List<String>> templateClassListMap = new HashMap<>();
		sessionsToClean.stream()
			.forEach(session -> {
				new DbSessionCleaner(session, persistenService, templateClassListMap).clean();
				new FileSessionCleaner(session, mrtConfigProperties).clean();
				new SessionHeaderCleaner(session, persistenService).clean();
			});
	}

}

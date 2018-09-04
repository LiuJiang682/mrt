package au.gov.vic.ecodev.mrt.data.record.cleaner;

import java.io.File;

import au.gov.vic.ecodev.mrt.config.MrtConfigProperties;
import au.gov.vic.ecodev.mrt.data.record.cleaner.persistent.model.Session;

public class FileSessionCleaner {

	private final Session session;
	private final MrtConfigProperties mrtConfigProperties;
	
	public FileSessionCleaner(Session session, MrtConfigProperties mrtConfigProperties) {
		if (null == session) {
			throw new IllegalArgumentException("FileSessionCleaner:session parameter cannot be null!");
		}
		this.session = session;
		if (null == mrtConfigProperties) {
			throw new IllegalArgumentException("FileSessionCleaner:mrtConfigProperties parameter cannot be null!");
		}
		this.mrtConfigProperties = mrtConfigProperties;
	}

	public void clean() {
		String failedDirectory = mrtConfigProperties.getFailedFileDirectory();
		File file = new File(failedDirectory + "/" + session.getSessionId());
		if (file.exists()) {
			file.delete();
		}
	}

}

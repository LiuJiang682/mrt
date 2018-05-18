package au.gov.vic.ecodev.mrt.map.services.helper;

import org.apache.log4j.Logger;

import au.gov.vic.ecodev.mrt.config.MrtConfigProperties;
import au.gov.vic.ecodev.mrt.mail.Mailer;

public class ExceptionEmailer {

	private static final Logger LOGGER = Logger.getLogger(ExceptionEmailer.class);
	
	private final MrtConfigProperties mrtConfigProperties;
	
	public ExceptionEmailer(MrtConfigProperties mrtConfigProperties) {
		if (null == mrtConfigProperties) {
			throw new IllegalArgumentException("ExceptionEmailer:mrtConfigProperties cannot be null!");
		}
		this.mrtConfigProperties = mrtConfigProperties;
	}

	public void sendEmail(Exception reportinException) {
		Mailer mailer = new Mailer(mrtConfigProperties);
		mailer.createSession();
		String body = new ExceptionEmailBodyBuilder(reportinException).build();
		try {
			mailer.send(mrtConfigProperties.getToEmail(), 
					mrtConfigProperties.getEmailSubject(), body , 
					mrtConfigProperties.getEmailUser(), 
					mrtConfigProperties.getEmailUserPwd());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		
	}

}

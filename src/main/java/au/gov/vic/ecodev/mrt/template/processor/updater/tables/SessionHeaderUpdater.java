package au.gov.vic.ecodev.mrt.template.processor.updater.tables;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.common.util.NullSafeCollections;
import au.gov.vic.ecodev.common.util.StringToDateConventor;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.dao.SessionHeaderDao;
import au.gov.vic.ecodev.mrt.model.SessionHeader;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.persistent.Dao;

public class SessionHeaderUpdater {

	private static final String KEY_H0102 = "H0102";
	private static final String KEY_H0003 = "H0003";
	private static final String DATE_FORMAT_DD_MMM_YY = "dd-MMM-yy";
	private static final String KEY_H0101 = "H0101";
	private static final String KEY_H0100 = "H0100";
	private final List<Dao> daos;
	private final long sessionId;
	private final Template template;
	
	public SessionHeaderUpdater(List<Dao> daos, long sessionId, Template template) {
		if (CollectionUtils.isEmpty(daos)) {
			throw new IllegalArgumentException("DAO list cannot be empty!");
		}
		if (null == template) {
			throw new IllegalArgumentException("Parameter template cannot be null!");
		}
		this.daos = daos;
		this.sessionId = sessionId;
		this.template = template;
	}
	
	protected final SessionHeaderDao getSessionHeaderDao() throws TemplateProcessorException {
		Optional<Dao> sessionHeaderDaoOptional = daos.stream()
			.filter(dao -> dao instanceof SessionHeaderDao)
			.findFirst();
		
		return	(SessionHeaderDao) sessionHeaderDaoOptional.orElseThrow(() -> new TemplateProcessorException("No SessionHeaderDao in the list"));
	}



	public void update() throws TemplateProcessorException {
		SessionHeaderDao sessionHeaderDao = getSessionHeaderDao();
		SessionHeader sessionHeader = (SessionHeader) sessionHeaderDao.get(sessionId);
		if (null == sessionHeader) {
			throw new TemplateProcessorException("Cannot retrieve session header with sessionId: " + sessionId);
		}
		sessionHeader.setProcessDate(new Date());
		sessionHeader.setTenement((String) new NullSafeCollections(template.get(KEY_H0100))
				.get(Numeral.ONE));
		sessionHeader.setTenementHolder((String) new NullSafeCollections(template.get(KEY_H0101))
				.get(Numeral.ONE));
		Date reportingDate = null;
		try {
			reportingDate = new StringToDateConventor(DATE_FORMAT_DD_MMM_YY)
					.parse((String) new NullSafeCollections(template.get(KEY_H0003))
							.get(Numeral.ONE));
			sessionHeader.setReportingDate(reportingDate);
		} catch (ParseException e) {
			throw new TemplateProcessorException(e.getMessage(), e);
		}
		sessionHeader.setProjectName((String) new NullSafeCollections(template.get(KEY_H0102))
				.get(Numeral.ONE));
		sessionHeaderDao.updateOrSave(sessionHeader);
	}

}

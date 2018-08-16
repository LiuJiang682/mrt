package au.gov.vic.ecodev.mrt.template.processor.services;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import au.gov.vic.ecodev.common.util.IDGenerator;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.constants.LogSeverity;
import au.gov.vic.ecodev.mrt.dao.SessionHeaderDao;
import au.gov.vic.ecodev.mrt.dao.StatusLogDao;
import au.gov.vic.ecodev.mrt.dao.TemplateConfigDao;
import au.gov.vic.ecodev.mrt.dao.TemplateContextPropertiesDao;
import au.gov.vic.ecodev.mrt.dao.TemplateUpdaterConfigDao;
import au.gov.vic.ecodev.mrt.model.SessionHeader;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.persistent.Dao;
import au.gov.vic.ecodev.mrt.template.processor.update.TemplateUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.TemplateUpdaterFactory;
import au.gov.vic.ecodev.mrt.template.utils.Utils;

@Service
public class DbPersistentServices implements PersistentServices {
	
	private static final Logger LOGGER = Logger.getLogger(DbPersistentServices.class);
	
	private static final long SAVING_FAILED = Numeral.NEGATIVE_ONE;

	private final TemplateConfigDao templateConfigDao;
	private final StatusLogDao statusLogDao;
	private final SessionHeaderDao sessionHeaderDao;
	private final TemplateUpdaterConfigDao templateUpdaterConfigDao;
	private final TemplateContextPropertiesDao templateContextPropertiesDao;
	
	@Autowired
	public DbPersistentServices(final TemplateConfigDao templateConfigDao, 
			final StatusLogDao statusLogDao,
			final SessionHeaderDao sessionHeaderDao,
			final TemplateUpdaterConfigDao templateUpdaterConfigDao,
			final TemplateContextPropertiesDao templateContextPropertiesDao) {
		if (null == templateConfigDao) {
			throw new IllegalArgumentException("Parameter templateConfigDao cannot be null!");
		}
		if (null == statusLogDao) {
			throw new IllegalArgumentException("Parameter statusLogDao cannot be null!");
		}
		if (null == sessionHeaderDao) {
			throw new IllegalArgumentException("Parameter sessionHeaderDao cannot be null!");
		}
		if (null == templateUpdaterConfigDao) {
			throw new IllegalArgumentException("Parameter templateUpdaterConfigDao cannot be null!");
		}
		if (null == templateContextPropertiesDao) {
			throw new IllegalArgumentException("Parameter templateContextPropertiesDao cannot be null!");
		}
		this.templateConfigDao = templateConfigDao;
		this.statusLogDao = statusLogDao;
		this.sessionHeaderDao = sessionHeaderDao;
		this.templateUpdaterConfigDao = templateUpdaterConfigDao;
		this.templateContextPropertiesDao = templateContextPropertiesDao;
	}

	@Override
	public long getNextBatchId(List<String> templateNames, List<String> fileNames) {
		Set<String> templateSet = new HashSet<>(templateNames);
		long sessionId = IDGenerator.getUIDAsAbsLongValue();
		SessionHeader sessionHeader = new SessionHeader(sessionId);
		sessionHeader.setTemplate(String.join(Strings.COMMA, templateSet));
		sessionHeader.setFileName(String.join(Strings.COMMA, fileNames));
		boolean successSaved = sessionHeaderDao.updateOrSave(sessionHeader);
		if (!successSaved) {
			sessionId = SAVING_FAILED;
		}
		return sessionId;
	}

	@Override
	public boolean saveStatusLog(long batchId, LogSeverity severity, String logMessage) {
		return statusLogDao.saveStatusLog(batchId, severity, logMessage);
	}

	@Override
	public String getTemplateClasses(String templateName) {
		return templateConfigDao.getTemplateClasses(templateName);
	}

	@Override
	public List<String> getErrorMessageByBatchId(long batchId, LogSeverity severity) {
		return statusLogDao.getErrorMessageByBatchId(batchId, severity);
	}

	@Override
	public boolean saveDataBean(final JdbcTemplate jdbcTemplate, long sessionId, Template template) {
		boolean successSaved = false;
		//TODO -- Remove this
		Utils.displayTemplateData(template);
		TemplateUpdater updater = TemplateUpdaterFactory.getTemplateUpdater(template, this);
		LOGGER.info(updater);
		if (null != updater) {
			try {
				List<Class<? extends Dao>> classList = updater.getDaoClasses(); 
				LOGGER.info(classList);
				List<Dao> daos = new DaoFactory(jdbcTemplate, classList).getDaos();
				LOGGER.info(daos);
				updater.setDaos(daos);
				updater.update(sessionId, template);
				successSaved = true;
			} catch (TemplateProcessorException e) {
				String errorMessage = "Failed to save template data due to " + e.getMessage();
				saveStatusLog(sessionId, LogSeverity.ERROR, errorMessage);
				LOGGER.error(errorMessage, e);
			}
		}
		return successSaved;
	}

	@Override
	public String getTemplateUpdaterClass(String templateName) {
		return templateUpdaterConfigDao.getTemplateUpdaterClass(templateName);
	}

	@Override
	public String getTemplateContextProperty(String templateName, String propertyName) {
		return templateContextPropertiesDao.getTemplateContextProperty(templateName, propertyName);
	}

	@Override
	public Map<String, Object> getTemplateOwnerEmail(String templateName) {
		return templateConfigDao.getOwnerEmailProperties(templateName);
	}

}

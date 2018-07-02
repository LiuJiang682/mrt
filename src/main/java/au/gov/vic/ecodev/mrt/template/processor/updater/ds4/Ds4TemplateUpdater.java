package au.gov.vic.ecodev.mrt.template.processor.updater.ds4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.dao.TemplateOptionalFieldDao;
import au.gov.vic.ecodev.mrt.dao.TemplateOptionalFieldDaoImpl;
import au.gov.vic.ecodev.mrt.dao.ds4.DownHoleDao;
import au.gov.vic.ecodev.mrt.dao.ds4.DownHoleDaoImpl;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.persistent.Dao;
import au.gov.vic.ecodev.mrt.template.processor.update.TemplateUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.NumberOfRecordsTemplateExtractor;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.TemplateHeaderH1000FieldUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.TemplateHeaderOptionalFieldUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.TemplateOptionalFieldUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.ds4.DownHoleUpdater;

public class Ds4TemplateUpdater implements TemplateUpdater {

	private static final List<String> TEMPLATE_PERSISTENT_KEY_LIST = Arrays.asList("H1001", "H1004");
	
	private List<Dao> daos;
	
	@Override
	public void setDaos(List<Dao> daos) {
		this.daos = daos;
	}

	@Override
	public void update(long sessionId, Template template) throws TemplateProcessorException {
		DownHoleDao downHoleDao = getDownHoleDao();
		TemplateOptionalFieldDao templateOptionalFieldDao = getTemplateOptionalFieldDao();
		List<Integer> mandatoryFieldIndexList = new ArrayList<>();
		
		try {
			int numOfRecords = new NumberOfRecordsTemplateExtractor()
					.extractNumOfRecordsFromTemplate(template);
			DownHoleUpdater downHoleUpdater = new DownHoleUpdater(downHoleDao, sessionId, template);
			downHoleUpdater.init(mandatoryFieldIndexList);
			TemplateOptionalFieldUpdater templateOptionalFiledUpdater = 
					new TemplateOptionalFieldUpdater(sessionId, template, 
					templateOptionalFieldDao);
			templateOptionalFiledUpdater.init(mandatoryFieldIndexList);
			
			TemplateHeaderH1000FieldUpdater templateHeaderH1000Updater = 
					new TemplateHeaderH1000FieldUpdater(sessionId, 
							template.get(Strings.KEY_H1000), 
							templateOptionalFieldDao, Strings.TEMPLATE_NAME_DS4);
			templateHeaderH1000Updater.update();
			
			TemplateHeaderOptionalFieldUpdater templateHeaderOptionalFieldUpdater = 
					new TemplateHeaderOptionalFieldUpdater(sessionId, template, 
							templateOptionalFieldDao, TEMPLATE_PERSISTENT_KEY_LIST);
			templateHeaderOptionalFieldUpdater.update();
			
			for (int index = Numeral.ONE; index <= numOfRecords; index++) {
				List<String> dataRecordList = template.get(Strings.DATA_RECORD_PREFIX + index);
				downHoleUpdater.update(dataRecordList);
				templateOptionalFiledUpdater.update(dataRecordList, index);
			}		
			templateOptionalFiledUpdater.flush();
		} catch(Exception e) {
			throw new TemplateProcessorException(e.getMessage(), e);
		}
	}

	protected final DownHoleDao getDownHoleDao() throws TemplateProcessorException {
		Optional<Dao> downHoleDao = daos.stream()
				.filter(dao -> dao instanceof DownHoleDao)
				.findFirst();
		return (DownHoleDao) downHoleDao.orElseThrow(() -> new TemplateProcessorException("No DownHoleDao in the list"));
	}

	@Override
	public List<Class<? extends Dao>> getDaoClasses() {
		List<Class<? extends Dao>> daoClasses = new ArrayList<>();
		daoClasses.add(DownHoleDaoImpl.class);
		daoClasses.add(TemplateOptionalFieldDaoImpl.class);
		return daoClasses;
	}

	protected final TemplateOptionalFieldDao getTemplateOptionalFieldDao() throws TemplateProcessorException {
		Optional<Dao> TemplateOptionalFieldDao = daos.stream()
				.filter(dao -> dao instanceof TemplateOptionalFieldDao)
				.findFirst();
		return (TemplateOptionalFieldDao) TemplateOptionalFieldDao.orElseThrow(() -> new TemplateProcessorException("No TemplateOptionalFieldDao in the list"));
	}
}

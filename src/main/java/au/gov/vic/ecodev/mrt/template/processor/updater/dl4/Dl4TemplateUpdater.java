package au.gov.vic.ecodev.mrt.template.processor.updater.dl4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.dao.TemplateOptionalFieldDao;
import au.gov.vic.ecodev.mrt.dao.TemplateOptionalFieldDaoImpl;
import au.gov.vic.ecodev.mrt.dao.dl4.LithologyDao;
import au.gov.vic.ecodev.mrt.dao.dl4.LithologyDaoImpl;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.persistent.Dao;
import au.gov.vic.ecodev.mrt.template.processor.update.TemplateUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.NumberOfRecordsTemplateExtractor;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.TemplateHeaderH1000FieldUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.TemplateHeaderOptionalFieldUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.TemplateOptionalFieldUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.dl4.LithologyUpdater;

public class Dl4TemplateUpdater implements TemplateUpdater {

	private static final List<String> TEMPLATE_PERSISTENT_KEY_LIST = Arrays.asList("H1001", "H1004");
	
	private List<Dao> daos;
	
	@Override
	public void setDaos(List<Dao> daos) {
		this.daos = daos;
	}

	@Override
	public void update(long sessionId, Template template) throws TemplateProcessorException {
		LithologyDao lithologyDao = getLithologyDao();
		TemplateOptionalFieldDao templateOptionalFieldDao = getTemplateOptionalFieldDao();
		List<Integer> mandatoryFieldIndexList = new ArrayList<>();
		
		try {
			LithologyUpdater lithologyUpdater = new LithologyUpdater(lithologyDao, sessionId, template);
			lithologyUpdater.init(mandatoryFieldIndexList);
			TemplateOptionalFieldUpdater templateOptionalFiledUpdater = 
					new TemplateOptionalFieldUpdater(sessionId, template, 
					templateOptionalFieldDao);
			templateOptionalFiledUpdater.init(mandatoryFieldIndexList);
			int numOfRecords = new NumberOfRecordsTemplateExtractor()
					.extractNumOfRecordsFromTemplate(template);
			
			TemplateHeaderH1000FieldUpdater templateHeaderH1000Updater = 
					new TemplateHeaderH1000FieldUpdater(sessionId, 
							template, templateOptionalFieldDao, Strings.TEMPLATE_NAME_DL4);
			templateHeaderH1000Updater.update();
			
			TemplateHeaderOptionalFieldUpdater templateHeaderOptionalFieldUpdater = 
					new TemplateHeaderOptionalFieldUpdater(sessionId, template, 
							templateOptionalFieldDao, TEMPLATE_PERSISTENT_KEY_LIST);
			templateHeaderOptionalFieldUpdater.update();
			
			for(int index = Numeral.ONE; index <= numOfRecords; index++) {
				List<String> dataRecordList = template.get(Strings.DATA_RECORD_PREFIX + index);
				lithologyUpdater.update(dataRecordList, index);
				templateOptionalFiledUpdater.update(dataRecordList, index);
			}
			templateOptionalFiledUpdater.flush();
		} catch(Exception e) {
			throw new TemplateProcessorException(e.getMessage(), e);
		}
	}

	@Override
	public List<Class<? extends Dao>> getDaoClasses() {
		List<Class<? extends Dao>> daoClasses = new ArrayList<>();
		daoClasses.add(LithologyDaoImpl.class);
		daoClasses.add(TemplateOptionalFieldDaoImpl.class);
		return daoClasses;
	}

	protected final LithologyDao getLithologyDao() throws TemplateProcessorException {
		Optional<Dao> lithologyDao = daos.stream()
				.filter(dao -> dao instanceof LithologyDao)
				.findFirst();
		
		return (LithologyDao) lithologyDao.orElseThrow(() -> new TemplateProcessorException("No LithologyDao in the list"));
	}

	protected final TemplateOptionalFieldDao getTemplateOptionalFieldDao() throws TemplateProcessorException {
		Optional<Dao> templateOptionalFieldDao = daos.stream()
				.filter(dao -> dao instanceof TemplateOptionalFieldDao)
				.findFirst();
		return (TemplateOptionalFieldDao) templateOptionalFieldDao.orElseThrow(() -> new TemplateProcessorException("No TemplateOptionalFieldDao in the list"));
	}

}

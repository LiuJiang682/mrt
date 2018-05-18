package au.gov.vic.ecodev.mrt.template.processor.updater.sg4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.dao.TemplateOptionalFieldDao;
import au.gov.vic.ecodev.mrt.dao.TemplateOptionalFieldDaoImpl;
import au.gov.vic.ecodev.mrt.dao.sg4.SurfaceGeochemistryDao;
import au.gov.vic.ecodev.mrt.dao.sg4.SurfaceGeochemistryDaoImpl;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.persistent.Dao;
import au.gov.vic.ecodev.mrt.template.processor.update.TemplateUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.NumberOfRecordsTemplateExtractor;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.TemplateHeaderH1000FieldUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.TemplateHeaderOptionalFieldUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.TemplateOptionalFieldUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.sg4.SurfaceGeochemistryUpdater;

public class Sg4TemplateUpdater implements TemplateUpdater {

	private static final List<String> TEMPLATE_PERSISTENT_KEY_LIST = Arrays.asList("H1001", "H1002", "H1003");
	
	private List<Dao> daos;
	
	@Override
	public void setDaos(List<Dao> daos) {
		this.daos = daos;
	}

	@Override
	public void update(long sessionId, Template template) throws TemplateProcessorException {
		SurfaceGeochemistryDao surfaceGeochemistryDao = getSurfaceGeochemistryDao();
		TemplateOptionalFieldDao templateOptionalFieldDao = getTemplateOptionalFieldDao();
		List<Integer> mandatoryFieldIndexList = new ArrayList<>();
		
		try {
			TemplateHeaderH1000FieldUpdater templateHeaderH1000Updater = 
					new TemplateHeaderH1000FieldUpdater(sessionId, template.get(Strings.KEY_H1000), 
							templateOptionalFieldDao, Strings.TEMPLATE_NAME_SG4);
			templateHeaderH1000Updater.update();
			TemplateHeaderOptionalFieldUpdater templateHeaderOptionalFieldUpdater = 
					new TemplateHeaderOptionalFieldUpdater(sessionId, template, 
							templateOptionalFieldDao, TEMPLATE_PERSISTENT_KEY_LIST);
			templateHeaderOptionalFieldUpdater.update();
			
			SurfaceGeochemistryUpdater surfaceGeochemistryUpdater = new SurfaceGeochemistryUpdater(surfaceGeochemistryDao, 
					sessionId, template);
			surfaceGeochemistryUpdater.init(mandatoryFieldIndexList);
			TemplateOptionalFieldUpdater templateOptionalFiledUpdater = 
					new TemplateOptionalFieldUpdater(sessionId, template, 
					templateOptionalFieldDao);
			templateOptionalFiledUpdater.init(mandatoryFieldIndexList);
			int numOfRecords = new NumberOfRecordsTemplateExtractor()
					.extractNumOfRecordsFromTemplate(template);
			for(int index = Numeral.ONE; index <= numOfRecords; index++) {
				List<String> dataRecordList = template.get(Strings.DATA_RECORD_PREFIX + index);
				surfaceGeochemistryUpdater.update(dataRecordList);
				templateOptionalFiledUpdater.update(dataRecordList, index);
			}
		} catch(Exception e) {
			throw new TemplateProcessorException(e.getMessage(), e);
		}
	}

	@Override
	public List<Class<? extends Dao>> getDaoClasses() {
		List<Class<? extends Dao>> daoClasses = new ArrayList<>();
		daoClasses.add(SurfaceGeochemistryDaoImpl.class);
		daoClasses.add(TemplateOptionalFieldDaoImpl.class);
		return daoClasses;
	}

	protected final SurfaceGeochemistryDao getSurfaceGeochemistryDao() throws TemplateProcessorException {
		Optional<Dao> surfaceGeochemistryDaoOptional = daos.stream()
				.filter(dao -> dao instanceof SurfaceGeochemistryDao)
				.findFirst();
		return (SurfaceGeochemistryDao) surfaceGeochemistryDaoOptional
				.orElseThrow(() -> new TemplateProcessorException("No SurfaceGeochemistryDao in the list"));
	}

	protected final TemplateOptionalFieldDao getTemplateOptionalFieldDao() throws TemplateProcessorException {
		Optional<Dao> TemplateOptionalFieldDao = daos.stream()
				.filter(dao -> dao instanceof TemplateOptionalFieldDao)
				.findFirst();
		return (TemplateOptionalFieldDao) TemplateOptionalFieldDao
				.orElseThrow(() -> new TemplateProcessorException("No TemplateOptionalFieldDao in the list"));
	}
}
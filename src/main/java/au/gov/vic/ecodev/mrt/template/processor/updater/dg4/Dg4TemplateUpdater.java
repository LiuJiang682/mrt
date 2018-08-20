package au.gov.vic.ecodev.mrt.template.processor.updater.dg4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.dao.TemplateMandatoryHeaderFieldDao;
import au.gov.vic.ecodev.mrt.dao.TemplateMandatoryHeaderFieldDaoImpl;
import au.gov.vic.ecodev.mrt.dao.TemplateOptionalFieldDao;
import au.gov.vic.ecodev.mrt.dao.TemplateOptionalFieldDaoImpl;
import au.gov.vic.ecodev.mrt.dao.dg4.GeoChemistryDao;
import au.gov.vic.ecodev.mrt.dao.dg4.GeoChemistryDaoImpl;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.persistent.Dao;
import au.gov.vic.ecodev.mrt.template.processor.update.TemplateUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.NumberOfRecordsTemplateExtractor;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.TemplateHeaderH1000FieldUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.TemplateHeaderOptionalFieldUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.TemplateMandatoryHeaderUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.TemplateOptionalFieldUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.dg4.GeoChemistryUpdater;

public class Dg4TemplateUpdater implements TemplateUpdater {

	private static final List<String> TEMPLATE_PERSISTENT_KEY_LIST = Arrays.asList("H1001", "H1002", "H1003", "H1004", "H1005", "H1006", "H1007");
	private static final List<String> TEMPLATE_OPTIONAL_FIELDS_PERSISTEN_KEY_LIST = Arrays.asList("H1000", "H1001", "H1002", "H1003", "H1004", "H1005", "H1006", "H1007");
	
	private List<Dao> daos;
	
	@Override
	public void setDaos(List<Dao> daos) {
		this.daos = daos;
	}

	@Override
	public void update(long sessionId, Template template) throws TemplateProcessorException {
		GeoChemistryDao geoChemistryDao = getGeoChemistryDao();
		TemplateMandatoryHeaderFieldDao templateMandatoryHeaderFieldDao = 
				getTemplateMandatoryHeaderFieldDao();
		TemplateOptionalFieldDao templateOptionalFieldDao = getTemplateOptionalFieldDao();
		List<Integer> mandatoryFieldIndexList = new ArrayList<>();
		
		try {
			TemplateHeaderH1000FieldUpdater templateHeaderH1000Updater = 
					new TemplateHeaderH1000FieldUpdater(sessionId, template, 
							templateOptionalFieldDao, Strings.TEMPLATE_NAME_DG4);
			templateHeaderH1000Updater.update();
			
			GeoChemistryUpdater geoChemistryUpdater = new GeoChemistryUpdater(geoChemistryDao, 
					sessionId, template);
			geoChemistryUpdater.init(mandatoryFieldIndexList);
			
			TemplateOptionalFieldUpdater templateOptionalFiledUpdater = 
					new TemplateOptionalFieldUpdater(sessionId, template, 
					templateOptionalFieldDao);
			templateOptionalFiledUpdater.init(mandatoryFieldIndexList);
			
			TemplateMandatoryHeaderUpdater templatemandatoryUpdater = 
					new TemplateMandatoryHeaderUpdater(
							sessionId,
							templateMandatoryHeaderFieldDao, 
							mandatoryFieldIndexList, TEMPLATE_PERSISTENT_KEY_LIST,
							template);
			templatemandatoryUpdater.update();
			
			TemplateHeaderOptionalFieldUpdater templateHeaderOptionalFieldUpdater = 
					new TemplateHeaderOptionalFieldUpdater(sessionId, template, 
							mandatoryFieldIndexList,
							templateOptionalFieldDao, TEMPLATE_OPTIONAL_FIELDS_PERSISTEN_KEY_LIST);
			templateHeaderOptionalFieldUpdater.update();
			int numOfRecords = new NumberOfRecordsTemplateExtractor()
					.extractNumOfRecordsFromTemplate(template);
			int len = TEMPLATE_OPTIONAL_FIELDS_PERSISTEN_KEY_LIST.size();
			
			for(int index = Numeral.ONE; index <= numOfRecords; index++) {
				List<String> dataRecordList = template.get(Strings.DATA_RECORD_PREFIX + index);
				geoChemistryUpdater.update(dataRecordList, index);
				templateOptionalFiledUpdater.update(dataRecordList, index + len);
			}
			templateOptionalFiledUpdater.flush();
		} catch(Exception e) {
			throw new TemplateProcessorException(e.getMessage(), e);
		}
	}

	@Override
	public List<Class<? extends Dao>> getDaoClasses() {
		List<Class<? extends Dao>> daoClasses = new ArrayList<>();
		daoClasses.add(GeoChemistryDaoImpl.class);
		daoClasses.add(TemplateMandatoryHeaderFieldDaoImpl.class);
		daoClasses.add(TemplateOptionalFieldDaoImpl.class);
		return daoClasses;
	}

	protected final GeoChemistryDao getGeoChemistryDao() throws TemplateProcessorException {
		Optional<Dao> geoChemistryDao = daos.stream()
				.filter(dao -> dao instanceof GeoChemistryDao)
				.findFirst();
		return (GeoChemistryDao) geoChemistryDao.orElseThrow(() -> new TemplateProcessorException("No GeoChemistryDao in the list"));
	}

	protected final TemplateOptionalFieldDao getTemplateOptionalFieldDao() throws TemplateProcessorException {
		Optional<Dao> TemplateOptionalFieldDao = daos.stream()
				.filter(dao -> dao instanceof TemplateOptionalFieldDao)
				.findFirst();
		return (TemplateOptionalFieldDao) TemplateOptionalFieldDao
				.orElseThrow(() -> new TemplateProcessorException("No TemplateOptionalFieldDao in the list"));
	}
	
	protected TemplateMandatoryHeaderFieldDao getTemplateMandatoryHeaderFieldDao() 
			throws TemplateProcessorException {
		Optional<Dao> templateMandatoryHeaderFieldDao = daos.stream()
				.filter(dao -> dao instanceof TemplateMandatoryHeaderFieldDao)
				.findFirst();
		return (TemplateMandatoryHeaderFieldDao) templateMandatoryHeaderFieldDao
				.orElseThrow(() ->  new TemplateProcessorException("No TemplateMandatoryHeaderFieldDao in the list"));
	}

}

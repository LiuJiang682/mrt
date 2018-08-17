package au.gov.vic.ecodev.mrt.template.processor.updater.sl4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.gov.vic.ecodev.mrt.dao.SessionHeaderDaoImpl;
import au.gov.vic.ecodev.mrt.dao.TemplateMandatoryHeaderFieldDaoImpl;
import au.gov.vic.ecodev.mrt.dao.TemplateOptionalFieldDaoImpl;
import au.gov.vic.ecodev.mrt.dao.sl4.BoreHoleDaoImpl;
import au.gov.vic.ecodev.mrt.dao.sl4.DrillingDetailsDaoImpl;
import au.gov.vic.ecodev.mrt.dao.sl4.SiteDaoImpl;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.persistent.Dao;
import au.gov.vic.ecodev.mrt.template.processor.update.TemplateUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.SessionHeaderUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.sl4.DrillingDetailsUpdater;

public class Sl4TemplateUpdater implements TemplateUpdater {

	private List<Dao> daos;
	private Map<String, Long> drillingCodes = new HashMap<>();
	
	@Override
	public void setDaos(List<Dao> daos) {
		this.daos = daos;
	}

	@Override
	public void update(long sessionId, Template template) throws TemplateProcessorException {
		try {
			SessionHeaderUpdater sessionHeaderUpdater = new SessionHeaderUpdater(daos, 
					sessionId, template);
			sessionHeaderUpdater.update();
			DrillingDetailsUpdater drillingDetailsUpdater = new DrillingDetailsUpdater(daos, 
					template, drillingCodes);
			drillingDetailsUpdater.update();
			Sl4DataRecordUpdater sl4DataRecordUpdater = new Sl4DataRecordUpdater(daos, 
					sessionId, template, drillingCodes);
			sl4DataRecordUpdater.update();
		} catch(Exception e) {
			throw new TemplateProcessorException(e.getMessage(), e);
		}
	}

	@Override
	public List<Class<? extends Dao>> getDaoClasses() {
		List<Class<? extends Dao>> daoClasses = new ArrayList<>();
		daoClasses.add(SessionHeaderDaoImpl.class);
		daoClasses.add(DrillingDetailsDaoImpl.class);
		daoClasses.add(SiteDaoImpl.class);
		daoClasses.add(BoreHoleDaoImpl.class);
		daoClasses.add(TemplateMandatoryHeaderFieldDaoImpl.class);
		daoClasses.add(TemplateOptionalFieldDaoImpl.class);
		// This bit of code is attempted to verify the plugin jar is loaded.
		//	try {
		//		Class<? extends Dao> test = (Class<? extends Dao>) Class.forName("au.gov.vic.ecodev.mrt.template.processor.persistent.CustomDao");
		//		daoClasses.add(test);
		//	} catch (ClassNotFoundException e) {			
		//		e.printStackTrace();
		//	}
		return daoClasses;
	}

}

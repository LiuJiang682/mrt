package au.gov.vic.ecodev.mrt.template.processor.updater.sl4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.dao.TemplateOptionalFieldDao;
import au.gov.vic.ecodev.mrt.dao.sl4.BoreHoleDao;
import au.gov.vic.ecodev.mrt.dao.sl4.SiteDao;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.MrtTemplateValue;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.persistent.Dao;
import au.gov.vic.ecodev.mrt.template.processor.updater.NumberOfRecordsTemplateExtractor;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.TemplateHeaderH1000FieldUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.TemplateHeaderOptionalFieldUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.TemplateOptionalFieldUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.sl4.BoreHoleUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.sl4.SiteUpdater;

public class Sl4DataRecordUpdater {

	private static final List<String> TEMPLATE_PERSISTENT_KEY_LIST = Arrays.asList("H1001", "H1004");
	
	private final List<Dao> daos;
	private final long sessionId;
	private final Template template;
	private final Map<String, Long> drillingCodes;
	
	public Sl4DataRecordUpdater(List<Dao> daos, long sessionId, Template template, 
			Map<String, Long> drillingCodes) {
		if (CollectionUtils.isEmpty(daos)) {
			throw new IllegalArgumentException("Parameter daos cannot be empty!");
		}
		if (null == template) {
			throw new IllegalArgumentException("Parameter template cannot be empty!");
		}
		if (CollectionUtils.isEmpty(drillingCodes)) {
			throw new IllegalArgumentException("Parameter drillingCodes cannot be empty!");
		}
		this.daos = daos;
		this.sessionId = sessionId;
		this.template = template;
		this.drillingCodes = drillingCodes;
	}

	public void update() throws TemplateProcessorException {
		int numOfRecords = new NumberOfRecordsTemplateExtractor()
				.extractNumOfRecordsFromTemplate(template);
		List<Integer> mandatoryFieldIndexList = new ArrayList<>();
		
		SiteDao siteDao = getSiteDao();
		SiteUpdater siteUpdater = new SiteUpdater(sessionId, template, siteDao);
		siteUpdater.init(mandatoryFieldIndexList);
		
		BoreHoleDao boreHoleDao = getBoreHoleDao();
		BoreHoleUpdater boreHoleUpdater = new BoreHoleUpdater(sessionId, template, boreHoleDao, drillingCodes);
		boreHoleUpdater.init(mandatoryFieldIndexList);
		
		TemplateOptionalFieldDao templateOptionalFieldDao = getTemplateOptionalFieldDao();
		TemplateOptionalFieldUpdater templateOptionalFiledUpdater = new TemplateOptionalFieldUpdater(sessionId, template, 
				templateOptionalFieldDao);
		templateOptionalFiledUpdater.init(mandatoryFieldIndexList);
		
		TemplateHeaderH1000FieldUpdater templateHeaderH1000Updater = 
				new TemplateHeaderH1000FieldUpdater(sessionId, 
						template, templateOptionalFieldDao, Strings.TEMPLATE_NAME_SL4);
		templateHeaderH1000Updater.update();
		
		TemplateHeaderOptionalFieldUpdater templateHeaderOptionalFieldUpdater = 
				new TemplateHeaderOptionalFieldUpdater(sessionId, template, 
						templateOptionalFieldDao, TEMPLATE_PERSISTENT_KEY_LIST);
		templateHeaderOptionalFieldUpdater.update();
		
		for (int index = Numeral.ONE; index <= numOfRecords; index++) {
			MrtTemplateValue mrtTemplateValue = (MrtTemplateValue) template
					.getTemplateValue(Strings.DATA_RECORD_PREFIX + index);
			siteUpdater.update(mrtTemplateValue, index);
			List<String> dataRecordList = mrtTemplateValue.getDatas();
			boreHoleUpdater.update(dataRecordList, index);
			templateOptionalFiledUpdater.update(dataRecordList, index);
		}
		
		templateOptionalFiledUpdater.flush();
	}

	protected final TemplateOptionalFieldDao getTemplateOptionalFieldDao() throws TemplateProcessorException {
		Optional<Dao> templateOptionalFieldDao = daos.stream()
				.filter(dao -> dao instanceof TemplateOptionalFieldDao)
				.findFirst();
		return (TemplateOptionalFieldDao) templateOptionalFieldDao
				.orElseThrow(() ->  new TemplateProcessorException("No TemplateOptionalFieldDao in the list"));
	}

	protected final BoreHoleDao getBoreHoleDao() throws TemplateProcessorException {
		Optional<Dao> boreHoleDaoOptional = daos.stream()
				.filter(dao -> dao instanceof BoreHoleDao)
				.findFirst();
		return (BoreHoleDao) boreHoleDaoOptional.orElseThrow(() -> new TemplateProcessorException("No BoreHoleDao in the list"));
	}

	protected final SiteDao getSiteDao() throws TemplateProcessorException {
		Optional<Dao> siteDaoOptional = daos.stream()
				.filter(dao -> dao instanceof SiteDao)
				.findFirst();
		return (SiteDao) siteDaoOptional.orElseThrow(() -> new TemplateProcessorException("No SiteDao in the list"));
	}

}

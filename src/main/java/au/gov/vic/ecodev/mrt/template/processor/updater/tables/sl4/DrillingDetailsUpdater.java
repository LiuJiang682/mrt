package au.gov.vic.ecodev.mrt.template.processor.updater.tables.sl4;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.common.util.IDGenerator;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.dao.sl4.DrillingDetailsDao;
import au.gov.vic.ecodev.mrt.model.sl4.DrillingDetails;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.persistent.Dao;
import au.gov.vic.ecodev.mrt.template.processor.updater.helper.FileNameExtractionHelper;

public class DrillingDetailsUpdater {

	private static final String KEY_H0402 = "H0402";
	private static final String KEY_H0401 = "H0401";
	private static final String KEY_H0400 = "H0400";
	
	private final List<Dao> daos;
	private final Template template;
	private final Map<String, Long> drillingCodes;
	
	public DrillingDetailsUpdater(final List<Dao> daos, final Template template,
			final Map<String, Long> drillingCodes) {
		if (CollectionUtils.isEmpty(daos)) {
			throw new IllegalArgumentException("Parameter daos cannot be empty!");
		}
		if (null == template) {
			throw new IllegalArgumentException("Parameter template cannot be null!");
		}
		if (null == drillingCodes) {
			throw new IllegalArgumentException("Parameter drilingCodes cannot be null!");
		}
		this.daos = daos;
		this.template = template;
		this.drillingCodes = drillingCodes;
	}

	protected final DrillingDetailsDao getDrillingDetailsDao() throws TemplateProcessorException {
		Optional<Dao> DrillingDetailsDaoOptional = daos.stream()
				.filter(dao -> dao instanceof DrillingDetailsDao)
				.findFirst();
		return (DrillingDetailsDao) DrillingDetailsDaoOptional.orElseThrow(() -> new TemplateProcessorException("No DrillingDetailsDao in the list"));
	}

	public void update() throws TemplateProcessorException {
		List<String> drillingCodeList = template.get(KEY_H0400);
		List<String> drillingCompanyList = template.get(KEY_H0401);
		List<String> drillingDescriptionList = template.get(KEY_H0402);
		String fileName = new FileNameExtractionHelper(template, Strings.CURRENT_FILE_NAME)
				.doFileNameExtraction();
		int drillingCodeListSize = (null == drillingCodeList) ? Numeral.ZERO : drillingCodeList.size();
		
		DrillingDetailsDao drillingDetailsDao = getDrillingDetailsDao();
		for(int index = Numeral.ONE; index < drillingCodeListSize; index++) {
			String drillingCode = drillingCodeList.get(index);
			String drillingCompany = drillingCompanyList.get(index);
			String drillingDescription = drillingDescriptionList.get(index);
			DrillingDetails drillingDetails = drillingDetailsDao.getByDrillingTypeAndCompany(drillingCode, drillingCompany);
			if (null == drillingDetails) {
				drillingDetails = new DrillingDetails();
				drillingDetails.setId(IDGenerator.getUID().longValue());
				drillingDetails.setFileName(fileName);
				drillingDetails.setDrillType(drillingCode);
				drillingDetails.setDrillCompany(drillingCompany);
				drillingDetails.setDrillDescription(drillingDescription);
			} else {
				drillingDetails.setDrillDescription(drillingDescription);
			}
			boolean saved = drillingDetailsDao.updateOrSave(drillingDetails);
			if (saved) {
				drillingCodes.put(drillingDetails.getDrillType(), drillingDetails.getId());
			}
		}
	}

}

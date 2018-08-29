package au.gov.vic.ecodev.mrt.template.processor.updater.tables;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import au.gov.vic.ecodev.common.util.IDGenerator;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.dao.TemplateMandatoryHeaderFieldDao;
import au.gov.vic.ecodev.mrt.model.TemplateMandatoryHeaderField;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.utils.file.helper.FileNameExtractionHelper;

public class TemplateMandatoryHeaderUpdater {

	private final long sessionId;
	private final TemplateMandatoryHeaderFieldDao templateMandatoryHeaderFieldDao;
	private final List<Integer> mandatoryFieldIndexList;
	private final List<String> templatePersistentKeyList;
	private final Template template;
	
	private String fileName;
	
	public TemplateMandatoryHeaderUpdater(
			final long sessionId,
			final TemplateMandatoryHeaderFieldDao templateMandatoryHeaderFieldDao,
			final List<Integer> mandatoryFieldIndexList, 
			final List<String> templatePersistentKeyList,
			final Template template) {
		this.sessionId = sessionId;
		if (null == templateMandatoryHeaderFieldDao) {
			throw new IllegalArgumentException("TemplateMandatoryHeaderUpdater:templateMandatoryHeaderFieldDao paremeter cannot be null!");
		}
		this.templateMandatoryHeaderFieldDao = templateMandatoryHeaderFieldDao;
		if (CollectionUtils.isEmpty(mandatoryFieldIndexList)) {
			throw new IllegalArgumentException("TemplateMandatoryHeaderUpdater:mandatoryFieldIndexList parameter cannot be null or empty!");
		}
		this.mandatoryFieldIndexList = mandatoryFieldIndexList;
		if (CollectionUtils.isEmpty(templatePersistentKeyList)) {
			throw new IllegalArgumentException("TemplateMandatoryHeaderUpdater:templatePersistentKeyList parameter cannot be null or empty!");
		}
		this.templatePersistentKeyList = templatePersistentKeyList;
		if (null == template) {
			throw new IllegalArgumentException("TemplateMandatoryHeaderUpdater:template paremeter cannot be null!");
		}
		this.template = template;
	}

	public void update() {
		this.fileName = new FileNameExtractionHelper(template, Strings.CURRENT_FILE_NAME)
				.doFileNameExtraction();
		List<String> headers = template.get(Strings.KEY_H1000);
		String templateName = getTemplateName(template.getClass().getSimpleName());
		templatePersistentKeyList.stream()
			.forEach(key -> {
				List<String> values = template.get(key);
				if (CollectionUtils.isNotEmpty(values)) {
					mandatoryFieldIndexList.stream()
						.forEach(index -> {
							String columnHeader = headers.get(index);
							String value = values.get(index);
							if (StringUtils.isNotBlank(value)) {
								TemplateMandatoryHeaderField templateMandatoryHeaderField = 
										new TemplateMandatoryHeaderField();
								templateMandatoryHeaderField.setId(IDGenerator.getUIDAsAbsLongValue());
								templateMandatoryHeaderField.setSessionId(sessionId);
								templateMandatoryHeaderField.setTemplateName(templateName);
								templateMandatoryHeaderField.setFileName(fileName);
								templateMandatoryHeaderField.setRowNumber(key);
								templateMandatoryHeaderField.setColumnHeader(columnHeader);
								templateMandatoryHeaderField.setFieldValue(value);
								templateMandatoryHeaderFieldDao.updateOrSave(templateMandatoryHeaderField);
							}
						});
				}
			});
	}

	private String getTemplateName(String templateName) {
		String name = templateName.substring(Numeral.ZERO, templateName.indexOf(Strings.TEMPLATE));
		return name.toUpperCase();
	}
}

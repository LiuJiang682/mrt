package au.gov.vic.ecodev.mrt.template.processor.updater.tables;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import au.gov.vic.ecodev.common.util.IDGenerator;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.dao.TemplateOptionalFieldDao;
import au.gov.vic.ecodev.mrt.model.TemplateOptionalField;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.utils.file.helper.FileNameExtractionHelper;

public class TemplateHeaderH1000FieldUpdater {

	private final long sessionId;
	private final Template template;
	private final List<String> headers;
	private final List<Integer> mandatoryFieldIndexList;
	private final TemplateOptionalFieldDao templateOptionalFieldDao;
	private final String templateName;
	
	public TemplateHeaderH1000FieldUpdater(long sessionId, final Template template,
			final TemplateOptionalFieldDao templateOptionalFieldDao, 
			final List<Integer> mandatoryFieldIndexList,
			final String templateName) {
		this.sessionId = sessionId;
		if (null == template) {
			throw new IllegalArgumentException("TemplateHeaderH1000FieldUpdater:template parameter cannot be null!");
		}
		this.template = template;
		List<String> headers = template.get(Strings.KEY_H1000);
		if (CollectionUtils.isEmpty(headers)) {
			throw new IllegalArgumentException("TemplateHeaderH1000FieldUpdater:headers parameter cannot be null or empty!");
		}
		this.headers = headers;
		if (null == templateOptionalFieldDao) {
			throw new IllegalArgumentException("TemplateHeaderH1000FieldUpdater:templateOptionalFieldDao parameter cannot be null!");
		}
		this.templateOptionalFieldDao = templateOptionalFieldDao;
		if (CollectionUtils.isEmpty(mandatoryFieldIndexList)) {
			throw new IllegalArgumentException("TemplateHeaderH1000FieldUpdater:mandatoryFieldIndexList parameter cannot be null or empty!");
		}
		this.mandatoryFieldIndexList = mandatoryFieldIndexList;
		if (StringUtils.isEmpty(templateName)) {
			throw new IllegalArgumentException("TemplateHeaderH1000FieldUpdater:templateName parameter cannot be null or empty!");
		}
		this.templateName = templateName;
	}

	public void update() {
		int len = headers.size();
		List<String> mandatoryFields = new ArrayList<>();
		List<String> optionalFields = new ArrayList<>();
		for (int i = Numeral.ZERO; i < len; i++ ) {
			if (mandatoryFieldIndexList.contains(i)) {
				mandatoryFields.add(headers.get(i));
			} else {
				optionalFields.add(headers.get(i));
			}
		}
		mandatoryFields.addAll(optionalFields);
		String fileName = new FileNameExtractionHelper(template, Strings.CURRENT_FILE_NAME)
				.doFileNameExtraction();
		TemplateOptionalField templateOptionalField = new TemplateOptionalField();
		templateOptionalField.setId(IDGenerator.getUIDAsAbsLongValue());
		templateOptionalField.setSessionId(sessionId);
		templateOptionalField.setFileName(fileName);
		templateOptionalField.setTemplateName(templateName);
		templateOptionalField.setTemplateHeader(Strings.KEY_H1000);
		templateOptionalField.setRowNumber(Strings.ZERO);
		templateOptionalField.setColumnNumber(Numeral.ZERO);
		String fieldValue = String.join(Strings.COMMA, mandatoryFields);
		templateOptionalField.setFieldValue(fieldValue);
		templateOptionalFieldDao.updateOrSave(templateOptionalField);
		
	}

}

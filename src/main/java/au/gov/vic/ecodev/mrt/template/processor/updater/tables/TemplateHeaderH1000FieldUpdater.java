package au.gov.vic.ecodev.mrt.template.processor.updater.tables;

import java.util.List;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import au.gov.vic.ecodev.common.util.IDGenerator;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.dao.TemplateOptionalFieldDao;
import au.gov.vic.ecodev.mrt.model.TemplateOptionalField;

public class TemplateHeaderH1000FieldUpdater {

	private final long sessionId;
	private final List<String> headers;
	private final TemplateOptionalFieldDao templateOptionalFieldDao;
	private final String templateName;
	
	public TemplateHeaderH1000FieldUpdater(long sessionId, final List<String> headers,
			final TemplateOptionalFieldDao templateOptionalFieldDao, final String templateName) {
		this.sessionId = sessionId;
		if (CollectionUtils.isEmpty(headers)) {
			throw new IllegalArgumentException("TemplateHeaderH1000FieldUpdater:headers parameter cannot be null or empty!");
		}
		this.headers = headers;
		if (null == templateOptionalFieldDao) {
			throw new IllegalArgumentException("TemplateHeaderH1000FieldUpdater:templateOptionalFieldDao parameter cannot be null!");
		}
		this.templateOptionalFieldDao = templateOptionalFieldDao;
		if (StringUtils.isEmpty(templateName)) {
			throw new IllegalArgumentException("TemplateHeaderH1000FieldUpdater:templateName parameter cannot be null or empty!");
		}
		this.templateName = templateName;
	}

	public void update() {
		TemplateOptionalField templateOptionalField = new TemplateOptionalField();
		templateOptionalField.setId(IDGenerator.getUID().longValue());
		templateOptionalField.setSessionId(sessionId);
		templateOptionalField.setTemplateName(templateName);
		templateOptionalField.setTemplateHeader(Strings.KEY_H1000);
		templateOptionalField.setRowNumber(Strings.KEY_H1000);
		String fieldValue = String.join(Strings.COMMA, headers);
		templateOptionalField.setFieldValue(fieldValue);
		templateOptionalFieldDao.updateOrSave(templateOptionalField);
		
	}

}
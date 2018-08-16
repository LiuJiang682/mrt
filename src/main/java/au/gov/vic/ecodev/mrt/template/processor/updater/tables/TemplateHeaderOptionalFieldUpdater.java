package au.gov.vic.ecodev.mrt.template.processor.updater.tables;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.common.util.IDGenerator;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.dao.TemplateOptionalFieldDao;
import au.gov.vic.ecodev.mrt.model.TemplateOptionalField;
import au.gov.vic.ecodev.mrt.template.processor.model.Entity;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.updater.helper.FileNameExtractionHelper;
import au.gov.vic.ecodev.mrt.template.processor.updater.helper.LabelledColumnIndexListExtractor;

public class TemplateHeaderOptionalFieldUpdater {

	private final long sessionId;
	private final Template template;
	private final TemplateOptionalFieldDao templateOptionalFieldDao;
	private final List<String> keys;
	
	private String fileName;
	private List<Entity> cache;
	
	public TemplateHeaderOptionalFieldUpdater(long sessionId, Template template,
			TemplateOptionalFieldDao templateOptionalFieldDao, List<String> keys) {
		this.sessionId = sessionId;
		if (null == template) {
			throw new IllegalArgumentException("TemplateHeaderOptionalFieldUpdater:template cannot be null!");
		}
		this.template = template;
		if (null == templateOptionalFieldDao) {
			throw new IllegalArgumentException("TemplateHeaderOptionalFieldUpdater:templateOptionalFieldDao cannot be null!");
		}
		this.templateOptionalFieldDao = templateOptionalFieldDao;
		if (CollectionUtils.isEmpty(keys)) {
			throw new IllegalArgumentException("TemplateHeaderOptionalFieldUpdater:keys cannot be null or empty!");
		}
		this.keys = keys;
		cache = new ArrayList<>();
	}

	public void update() {
		this.fileName = new FileNameExtractionHelper(template, Strings.CURRENT_FILE_NAME)
				.doFileNameExtraction();
		List<String> headers = template.get(Strings.KEY_H1000);
		String templateName = getTemplateName(template.getClass().getSimpleName());
		List<Integer> duplicatedKeyIndexList = 
				new LabelledColumnIndexListExtractor(template)
					.getColumnIndexListByStartWith(Strings.KEY_PREFIX_DUPLICATED);
		keys.stream()
			.forEach(key -> {
				List<String> values = template.get(key);
				if (!CollectionUtils.isEmpty(values)) {
					int len  = values.size();
					for (int index = Numeral.ZERO; index < len; index++) {
						String value = values.get(index);
						if (StringUtils.isNotBlank(value)) {
							TemplateOptionalField templateOptionalField = new TemplateOptionalField();
							templateOptionalField.setId(IDGenerator.getUIDAsAbsLongValue());
							templateOptionalField.setSessionId(sessionId);
							templateOptionalField.setFileName(fileName);
							templateOptionalField.setTemplateName(templateName);
							String header = headers.get(index);
							if (duplicatedKeyIndexList.contains(index)) {
								header += index;
							}
							templateOptionalField.setTemplateHeader(header);
							templateOptionalField.setRowNumber(key);
							templateOptionalField.setFieldValue(value);
//							templateOptionalFieldDao.updateOrSave(templateOptionalField);
							cache.add(templateOptionalField);
						}
					}
				}
			});
		templateOptionalFieldDao.batchUpdate(cache);
	}

	private String getTemplateName(String templateName) {
		String name = templateName.substring(Numeral.ZERO, templateName.indexOf(Strings.TEMPLATE));
		return name.toUpperCase();
	}
}

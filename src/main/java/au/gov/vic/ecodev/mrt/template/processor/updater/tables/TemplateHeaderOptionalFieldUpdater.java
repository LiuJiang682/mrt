package au.gov.vic.ecodev.mrt.template.processor.updater.tables;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.common.util.IDGenerator;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.dao.TemplateOptionalFieldDao;
import au.gov.vic.ecodev.mrt.model.TemplateOptionalField;
import au.gov.vic.ecodev.mrt.template.processor.model.Entity;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.utils.file.helper.FileNameExtractionHelper;

public class TemplateHeaderOptionalFieldUpdater {

	private static final String NOT_RECORDED = "NR";
	
	private final long sessionId;
	private final Template template;
	private final List<Integer> mandatoryFieldIndexList;
	private final TemplateOptionalFieldDao templateOptionalFieldDao;
	private final List<String> keys;
	
	private String fileName;
	private List<Entity> cache;
	
	public TemplateHeaderOptionalFieldUpdater(long sessionId, Template template,
			final List<Integer> mandatoryFieldIndexList, 
			TemplateOptionalFieldDao templateOptionalFieldDao, List<String> keys) {
		this.sessionId = sessionId;
		if (null == template) {
			throw new IllegalArgumentException("TemplateHeaderOptionalFieldUpdater:template cannot be null!");
		}
		this.template = template;
		if (null == mandatoryFieldIndexList) {
			throw new IllegalArgumentException("TemplateHeaderOptionalFieldUpdater:templateMandatoryHeaderFieldDao cannot be null!");
		}
		this.mandatoryFieldIndexList = mandatoryFieldIndexList;
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
		int len = headers.size();
		String templateName = getTemplateName(template.getClass().getSimpleName());
		AtomicInteger rowCounter = new AtomicInteger();
		keys.stream()
			.forEach(key -> {
				List<String> values = template.get(key);
				if (CollectionUtils.isEmpty(values)) {
					values = getNotRecordedValues(len);
				}
				String rowNumber = String.valueOf(rowCounter.incrementAndGet());
				AtomicInteger columnCounter = new AtomicInteger();
				int valueLen = values.size();
				for (int index = Numeral.ZERO; index < len; index++) {
					if (!mandatoryFieldIndexList.contains(index)) {
						String value = NOT_RECORDED;
						if (index < valueLen) {
							value  = values.get(index);
						}
						if (StringUtils.isBlank(value)) {
							value = NOT_RECORDED;
						}
						TemplateOptionalField templateOptionalField = new TemplateOptionalField();
						templateOptionalField.setId(IDGenerator.getUIDAsAbsLongValue());
						templateOptionalField.setSessionId(sessionId);
						templateOptionalField.setFileName(fileName);
						templateOptionalField.setTemplateName(templateName);
//						String header = headers.get(index);
						templateOptionalField.setTemplateHeader(key);
						templateOptionalField.setRowNumber(rowNumber);
						templateOptionalField.setColumnNumber(columnCounter.incrementAndGet());
						templateOptionalField.setFieldValue(value);
//						templateOptionalFieldDao.updateOrSave(templateOptionalField);
						cache.add(templateOptionalField);
					}
				}
			});
		templateOptionalFieldDao.batchUpdate(cache);
	}

	private List<String> getNotRecordedValues(int size) {
		List<String> values = new ArrayList<>(size);
		for(int i =  Numeral.ZERO; i < size; i++) {
			values.add(NOT_RECORDED);
		}
		return values;
	}

	private String getTemplateName(String templateName) {
		String name = templateName.substring(Numeral.ZERO, templateName.indexOf(Strings.TEMPLATE));
		return name.toUpperCase();
	}
}

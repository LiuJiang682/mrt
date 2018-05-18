package au.gov.vic.ecodev.mrt.template.processor.updater.tables;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.common.util.IDGenerator;
import au.gov.vic.ecodev.common.util.NullSafeCollections;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.dao.TemplateOptionalFieldDao;
import au.gov.vic.ecodev.mrt.model.TemplateOptionalField;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.updater.helper.LabelledColumnIndexListExtractor;

public class TemplateOptionalFieldUpdater {

	private final TemplateOptionalFieldDao templateOptionalFieldDao;
	private final long sessionId;
	private final Template template;
	
	private List<Integer> indexList;
	
	public TemplateOptionalFieldUpdater(long sessionId, Template template,
			TemplateOptionalFieldDao templateOptionalFieldDao) {
		this.sessionId = sessionId;
		if (null == template) {
			throw new IllegalArgumentException("TemplateOptionalFieldUpdater:template parameter cannot be null!");
		}
		this.template = template;
		if (null == templateOptionalFieldDao) {
			throw new IllegalArgumentException("TemplateOptionalFieldUpdater:templateOptionalFieldDao parameter cannot be null!");
		}
		this.templateOptionalFieldDao = templateOptionalFieldDao;
	}

	public void init(List<Integer> mandatoryFieldIndexList) throws TemplateProcessorException {
		List<String> headers = template.get(Strings.KEY_H1000);
		if (CollectionUtils.isEmpty(headers)) {
			throw new TemplateProcessorException("No column header pass down!");
		} else {
			int len = headers.size();
			if (mandatoryFieldIndexList.size() < len) {
				indexList = new ArrayList<>();
				for(int index = Numeral.ZERO; index < len; index++) {
					if (!mandatoryFieldIndexList.contains(index)) {
						indexList.add(index);
					}
				}
			}
		}
	}

	public List<Integer> getIndexList() {
		return indexList;
	}

	public void update(List<String> dataRecordList, int rowNumber) {
		if (!CollectionUtils.isEmpty(indexList)) {
			List<String> headers = template.get(Strings.KEY_H1000);
			String templateName = getTemplateName(template.getClass().getSimpleName());
			List<Integer> duplicatedKeyIndexList = 
					new LabelledColumnIndexListExtractor(template)
						.getColumnIndexListByStartWith(Strings.KEY_PREFIX_DUPLICATED);
			indexList.stream()
				.forEach(index -> {
					TemplateOptionalField templateOptionalField = new TemplateOptionalField();
					templateOptionalField.setId(IDGenerator.getUID().longValue());
					templateOptionalField.setSessionId(sessionId);
					templateOptionalField.setTemplateName(templateName);
					String header = headers.get(index);
					if (duplicatedKeyIndexList.contains(index)) {
						header += index;
					}
					templateOptionalField.setTemplateHeader(header);
					templateOptionalField.setRowNumber(String.valueOf(rowNumber));
					templateOptionalField.setFieldValue(
							(String) new NullSafeCollections(dataRecordList).get(index));
					templateOptionalFieldDao.updateOrSave(templateOptionalField);
				});
		}
	}

	private String getTemplateName(String templateName) {
		String name = templateName.substring(Numeral.ZERO, templateName.indexOf(Strings.TEMPLATE));
		return name.toUpperCase();
	}	
}
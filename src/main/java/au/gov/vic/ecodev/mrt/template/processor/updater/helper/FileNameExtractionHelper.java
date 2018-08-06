package au.gov.vic.ecodev.mrt.template.processor.updater.helper;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import au.gov.vic.ecodev.common.util.NullSafeCollections;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class FileNameExtractionHelper {

	private final Template template;
	private final String key;
	
	public FileNameExtractionHelper(final Template template, final String key) {
		if (null == template) {
			throw new IllegalArgumentException("FileNameExtractionHelper:template parameter cannot be null!");
		}
		this.template = template;
		if (StringUtils.isEmpty(key)) {
			throw new IllegalArgumentException("FileNameExtractionHelper:key parameter cannot be null or empty!");
		}
		this.key = key;
	}
	
	public String doFileNameExtraction() {
		List<String> fileNameList = template.get(key);
		String fileName = (String) new NullSafeCollections(fileNameList).get(Numeral.ZERO);
		return fileName;
	}
}

package au.gov.vic.ecodev.mrt.template.processor.file.validator.common.helper;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.utils.file.FileNameUtils;

public class FileNameHelper {

	private static final Logger LOGGER = Logger.getLogger(FileNameHelper.class);
	private static final String DELIM = System.getProperty("file.separator");
	
	private final String fileName;
	
	public FileNameHelper(String fileName) {
		if (StringUtils.isBlank(fileName)) {
			throw new IllegalArgumentException("FileNameHelper -- fileName parameter cannot be null!");
		}
		this.fileName = fileName;
	}

	public boolean isPartailFileName() {
		boolean isPartialFileName = false;
		String simpleFileName = fileName;
		int index = fileName.lastIndexOf(DELIM);
		if (Numeral.NOT_FOUND < index) {
			simpleFileName = fileName.substring(index + Numeral.ONE);
		}
		
		try {
			isPartialFileName = FileNameUtils.INSTANCE.isPatternMatchedFileName(simpleFileName);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return isPartialFileName;
	}

}

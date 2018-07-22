package au.gov.vic.ecodev.mrt.template.files;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.common.db.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.file.TemplateFileSelector;

public class H0202HeaderTemplateFileSelector implements TemplateFileSelector {

	private static final Logger LOGGER = Logger.getLogger(H0202HeaderTemplateFileSelector.class);
	
	private static final String DATA_FORMAT_HEADER = "H0202";
	private static final String DATA_FORMAT = "Data_format";
	public static final String DATA_FORMAT_SL4 = "SL4";
	
	private String directory;

	public Optional<List<String>> getTemplateFileInDirectory(final List<String> dataTemplate) throws Exception {
		if (CollectionUtils.isEmpty(dataTemplate)) {
			throw new IllegalArgumentException("Parameter dataTemplate cannot be null!");
		}
		if (StringUtils.isBlank(directory)) {
			throw new IllegalArgumentException("Parameter directory cannot be null or empty!");
		}
		List<File> files = new DirectoryFilesScanner(directory).scan();
		return findTemplateFileName(files, dataTemplate);
	}

	protected final Optional<List<String>> findTemplateFileName(List<File> files, List<String> dataTemplate) {
		Optional<List<String>> slTemplateFileName = Optional.empty();
		List<String> fileNames = new ArrayList<>();
		for (File file : files) {
			LineNumberReader lineNumberReader;
			String str;
			
			try {
				lineNumberReader = new LineNumberReader(new FileReader(file));
				while(null != (str = lineNumberReader.readLine())) { 
					if (str.trim().toUpperCase().startsWith(DATA_FORMAT_HEADER)) {
						String template = str.replace(DATA_FORMAT_HEADER, 
								au.gov.vic.ecodev.mrt.constants.Constants.Strings.EMPTY).trim();
						template = StringUtils.normalizeSpace(template);
						template = template.replace(DATA_FORMAT, 
								au.gov.vic.ecodev.mrt.constants.Constants.Strings.EMPTY).trim().toUpperCase();
						if (dataTemplate.contains(template)) {
							fileNames.add(template + Strings.SPACE + file.getAbsolutePath());
						}
						break;
					}
				}
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		if (Numeral.ZERO < fileNames.size()) {
			slTemplateFileName = Optional.of(fileNames);
		}
		
		return slTemplateFileName;
	}

	@Override
	public void setSelectionFileDirectory(String directory) {
		this.directory = directory;
	}
	
}

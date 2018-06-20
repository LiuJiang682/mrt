package au.gov.vic.ecodev.mrt.template.files;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;

public class TemplateFileSelector {

	private static final Logger LOGGER = Logger.getLogger(TemplateFileSelector.class);
	
	private static final String DATA_FORMAT_HEADER = "H0202";
	private static final String DATA_FORMAT = "Data_format";
	public static final String DATA_FORMAT_SL4 = "SL4";
	
	private final String directory;
	
	public TemplateFileSelector(String directory) {
		if (StringUtils.isBlank(directory)) {
			throw new IllegalArgumentException("Parameter directory cannot be null or empty!");
		}
		this.directory = directory;
		
	}

	public Optional<String> getTemplateFileInDirectory(final List<String> dataTemplate) throws IOException {
		if (CollectionUtils.isEmpty(dataTemplate)) {
			throw new IllegalArgumentException("Parameter dataTemplate cannot be null!");
		}
		List<File> files = new DirectoryFilesScanner(directory).scan();
		return findTemplateFileName(files, dataTemplate);
	}

	protected final Optional<String> findTemplateFileName(List<File> files, List<String> dataTemplate) {
		Optional<String> slTemplateFileName = Optional.empty();
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
							return Optional.of(template + Strings.SPACE + file.getAbsolutePath());
						}
					}
				}
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		
		return slTemplateFileName;
	}
	
}

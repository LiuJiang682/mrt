package au.gov.vic.ecodev.mrt.template.loader.fsm.helpers;

import java.util.List;
import java.util.stream.Collectors;

public class ZipFileFilter {

	private static final String ZIP_FILE_NAME_PATTERN = "^\\p{Alnum}+[_\\p{Alnum}]+\\.zip$";
	
	public final  List<String> filterZipFile(List<String> fileNames) {
		return fileNames.stream()
			.filter(fileName -> fileName.matches(ZIP_FILE_NAME_PATTERN))
			.collect(Collectors.toList());
	}
}

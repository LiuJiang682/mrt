package au.gov.vic.ecodev.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

public class StringListToFileListConvertor {

	public final List<File> convertToFile(List<String> fileNames) {
		List<File> files = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(fileNames)) {
			fileNames.stream()
				.forEach(fileName -> {
					File file = new File(fileName);
					files.add(file);
				});
		}
		return files;
	}
}

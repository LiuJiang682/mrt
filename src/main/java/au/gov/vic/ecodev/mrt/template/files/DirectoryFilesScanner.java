package au.gov.vic.ecodev.mrt.template.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class DirectoryFilesScanner {

	private final String directory;
	
	public DirectoryFilesScanner(String directory) {
		if (StringUtils.isBlank(directory)) {
			throw new IllegalArgumentException("Parameter directory cannot be null or empty!");
		}
		this.directory = directory;
	}

	public List<File> scan() throws IOException {
		List<File> files = Files.walk(Paths.get(directory))
				.filter(Files::isRegularFile)
				.map(Path::toFile)
				.collect(Collectors.toList());
		return files;
	}

	
}

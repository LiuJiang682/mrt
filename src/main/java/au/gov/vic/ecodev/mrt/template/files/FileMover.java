package au.gov.vic.ecodev.mrt.template.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

public class FileMover {

	private static final Logger LOGGER = Logger.getLogger(FileMover.class);
	
	private final List<File> zipFiles;
	
	public FileMover(List<File> zipFiles) {
		if (CollectionUtils.isEmpty(zipFiles)) {
			throw new IllegalArgumentException("Parameter zipFiles cannot be null or empty!");
		}
		this.zipFiles = zipFiles;
	}

	public List<File> moveFile(String zipFileExtractDestination) {
		List<File> successMovedFiles = new ArrayList<>();
		if (StringUtils.isNotBlank(zipFileExtractDestination)) {
			Path path = Paths.get(zipFileExtractDestination);
			if (!Files.exists(path)) {
				try {
					Files.createDirectory(path);
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
					return successMovedFiles;
				}
			}
			if (Files.isWritable(path)) {
				zipFiles.stream()
					.forEach(file -> {
						String fileName = file.getName();
						Path source = Paths.get(file.getAbsolutePath());
						Path dest = Paths.get(zipFileExtractDestination + File.separator + fileName);
						try {
							Path newPath = Files.move(source, dest, StandardCopyOption.REPLACE_EXISTING);
							successMovedFiles.add(newPath.toFile());
						} catch (IOException e) {
							LOGGER.error(e.getMessage(), e);
						}
					});
			}
		}
		
		return successMovedFiles;
	}

}

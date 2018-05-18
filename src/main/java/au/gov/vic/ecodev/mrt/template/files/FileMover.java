package au.gov.vic.ecodev.mrt.template.files;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

public class FileMover {

	private final List<File> zipFiles;
	
	public FileMover(List<File> zipFiles) {
		if (CollectionUtils.isEmpty(zipFiles)) {
			throw new IllegalArgumentException("Parameter zipFiles cannot be null or empty!");
		}
		this.zipFiles = zipFiles;
	}

	public List<File> moveFile(String zipFileExtractDestination, boolean overwritten) {
		List<File> successMovedFiles = new ArrayList<>();
		if (StringUtils.isNotBlank(zipFileExtractDestination)) {
			Path path = Paths.get(zipFileExtractDestination);
			if (Files.isWritable(path)) {
				zipFiles.stream()
					.forEach(file -> {
						String fileName = file.getName();
						File newFile = new File(zipFileExtractDestination + File.separator + fileName); 
						if (newFile.exists() && overwritten) {
							newFile.delete();
						}
						boolean moveSuccess = file.renameTo(newFile);
						if (moveSuccess) {
							successMovedFiles.add(newFile);
						}
					});
			}
		}
		
		return successMovedFiles;
	}

}

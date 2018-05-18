package au.gov.vic.ecodev.mrt.template.files;

import java.io.File;

import org.springframework.util.StringUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;

public class DirectoryTreeReverseTraversalZipFileFinder {

	private final String parentName;
	
	public DirectoryTreeReverseTraversalZipFileFinder(String parentName) {
		if (StringUtils.isEmpty(parentName)) {
			throw new IllegalArgumentException("Parameter parentName cannot be empty!");
		}
		this.parentName = parentName;
	}

	public String findZipFile() {
		File file = new File(parentName);
		
		return backTracking(file);
	}
	
	protected final String backTracking(File file) {
		File parent = file.getParentFile();
		if (null == parent) {
			return null;
		}
		File[] filesInParent = parent.listFiles();
		for (File fileInParent : filesInParent) {
			if (fileInParent.isFile() 
					&& (fileInParent.getName().endsWith(Strings.ZIP_FILE_EXTENSION))) {
				String name = fileInParent.getName().replaceAll(Strings.ZIP_FILE_EXTENSION, Strings.EMPTY);
				if (this.parentName.contains(name)) {
					return fileInParent.getAbsolutePath();
				}
			}
		}
		
		return backTracking(parent);
	}
}

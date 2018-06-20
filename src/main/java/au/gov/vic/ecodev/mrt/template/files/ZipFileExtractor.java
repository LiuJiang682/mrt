package au.gov.vic.ecodev.mrt.template.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;

public class ZipFileExtractor {

	private static final int FILE_SIZE_1K = 1024;
	private final File file;
	
	public ZipFileExtractor(File file) {
		if (null == file) {
			throw new IllegalArgumentException("Parameter file cannot be null!");
		} else if (!file.getName().endsWith(Strings.ZIP_FILE_EXTENSION)) {
			throw new IllegalArgumentException("Parameter file " + file + " is not a zip file!");
		}
		
		this.file = file;
	}

	public File extract() throws IOException {
		File directory = createExtractDirectory();
		byte[] buf = new byte[FILE_SIZE_1K];
		try (ZipInputStream zis = new ZipInputStream(new FileInputStream(file))) {
			ZipEntry zipEntry = zis.getNextEntry();
			while (null != zipEntry) {
				String fileName = zipEntry.getName();
	            File newFile = new File(directory.getAbsolutePath() + File.separator + fileName);
	            createDirectoryIfNotExist(newFile.getParent());
	            try (FileOutputStream fos = new FileOutputStream(newFile)) {
	            	int len;
		            while ((len = zis.read(buf)) > Numeral.ZERO) {
		                fos.write(buf, Numeral.ZERO, len);
		            }
	            };
	            
	            zipEntry = zis.getNextEntry();
	        }
			zis.closeEntry();
		};
		
        return directory;
	}

	protected final void createDirectoryIfNotExist(String parent) {
		File parentDirectory = new File(parent);
		if (!parentDirectory.exists()) {
			createDirectory(parent);
		}
		
	}

	protected final File createExtractDirectory() {
		String fileName = file.getAbsolutePath();
		fileName = fileName.replace(Strings.ZIP_FILE_EXTENSION, 
				au.gov.vic.ecodev.mrt.constants.Constants.Strings.EMPTY);
		return createDirectory(fileName);
	}

	protected final File createDirectory(final String fileName) {
		File directory = new File(fileName);
		directory.mkdirs();
		return directory;
	}
}

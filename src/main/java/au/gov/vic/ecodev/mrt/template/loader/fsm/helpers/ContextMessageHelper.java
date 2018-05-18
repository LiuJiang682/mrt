package au.gov.vic.ecodev.mrt.template.loader.fsm.helpers;

import java.io.File;
import java.util.List;

import org.springframework.util.StringUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.Message;

public class ContextMessageHelper {

	private final Message message;
	
	public ContextMessageHelper(Message message) {
		if (null == message) {
			throw new IllegalArgumentException("Parameter message cannot be null!");
		}
		this.message = message;
	}

	public boolean addFailedFiles(String fileName) {
		File file = getFile(fileName);
		if (message.getPassedFiles().contains(file)) {
			synchronized (this) {
				message.getPassedFiles().remove(file);
			}
		}
		return addFile(message.getFailedFiles(), fileName);
	}
	
	public boolean addSuccessFiles(String fileName) {
		File file = getFile(fileName);
		if (!message.getFailedFiles().contains(file)) {
			return addFile(message.getPassedFiles(), fileName);
		}
		return true;
	}

	protected final boolean addFile(List<File> files, String fileName) {
		File file = getFile(fileName);
		if (!files.contains(file)) {
			files.add(file);
		}
		return true;
	}

	protected final File getFile(String fileName) {
		if (StringUtils.isEmpty(fileName)) {
			throw new IllegalArgumentException("Parameter fileName cannot be null!");
		}
		if (!fileName.endsWith(Strings.ZIP_FILE_EXTENSION)) {
			fileName = fileName + Strings.ZIP_FILE_EXTENSION;
		}
		return new File(fileName);
	}
	
}

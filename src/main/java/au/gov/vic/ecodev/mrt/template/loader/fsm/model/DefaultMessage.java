package au.gov.vic.ecodev.mrt.template.loader.fsm.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DefaultMessage implements Message, Serializable {

	private List<File> fileList;
	private List<String> fileNames;
	private List<String> templateClasses;
	private long batchId;
	private List<File> passedFiles;
	private List<File> failedFiles;
	private String logFileName;
	private String directErrorMessage;
	private String templateOwnerEmail;
	
	/**
	 * Generated serial verison UID
	 */
	private static final long serialVersionUID = 7169609988757271195L;

	@Override
	public List<File> getFileList() {
		return fileList;
	}

	@Override
	public void setFileList(List<File> fileList) {
		this.fileList = fileList;
	}

	@Override
	public List<String> getFileNames() {
		return fileNames;
	}

	@Override
	public void setFileNames(List<String> fileNames) {
		this.fileNames = fileNames;
	}

	@Override
	public List<String> getTemplateClasses() {
		return templateClasses;
	}

	@Override
	public void setTemplateClasses(List<String> templateClasses) {
		this.templateClasses = templateClasses;
	}

	@Override
	public long getBatchId() {
		return batchId;
	}

	@Override
	public void setBatchId(long batchId) {
		this.batchId = batchId;
	}

	@Override
	public List<File> getPassedFiles() {
		if (null == passedFiles) {
			passedFiles = new ArrayList<>();
		}
		return passedFiles;
	}

	@Override
	public void setPassedFiles(List<File> passedFiles) {
		this.passedFiles = passedFiles;
	}

	@Override
	public List<File> getFailedFiles() {
		if (null == failedFiles) {
			failedFiles = new ArrayList<>();
		}
		return failedFiles;
	}

	@Override
	public void setFailedFiles(List<File> failedFiles) {
		this.failedFiles = failedFiles;
	}

	@Override
	public String getLogFileName() {
		return logFileName;
	}

	@Override
	public void setLogFileName(String logFileName) {
		this.logFileName = logFileName;
	}

	@Override
	public void setDirectErrorMessage(String directErrorMessage) {
		this.directErrorMessage = directErrorMessage;
	}

	@Override
	public String getDirectErrorMessage() {
		return directErrorMessage;
	}

	@Override
	public String getTemplateOwnerEmail() {
		return templateOwnerEmail;
	}

	@Override
	public void setTemplateOwnerEmail(String templateOwnerEmail) {
		this.templateOwnerEmail = templateOwnerEmail;
	}
}

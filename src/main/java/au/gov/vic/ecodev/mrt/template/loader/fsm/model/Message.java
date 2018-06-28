package au.gov.vic.ecodev.mrt.template.loader.fsm.model;

import java.io.File;
import java.util.List;

public interface Message {

	public List<File> getFileList();
	public void setFileList(final List<File> fileList);
	
	public List<String> getFileNames();
	public void setFileNames(final List<String> fileNames);
	
	public List<String> getTemplateClasses();
	public void setTemplateClasses(final List<String> templateClasses);
	
	public long getBatchId();
	public void setBatchId(final long batchId);
	
	public List<File> getPassedFiles();
	public void setPassedFiles(final List<File> passedFileNames);
	
	public List<File> getFailedFiles();
	public void setFailedFiles(final List<File> failedFileNames);
	
	public String getLogFileName();
	public void setLogFileName(String logFileName);
	
	public String getDirectErrorMessage();
	public void setDirectErrorMessage(String string);
	
	public String getTemplateOwnerEmail();
	public void setTemplateOwnerEmail(final String owenerEmail);
}

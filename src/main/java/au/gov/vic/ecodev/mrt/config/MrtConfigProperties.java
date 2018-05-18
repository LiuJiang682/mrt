package au.gov.vic.ecodev.mrt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import au.gov.vic.ecodev.mrt.template.properties.TemplateProperties;

@Component
public class MrtConfigProperties implements TemplateProperties {

	@Value("${mrt.upload.file.dir}")
	private String directoryToScan;
	@Value("${mrt.ds.jdbc.driver.class}")
	private String driverClassName;
	@Value("${mrt.ds.url}")
	private String dsUrl;
	@Value("${mrt.ds.username}")
	private String dsUserName;
	@Value("${mrt.ds.pwd}")
	private String dsPwd;
	@Value("${mrt.zipfile.extract.destination}")
	private String zipFileExtractDestination;
	@Value("${mrt.template.file.api}")
	private String templateApiJarFile;
	@Value("${mrt.template.file.custom.jar.dir}")
	private String customTemplateJarFileDir;
	@Value("${mrt.template.processor.loaded.test:}")
	private String testLoaded;
	@Value("${mrt.logfile.output.dir}")
	private String logFileOutputDirectory;
	@Value("${mrt.passedfile.output.dir}")
	private String passedFileDirectory;
	@Value("${mrt.failedfile.output.dir}")
	private String failedFileDirectory;
	@Value("${mail.smtp.auth}")
	private String mailSmtpAuth;
	@Value("${mail.smtp.starttls.enable}")
	private String mailSmtpStartTlsEnable;
	@Value("${mail.smtp.host}")
	private String mailSmtpHost;
	@Value("${mail.smtp.port}")
	private String mailSmtpPort;
	@Value("${mail.toemail}")
	private String toEmail;
	@Value("${mail.email.subject}")
	private String emailSubject;
	@Value("${mail.email.user:}")
	private String emailUser;
	@Value("${mail.email.user.pwd:}")
	private String emailUserPwd;
	@Value("${mrt.model.package.scan}")
	private String packageToScan;
	@Value("${mrt.template.updater.loaded.test:}")
	private String testLoadTemplateUpdater;
	@Value("${mrt.template.dao.loaded.test:}")
	private String testLoadDao;
	@Value("${mrt.zipfile.overwritten:false}")
	private boolean zipFileOverwritten;
	@Value("${mrt.failedfile.overwritten:false}")
	private boolean failedFileOverwritten;
	@Value("${mrt.passedfile.overwritten:false}")
	private boolean passedFileOverwritten;
	@Value("${mrt.map.srid.gda94}")
	private int sridGda94;
	@Value("${mrt.map.mga.54.ne.file.name}")
	private String mga54NeFileName;
	@Value("${mrt.map.mga.55.ne.file.name}")
	private String mga55NeFileName;
	@Value("${mrt.map.mga.54.lat.file.name}")
	private String mga54LatFileName;
	@Value("${mrt.map.mga.55.lat.file.name}")
	private String mga55LatFileName;
	@Value("${mrt.map.srid.agd84}")
	private int sridAgd84;
	@Value("${mrt.map.agd84.54.ne.file.name}")
	private String agd8454NeFileName;
	@Value("${mrt.map.agd84.55.ne.file.name}")
	private String agd8455NeFileName;
	@Value("${mrt.map.ds.jdbc.driver.class}")
	private String sdoDsDriverClassName;
	@Value("${mrt.map.ds.url}")
	private String sdoDsUrl;
	@Value("${mrt.map.ds.username}")
	private String sdoDsUserName;
	@Value("${mrt.map.ds.pwd}")
	private String sdoDsPwd;

	public String getDirectoryToScan() {
		return directoryToScan;
	}

	public String getDsDriverClassName() {
		return driverClassName;
	}

	public String getDsUrl() {
		return dsUrl;
	}

	public String getDsUserName() {
		return dsUserName;
	}

	public String getDsPwd() {
		return dsPwd;
	}

	public String getZipFileExtractDestination() {
		return zipFileExtractDestination;
	}

	public String getTemplateApiJarFile() {
		return templateApiJarFile;
	}

	public String getCustomTemplateJarFileDir() {
		return customTemplateJarFileDir;
	}

	public String getTestLoaded() {
		return testLoaded;
	}

	public String getLogFileOutputDirectory() {
		return logFileOutputDirectory;
	}

	public String getPassedFileDirectory() {
		return passedFileDirectory;
	}

	public String getFailedFileDirectory() {
		return failedFileDirectory;
	}

	public String getMailSmtpAuth() {
		return mailSmtpAuth;
	}

	public String getMailSmtpStartTlsEnable() {
		return mailSmtpStartTlsEnable;
	}

	public String getMailSmtpHost() {
		return mailSmtpHost;
	}

	public String getMailSmtpPort() {
		return mailSmtpPort;
	}

	public String getToEmail() {
		return toEmail;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public String getEmailUser() {
		return emailUser;
	}

	public String getEmailUserPwd() {
		return emailUserPwd;
	}

	public String getPackageToScan() {
		return packageToScan;
	}

	public String getTestLoadTemplateUpdater() {
		return testLoadTemplateUpdater;
	}
	
	public String getTestLoadDao() {
		return testLoadDao;
	}

	public boolean getZipFileOverwritten() {
		return zipFileOverwritten;
	}

	public boolean getPassedFileOverwritten() {
		return passedFileOverwritten;
	}

	public boolean getFailedFileOverwritten() {
		return failedFileOverwritten;
	}

	public int getSridGda94() {
		return sridGda94;
	}

	public String getMga54NeFileName() {
		return mga54NeFileName;
	}

	public String getMga55NeFileName() {
		return mga55NeFileName;
	}

	public String getMga54LatFileName() {
		return mga54LatFileName;
	}
	
	public String getMga55LatFileName() {
		return mga55LatFileName;
	}

	public int getSridAgd84() {
		return sridAgd84;
	}

	public String getAgd8454NeFileName() {
		return agd8454NeFileName;
	}

	public String getAgd8455NeFileName() {
		return agd8455NeFileName;
	}

	public String getSdoDsDriverClassName() {
		return sdoDsDriverClassName;
	}

	public String getSdoDsUrl() {
		return sdoDsUrl;
	}

	public String getSdoDsUserName() {
		return sdoDsUserName;
	}

	public String getSdoDsPwd() {
		return sdoDsPwd;
	}
}

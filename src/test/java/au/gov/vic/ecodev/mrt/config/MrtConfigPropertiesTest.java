package au.gov.vic.ecodev.mrt.config;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class MrtConfigPropertiesTest {

	@Autowired
	private MrtConfigProperties configProperties;
	
	@Test
	public void shouldAutowiredConfig() {
		assertThat(configProperties, is(notNullValue()));
	}
	
	@Test
	public void shouldReturnScanDirectory() {
		//Given
		//When
		String directoryToScan = configProperties.getDirectoryToScan();
		//Then
		assertThat(directoryToScan, is(equalTo("src/test/resources/zip")));
	}
	
	@Test
	public void shouldReturnLogFileOutputDirectory() {
		//Given
		//When
		String logFileOutputDirectory = configProperties.getLogFileOutputDirectory();
		//Then
		assertThat(logFileOutputDirectory, is(equalTo("src/test/resources/generated/log")));
	}
	
	@Test
	public void shouldReturnPassedFileDirectory() {
		//Given
		//When
		String passedFileDirectory = configProperties.getPassedFileDirectory();
		//Then
		assertThat(passedFileDirectory, is(equalTo("src/test/resources/passed")));
	}
	
	@Test
	public void shouldReturnFailedFileDirectory() {
		//Given
		//When
		String failedFileDirectory = configProperties.getFailedFileDirectory();
		//Then
		assertThat(failedFileDirectory, is(equalTo("src/test/resources/failed")));
	}
	
	@Test
	public void shouldReturnMailSmtpAuth() {
		//Given
		//When
		String mailSmtpAuth = configProperties.getMailSmtpAuth();
		//Then
		assertThat(mailSmtpAuth, is(equalTo("true")));
	}
	
	@Test
	public void shouldReturnMailSmtpStartTlsEnable() {
		//Given
		//When
		String mailSmtpStartedTlsEnable = configProperties.getMailSmtpStartTlsEnable();
		//Then
		assertThat(mailSmtpStartedTlsEnable, is(equalTo("true")));
	}
	
	@Test
	public void shouldReturnMailSmtpHost() {
		//Given
		//When
		String mailSmtpHost = configProperties.getMailSmtpHost();
		//Then
		assertThat(mailSmtpHost, is(equalTo("127.0.0.1")));
	}
	
	@Test
	public void shouldReturnMailSmtpPort() {
		//Given
		//When
		String mailSmtpPort = configProperties.getMailSmtpPort();
		//Then
		assertThat(mailSmtpPort, is(equalTo("25")));
	}
	
	@Test
	public void shouldReturnMailToEmail() {
		//Given
		//When
		String toEmail = configProperties.getToEmail();
		//Then
		assertThat(toEmail, is(equalTo("jiang.liu@ecodev.vic.gov.au")));
	}
	
	@Test
	public void shouldReturnEmailSubject() {
		//Given
		//When
		String emailSubject = configProperties.getEmailSubject();
		//Then
		assertThat(emailSubject, is(equalTo("Template File")));
	}
	
	@Test
	public void shouldReturnEmailUser() {
		//Given
		//When
		String emailUser = configProperties.getEmailUser();
		//Then
		assertThat(emailUser, is(equalTo("")));
	}
	
	@Test
	public void shouldReturnEmailUserPwd() {
		//Given
		//When
		String emailUserPwd = configProperties.getEmailUserPwd();
		//Then
		assertThat(emailUserPwd, is(equalTo("")));
	}
}

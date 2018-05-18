package au.gov.vic.ecodev.mrt.mail;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.Test;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.config.MrtConfigProperties;

public class MailerTest {

	private MrtConfigProperties mockMrtConfigProperties;
	private Mailer mailer;
	
	@Test
	public void shouldValidEmailAddress() {
		// Given
		String[] emails = { "\"Fred Bloggs\"@example.com", "user@.invalid.com", "Chuck Norris <gmail@chucknorris.com>", "webmaster@müller.de", "matteo@78.47.122.114" };
		givenTestInstance();
		// When
		// Then
		assertThat(mailer.isValidEmailAddress(emails[0]), is(true));
		assertThat(mailer.isValidEmailAddress(emails[1]), is(false));
		assertThat(mailer.isValidEmailAddress(emails[2]), is(true));
		assertThat(mailer.isValidEmailAddress(emails[3]), is(true));
		assertThat(mailer.isValidEmailAddress(emails[4]), is(true));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenTheSessionIsNull() throws Exception {
		//Given
		givenTestInstance();
		//When
		mailer.send(null, null, null, null, null);
		fail("Program reached unexpected point!");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenToEmailIsNull() throws Exception {
		//Given
		givenTestInstance();
		mailer.createSession();
		//When
		mailer.send(null, null, null, null, null);
		fail("Program reached unexpected point!");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenToEmailIsInvalid() throws Exception {
		//Given
		givenTestInstance();
		mailer.createSession();
		//When
		mailer.send("abc", "", "", "", "");
		fail("Program reached unexpected point!");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenPropertiesIsNull() {
		// Given
		MrtConfigProperties mockMrtConfigProperties = null;
		// When
		new Mailer(mockMrtConfigProperties);
		fail("Program reached unexpected point!");
	}

	@Test
	public void shouldReturnInstance() {
		// Given
		givenTestInstance();
		// When
		// Then
		assertThat(mailer, is(notNullValue()));
	}

	@Test
	public void shouldSendEmail() throws Exception {
		// Given
		String cmd = "C:\\Data\\app\\smtp4dev\\Smtp4dev.exe";
		File executeFile = new File(cmd);
		if (executeFile.exists()) {
			Process process = Runtime.getRuntime().exec(cmd);
			givenTestInstance();
			mailer.createSession();
			// When
			mailer.send("jiang.liu@ecodev.vic.gov.au", "Test", "Test", "", "");
			// Then
			assertThat(true, is(true));
			process.destroy();
		} 
	}
	
	private void givenTestInstance() {
		mockMrtConfigProperties = Mockito.mock(MrtConfigProperties.class);
		when(mockMrtConfigProperties.getMailSmtpAuth()).thenReturn("true");
		when(mockMrtConfigProperties.getMailSmtpStartTlsEnable()).thenReturn("true");
		when(mockMrtConfigProperties.getMailSmtpHost()).thenReturn("127.0.0.1");
		when(mockMrtConfigProperties.getMailSmtpPort()).thenReturn("25");
		mailer = new Mailer(mockMrtConfigProperties);
	}
}

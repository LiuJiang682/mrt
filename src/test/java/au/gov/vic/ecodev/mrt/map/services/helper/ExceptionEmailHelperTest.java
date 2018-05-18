package au.gov.vic.ecodev.mrt.map.services.helper;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import au.gov.vic.ecodev.mrt.config.MrtConfigProperties;
import au.gov.vic.ecodev.mrt.mail.Mailer;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ExceptionEmailer.class)
public class ExceptionEmailHelperTest {

	@Test
	public void shouldSendEmailWhenSendEmailMethodCalled() throws Exception {
		//Given
		MrtConfigProperties mockMrtConfigProperties = Mockito.mock(MrtConfigProperties.class);
		when(mockMrtConfigProperties.getMailSmtpAuth()).thenReturn("true");
		when(mockMrtConfigProperties.getMailSmtpStartTlsEnable()).thenReturn("true");
		when(mockMrtConfigProperties.getMailSmtpHost()).thenReturn("127.0.0.1");
		when(mockMrtConfigProperties.getMailSmtpPort()).thenReturn("25");
		when(mockMrtConfigProperties.getToEmail()).thenReturn("jiang.liu@ecodev.vic.gov.au");
		when(mockMrtConfigProperties.getEmailUser()).thenReturn("");
		when(mockMrtConfigProperties.getEmailUserPwd()).thenReturn("");
		ExceptionEmailer testInstance = new ExceptionEmailer(mockMrtConfigProperties);
		RuntimeException e = new RuntimeException("Test");
		Mailer mockMailer = Mockito.mock(Mailer.class);
		PowerMockito.whenNew(Mailer.class).withArguments(mockMrtConfigProperties)
			.thenReturn(mockMailer);
		//When
		testInstance.sendEmail(e);
		//Then
		verify(mockMailer).send(Matchers.anyString(), 
				Matchers.anyString(), Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString());
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		MrtConfigProperties mrtConfigProperties = new MrtConfigProperties();
		//When
		ExceptionEmailer testInstance = new ExceptionEmailer(mrtConfigProperties);
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExcetpionWhenPropertiesIsNull() {
		//Given
		MrtConfigProperties mrtConfigProperties = null;
		//When
		new ExceptionEmailer(mrtConfigProperties);
		fail("Program reached unexpected point!");
	}
}

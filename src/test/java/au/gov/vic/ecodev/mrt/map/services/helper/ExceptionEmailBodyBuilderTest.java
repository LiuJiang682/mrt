package au.gov.vic.ecodev.mrt.map.services.helper;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

public class ExceptionEmailBodyBuilderTest {

	private static final String EMAIL_PREFIX = "Hi\n\nThere are failures in the system due to: Test\nStackTrace:\njava.lang.RuntimeException: Test\r\n	at au.gov.vic.ecodev.mrt.map.services.helper.ExceptionEmailBodyBuilderTest.shouldReturnEmailBody(ExceptionEmailBodyBuilderTest.java:17)";

	@Test
	public void shouldReturnEmailBody() {
		// Given
		Exception exception = new RuntimeException("Test");
		ExceptionEmailBodyBuilder testInstance = new ExceptionEmailBodyBuilder(exception);
		// When
		String emailBody = testInstance.build();
		// Then
		assertThat(emailBody, is(notNullValue()));
		assertThat(emailBody.startsWith(EMAIL_PREFIX), is(true));
	}

	@Test
	public void shouldReturnInstance() {
		// Given
		Exception exception = new RuntimeException();
		// When
		ExceptionEmailBodyBuilder testInstance = new ExceptionEmailBodyBuilder(exception);
		// Then
		assertThat(testInstance, is(notNullValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenExceptionIsNull() {
		// Given
		Exception exception = null;
		// When
		new ExceptionEmailBodyBuilder(exception);
		fail("Program reached unexpected point!");
	}
}

package au.gov.vic.ecodev.mrt.template.context.properties;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

public class DefaultStringTemplatePropertiesTest {

	@Test
	public void shouldReturnInstance() {
		//Given
		String value = "abc";
		//When
		DefaultStringTemplateProperties testInstance = new DefaultStringTemplateProperties(value);
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenValueIsNull() {
		//Given
		String value = null;
		//When
		new DefaultStringTemplateProperties(value);
		fail("Program reached unexpected point!");
	}
}

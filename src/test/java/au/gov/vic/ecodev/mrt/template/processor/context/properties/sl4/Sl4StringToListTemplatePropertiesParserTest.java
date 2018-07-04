package au.gov.vic.ecodev.mrt.template.processor.context.properties.sl4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.template.context.properties.DefaultStringTemplateProperties;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.properties.TemplateProperties;
import au.gov.vic.ecodev.template.processor.context.properties.StringToListTemplatePropertiesParser;

public class Sl4StringToListTemplatePropertiesParserTest {

	private StringToListTemplatePropertiesParser testInstance;
	private TemplateProperties property;
	private String delim;
	
	@Test
	public void shouldParserStringToList() throws TemplateProcessorException {
		//Given
		givenTestInstance();
		//When
		List<String> list = testInstance.parse();
		//Then
		assertThat(CollectionUtils.isEmpty(list), is(false));
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionWhenPropertyIsNotString() throws TemplateProcessorException {
		//Given
		property = Mockito.mock(TemplateProperties.class);
		String delim = ",";
		testInstance = new StringToListTemplatePropertiesParser(property, delim);
		//When
		testInstance.parse();
		fail("Program reached unexpected point!");
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		givenTestInstance();
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenPropertyIsNull() {
		//Given
		TemplateProperties property = null;
		String delim = null;
		//When 
		new StringToListTemplatePropertiesParser(property, delim);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenDelimIsNull() {
		//Given
		TemplateProperties property = Mockito.mock(TemplateProperties.class);
		String delim = null;
		//When 
		new StringToListTemplatePropertiesParser(property, delim);
		fail("Program reached unexpected point!");
	}
	
	private void givenTestInstance() {
		property = new DefaultStringTemplateProperties("H0002,H0005,H0202,H0203,H0400,H0401,H0402,H0501,H0502,H0503,H0530,H0531,H0532,H0533,H1000,D");
		delim = ",";
	
		testInstance = new StringToListTemplatePropertiesParser(property, delim);
	}
}

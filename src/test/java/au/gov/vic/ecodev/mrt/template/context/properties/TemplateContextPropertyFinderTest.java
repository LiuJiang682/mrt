package au.gov.vic.ecodev.mrt.template.context.properties;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.services.PersistentServices;
import au.gov.vic.ecodev.mrt.template.properties.TemplateProperties;
import au.gov.vic.ecodev.template.processor.context.properties.StringTemplateProperties;

public class TemplateContextPropertyFinderTest {

	private TemplateContextPropertyFinder testInstance;
	private PersistentServices mockPersistentServices;
	private String templatePropertyName;
	
	@Test
	public void shouldReturnListOfFieldsWhenCorrectTemplatePropertyNameProvided() throws TemplateProcessorException {
		//Given
		givenTestInstance();
		when(mockPersistentServices.getTemplateContextProperty(Matchers.anyString(), 
				Matchers.anyString())).thenReturn("abc");
		//When
		TemplateProperties templateProperties = testInstance.find();
		//Then
		assertThat(templateProperties, is(notNullValue()));
		assertThat(templateProperties, is(instanceOf(StringTemplateProperties.class)));
		assertThat(((StringTemplateProperties)templateProperties).getValue(), is(equalTo("abc")));
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionWhenInCorrectTemplatePropertyNameProvided() throws TemplateProcessorException {
		//Given
		mockPersistentServices = Mockito.mock(PersistentServices.class);
		templatePropertyName = "MANDATORY.VALIDATE.FIELDS";
		testInstance = new TemplateContextPropertyFinder(mockPersistentServices,
				templatePropertyName);
		//When
		testInstance.find();
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
	public void shouldRaiseExceptionWhenTemplateContextPropertiesDaoIsNull() {
		//Given
		PersistentServices persistentServices = null;
		String templatePropertyName = null;
		//When
		new TemplateContextPropertyFinder(persistentServices, templatePropertyName);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenTemplatePropertyNameIsNull() {
		//Given
		PersistentServices persistentServices =  Mockito.mock(PersistentServices.class);;
		String templatePropertyName = null;
		//When
		new TemplateContextPropertyFinder(persistentServices, templatePropertyName);
		fail("Program reached unexpected point!");
	}
	
	private void givenTestInstance() {
		mockPersistentServices = Mockito.mock(PersistentServices.class);
		templatePropertyName = "sl4:MANDATORY.VALIDATE.FIELDS";
		testInstance = new TemplateContextPropertyFinder(mockPersistentServices,
				templatePropertyName);
	}
}

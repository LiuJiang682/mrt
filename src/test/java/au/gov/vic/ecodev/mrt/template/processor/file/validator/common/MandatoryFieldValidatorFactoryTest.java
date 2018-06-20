package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;

import au.gov.vic.ecodev.mrt.api.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;

public class MandatoryFieldValidatorFactoryTest {

	
	private MandatoryFieldValidatorFactory testInstance;
	private TemplateProcessorContext mockTemplateProcessorContext;
	private List<String> mandatoryFields;
	
	@Test
	public void shouldReturnH0002ValidatorWithCorrectInput() throws Exception {
		//Given
		givenTestInstance();
		when(mockTemplateProcessorContext.getTemplateContextProperty(Matchers.anyString()))
			.thenReturn(TestFixture.getH0002ValidatorClassName());
		String line = "H0002\tdef";
		Validator mockValidator = Mockito.mock(Validator.class);
		//When
		Validator validator = testInstance.getValidator(mockValidator, line, "DS4:");
		//Then
		assertThat(validator, is(not(mockValidator)));
		assertThat(validator, is(instanceOf(H0002Validator.class)));
	}
	
	@Test
	public void shouldCallInitMethodWithNullMandatoryFields() throws Exception {
		//Given
		mockTemplateProcessorContext = Mockito.mock(TemplateProcessorContext.class);
		mandatoryFields = null;
		testInstance = 
				new MandatoryFieldValidatorFactory(mockTemplateProcessorContext, mandatoryFields);
		String line = "abc";
		Validator mockValidator = Mockito.mock(Validator.class);
		//When
		testInstance.getValidator(mockValidator, line, "DS4:");
		//Then
		ArgumentCaptor<String[]> stringArrayCaptor = ArgumentCaptor.forClass(String[].class);
		verify(mockValidator).init(stringArrayCaptor.capture());
		String[] capturedStrings = stringArrayCaptor.getValue();
		assertThat(capturedStrings.length, is(equalTo(1)));
		assertThat(capturedStrings[Numeral.ZERO], is(equalTo("abc")));
	}
	
	@Test
	public void shouldCallInitMethodWith2Strings() throws Exception {
		//Given
		givenTestInstance();
		String line = "abc\tdef";
		Validator mockValidator = Mockito.mock(Validator.class);
		//When
		testInstance.getValidator(mockValidator, line, "DS4:");
		//Then
		ArgumentCaptor<String[]> stringArrayCaptor = ArgumentCaptor.forClass(String[].class);
		verify(mockValidator).init(stringArrayCaptor.capture());
		String[] capturedStrings = stringArrayCaptor.getValue();
		assertThat(capturedStrings.length, is(equalTo(2)));
		assertThat(capturedStrings[Numeral.ZERO], is(equalTo("abc")));
		assertThat(capturedStrings[Numeral.ONE], is(equalTo("def")));
	}
	
	@Test
	public void shouldCallInitMethod() throws Exception {
		//Given
		givenTestInstance();
		String line = "abc";
		Validator mockValidator = Mockito.mock(Validator.class);
		//When
		testInstance.getValidator(mockValidator, line, "DS4:");
		//Then
		ArgumentCaptor<String[]> stringArrayCaptor = ArgumentCaptor.forClass(String[].class);
		verify(mockValidator).init(stringArrayCaptor.capture());
		String[] capturedStrings = stringArrayCaptor.getValue();
		assertThat(capturedStrings.length, is(equalTo(1)));
		assertThat(capturedStrings[Numeral.ZERO], is(equalTo("abc")));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		givenTestInstance();
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
		TemplateProcessorContext retrievedContext = Whitebox.getInternalState(testInstance, "context");
		List<String> retrievedFields = Whitebox.getInternalState(testInstance, "mandatoryFields");
		assertThat(retrievedContext, is(equalTo(mockTemplateProcessorContext)));
		assertThat(retrievedFields, is(equalTo(mandatoryFields)));
	}

	private void givenTestInstance() {
		mockTemplateProcessorContext = Mockito.mock(TemplateProcessorContext.class);
		mandatoryFields = new ArrayList<>();
		mandatoryFields.add("H0002");
		
		testInstance = 
				new MandatoryFieldValidatorFactory(mockTemplateProcessorContext, mandatoryFields);
	}
}

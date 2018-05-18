package au.gov.vic.ecodev.mrt.template.processor.file.validator.sg4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0002Validator;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;

public class Sg4ValidatorFactoryTest {

	@Test
	public void shouldReturnAH0002Validator() throws Exception {
		//Given
		String line = "H0002\tVersion\t4";
		TemplateProcessorContext mockContext = Mockito.mock(TemplateProcessorContext.class);
		when(mockContext.getTemplateContextProperty(Matchers.anyString()))
			.thenReturn(TestFixture.getH0002ValidatorClassName());
		//When
		Validator validator = new Sg4ValidatorFactory(mockContext, 
				TestFixture.getSg4MandatoryFieldsList())
				.getLineValidator(line);
		//Then
		assertThat(validator, is(instanceOf(H0002Validator.class)));
	}
	
	@Test
	public void shouldReturnDefaultValidator() throws Exception {
		// Given
		TemplateProcessorContext mockTemplateProcessorContext = 
				Mockito.mock(TemplateProcessorContext.class);
		List<String> mandatoryFields = TestFixture.getSg4MandatoryFieldsList();
		Sg4ValidatorFactory testInstance = new Sg4ValidatorFactory(mockTemplateProcessorContext, 
				mandatoryFields);
		// When
		Validator validator = testInstance.getLineValidator(null);
		// Then
		assertThat(validator, is(instanceOf(Sg4DefaultValidator.class)));
	}

	@Test
	public void shouldReturnInstance() {
		// Given
		TemplateProcessorContext mockTemplateProcessorContext = 
				Mockito.mock(TemplateProcessorContext.class);
		List<String> mandatoryFields = TestFixture.getSg4MandatoryFieldsList();
		// When
		Sg4ValidatorFactory testInstance = new Sg4ValidatorFactory(mockTemplateProcessorContext, 
				mandatoryFields);
		// Then
		assertThat(testInstance, is(notNullValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenContextIsNull() {
		// Given
		TemplateProcessorContext templateProcessorContext = null;
		List<String> mandatoryFields = null;
		// When
		new Sg4ValidatorFactory(templateProcessorContext, mandatoryFields);
		fail("Program reached unexpected point!");
	}
}

package au.gov.vic.ecodev.mrt.template.processor.file.validator.dl4;

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

public class Dl4ValidatorFactoryTest {

	private TemplateProcessorContext mockContext;
	private Dl4ValidatorFactory testInstance;
	
	@Test
	public void shouldReturnAH0002Validator() throws Exception {
		//Given
		givenTestInstance();
		when(mockContext.getTemplateContextProperty(Matchers.anyString()))
			.thenReturn(TestFixture.getH0002ValidatorClassName());
		String line = "H0002\tVersion\t4";
		//When
		Validator validator = testInstance.getLineValidator(line);
		//Then
		assertThat(validator, is(instanceOf(H0002Validator.class)));
	}
	
	@Test
	public void shouldReturnDs4DefaultValidator() throws Exception {
		// Given
		givenTestInstance();
		String line = "abc";
		// When
		Validator validator = testInstance.getLineValidator(line);
		// Then
		assertThat(validator, is(instanceOf(Dl4DefaultValidator.class)));
	}
	
	@Test
	public void shouldReturnInstance() {
		// Given
		givenTestInstance();
		// When
		// Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenContextIsNull() {
		// Given
		TemplateProcessorContext context = null;
		List<String> mandatoryFields = TestFixture.getMandatoryFieldDs4();
		// When
		new Dl4ValidatorFactory(context, mandatoryFields);
		fail("Program reached unexpected point!");
	}
	
	private void givenTestInstance() {
		mockContext = Mockito.mock(TemplateProcessorContext.class);
		List<String> mandatoryFields = TestFixture.getMandatoryFieldDs4();
	
		testInstance = new Dl4ValidatorFactory(mockContext, mandatoryFields);
	}
}

package au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0002Validator;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4.Sl4DefaultValidator;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4.Sl4ValidatorFactory;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;

public class Sl4ValidatorFactoryTest {

	private TemplateProcessorContext mockContext;
	
	@Test
	public void shouldReturnAH0002Validator() throws Exception {
		//Given
		String line = "H0002\tVersion\t4";
		mockContext = Mockito.mock(TemplateProcessorContext.class);
		when(mockContext.getTemplateContextProperty(Matchers.anyString()))
			.thenReturn(TestFixture.getH0002ValidatorClassName());
		//When
		Validator validator = new Sl4ValidatorFactory(mockContext, TestFixture.getSl4RequiredFields())
				.getLineValidator(line);
		//Then
		assertThat(validator, is(instanceOf(H0002Validator.class)));
	}
	
	@Test
	public void shouldReturnABlankValidator() throws Exception {
		//Given
		String line = null;
		mockContext = Mockito.mock(TemplateProcessorContext.class);
		//When
		Validator validator = new Sl4ValidatorFactory(mockContext, TestFixture.getSl4RequiredFields())
				.getLineValidator(line);
		//Then
		assertThat(validator, is(instanceOf(Sl4DefaultValidator.class)));
	}
}

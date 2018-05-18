package au.gov.vic.ecodev.mrt.template.processor;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import au.gov.vic.ecodev.mrt.template.processor.sl4.Sl4TemplateProcessor;

@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor("au.gov.vic.ecodev.mrt.template.TemplateProcessorFactory")
public class TemplateProcessorFactoryTest {

	@Test
	public void shouldReturnTemplateProcessorWhenSL4AndDirectoryProvided() throws Exception {
		//Given
		String fullTemplateClassName = "au.gov.vic.ecodev.mrt.template.processor.sl4.Sl4TemplateProcessor";
		//When
		TemplateProcessor templateProcessor = 
				TemplateProcessorFactory.getProcessor(fullTemplateClassName);
		//Then
		assertThat(templateProcessor, is(instanceOf(Sl4TemplateProcessor.class)));
	}
}

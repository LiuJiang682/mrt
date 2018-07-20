package au.gov.vic.ecodev.mrt.template.files;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import au.gov.vic.ecodev.mrt.template.file.TemplateFileSelector;

public class TemplateFileSelectorFactoryTest {

	@Test
	public void shouldReturnTemplateProcessorWhenSL4AndDirectoryProvided() throws Exception {
		//Given
		String templateFileSelectorClassName = "au.gov.vic.ecodev.mrt.template.files.H0202HeaderTemplateFileSelector";
		//When
		TemplateFileSelector templateFileSelector = 
				TemplateFileSelectorFactory.getTemplateFileSelector(templateFileSelectorClassName);
		//Then
		assertThat(templateFileSelector, is(instanceOf(H0202HeaderTemplateFileSelector.class)));
	}
}

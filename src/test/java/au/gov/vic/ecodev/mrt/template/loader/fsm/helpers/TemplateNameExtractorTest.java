package au.gov.vic.ecodev.mrt.template.loader.fsm.helpers;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TemplateNameExtractorTest {
	
	private TemplateNameExtractor testInstance;

	@Test
	public void shouldeExtractTemplateFromZipFiles() {
		//Given
		List<String> fileNames = Arrays.asList("mrt_eco.zip");
		givenTestInstance();
		//When
		List<String> templateName = testInstance.extractTemplateName(fileNames);
		//Then
		assertThat(templateName, is(notNullValue()));
		assertThat(templateName.size(), is(equalTo(1)));
		assertThat(templateName.get(0), is(equalTo("mrt")));
	}
	
	@Test
	public void shouldeExtractTemplateFromMultiUnderScoreZipFiles() {
		//Given
		List<String> fileNames = Arrays.asList("mrt_eco_final.zip");
		givenTestInstance();
		//When
		List<String> templateName = testInstance.extractTemplateName(fileNames);
		//Then
		assertThat(templateName, is(notNullValue()));
		assertThat(templateName.size(), is(equalTo(1)));
		assertThat(templateName.get(0), is(equalTo("mrt")));
	}
	
	private void givenTestInstance() {
		testInstance = new TemplateNameExtractor();
	}
}

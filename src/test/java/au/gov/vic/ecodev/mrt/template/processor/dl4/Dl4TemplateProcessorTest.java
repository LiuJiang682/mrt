package au.gov.vic.ecodev.mrt.template.processor.dl4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.context.properties.StringListTemplateProperties;
import au.gov.vic.ecodev.mrt.template.criteria.TemplateCriteria;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class Dl4TemplateProcessorTest {

	@Test
	public void shouldProcessFiles() throws Exception {
		//Given
		Dl4TemplateProcessor testInstance = new Dl4TemplateProcessor();
		List<File> files = givenTestFileList();
		testInstance.setFileList(files);
		TemplateProcessorContext mockContext = Mockito.mock(TemplateProcessorContext.class);
		TestFixture.givenDl4TemplateProperties(mockContext);
		testInstance.setTemplateProcessorContent(mockContext);
		when(mockContext.saveDataBean(Matchers.any(Template.class))).thenReturn(true);
		StringListTemplateProperties stringListProperties = new StringListTemplateProperties(null);
		when(mockContext.search(Matchers.any(TemplateCriteria.class))).thenReturn(stringListProperties);
		//When
		testInstance.processFile();
		//Then
		ArgumentCaptor<Template> templateCaptor = ArgumentCaptor.forClass(Template.class);
		verify(mockContext).saveDataBean(templateCaptor.capture());
		assertThat(templateCaptor.getValue(), is(notNullValue()));
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionWhenFileListIsNull() throws TemplateProcessorException {
		//Given
		Dl4TemplateProcessor testInstance = new Dl4TemplateProcessor();
		List<File> files = null;
		testInstance.setFileList(files);
		//When
		testInstance.processFile();
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionWhenContextIsNull() throws Exception {
		Dl4TemplateProcessor testInstance = new Dl4TemplateProcessor();
		List<File> files = givenTestFileList();
		testInstance.setFileList(files);
		TemplateProcessorContext mockContext = null;
		testInstance.setTemplateProcessorContent(mockContext);
		//When
		testInstance.processFile();
		fail("Program reached unexpected point!");
	}
	
	private List<File> givenTestFileList() {
		List<File> files = new ArrayList<>();
		File dl4File = new File("src/test/resources/template/MtStavely_201703_03_Dl4.txt");
		files.add(dl4File);
		return files;
	}
}

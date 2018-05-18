package au.gov.vic.ecodev.mrt.template.processor.dg4;

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
import au.gov.vic.ecodev.mrt.template.processor.dg4.Dg4TemplateProcessor;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class Dg4TemplateProcessorTest {

	@Test
	public void shouldProcessFiles() throws Exception {
		//Given
		Dg4TemplateProcessor testInstance = new Dg4TemplateProcessor();
		List<File> files = givenTestFileList();
		testInstance.setFileList(files);
		TemplateProcessorContext mockContext = Mockito.mock(TemplateProcessorContext.class);
		TestFixture.givenDg4TemplateProperties(mockContext);
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
		Dg4TemplateProcessor testInstance = new Dg4TemplateProcessor();
		List<File> files = null;
		testInstance.setFileList(files);
		//When
		testInstance.processFile();
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionWhenContextIsNull() throws Exception {
		Dg4TemplateProcessor testInstance = new Dg4TemplateProcessor();
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
		File dg4File = new File("src/test/resources/template/EL5478_201702_01_Dg4.txt");
		files.add(dg4File);
		return files;
	}
}

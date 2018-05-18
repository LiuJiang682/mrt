package au.gov.vic.ecodev.mrt.template.processor.sg4;

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
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class Sg4TemplateProcessorTest {

	@Test
	public void shouldProcessFiles() throws Exception {
		//Given
		Sg4TemplateProcessor testInstance = new Sg4TemplateProcessor();
		List<File> files = givenTestFileList();
		testInstance.setFileList(files);
		TemplateProcessorContext mockContext = Mockito.mock(TemplateProcessorContext.class);
		testInstance.setTemplateProcessorContent(mockContext);
		TestFixture.givenSg4TemplateProperties(mockContext);
		when(mockContext.saveDataBean(Matchers.any(Template.class))).thenReturn(true);
		//When
		testInstance.processFile();
		//Then
		ArgumentCaptor<Template> templateCaptor = ArgumentCaptor.forClass(Template.class);
		verify(mockContext).saveDataBean(templateCaptor.capture());
		assertThat(templateCaptor.getValue(), is(notNullValue()));
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionWhenFileIsEmpty() throws Exception {
		Sg4TemplateProcessor testInstance = new Sg4TemplateProcessor();
		List<File> files = new ArrayList<>();
		testInstance.setFileList(files);
		//When
		testInstance.processFile();
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionWhenContextIsNull() throws Exception {
		Sg4TemplateProcessor testInstance = new Sg4TemplateProcessor();
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
		File file = new File("src/test/resources/template/EL5478_201702_01_Sg4.txt");
		files.add(file);
		return files;
	}
}

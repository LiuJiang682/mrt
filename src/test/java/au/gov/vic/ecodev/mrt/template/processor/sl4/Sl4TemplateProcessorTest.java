package au.gov.vic.ecodev.mrt.template.processor.sl4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.map.services.VictoriaMapServices;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.DefaultMessage;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.file.parser.sl4.Sl4TemplateFileParser;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.utils.file.finder.DirectoryTreeReverseTraversalZipFileFinder;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Sl4TemplateFileParser.class)
public class Sl4TemplateProcessorTest {

	@Test
	public void shouldProcessFiles() throws Exception {
		//Given
		Sl4TemplateProcessor testInstance = new Sl4TemplateProcessor();
		List<File> files = givenTestFileList();
		testInstance.setFileList(files);
		TemplateProcessorContext mockContext = Mockito.mock(TemplateProcessorContext.class);
		testInstance.setTemplateProcessorContent(mockContext);
		TestFixture.givenSl4TemplateProperties(mockContext);
		givenMapServices(mockContext);
		givenMockZipFileFinder();
		when(mockContext.saveDataBean(Matchers.any(Template.class))).thenReturn(true);
		when(mockContext.getMessage()).thenReturn(new DefaultMessage());
		//When
		testInstance.processFile();
		//Then
		ArgumentCaptor<Template> templateCaptor = ArgumentCaptor.forClass(Template.class);
		verify(mockContext).saveDataBean(templateCaptor.capture());
		assertThat(templateCaptor.getValue(), is(notNullValue()));
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionWhenFileIsEmpty() throws Exception {
		Sl4TemplateProcessor testInstance = new Sl4TemplateProcessor();
		List<File> files = new ArrayList<>();
		testInstance.setFileList(files);
		//When
		testInstance.processFile();
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionWhenContextIsNull() throws Exception {
		Sl4TemplateProcessor testInstance = new Sl4TemplateProcessor();
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
		File file = new File("src/test/resources/template/EL5478_201702_01_Collar.txt");
		files.add(file);
		return files;
	}
	
	private void givenMapServices(TemplateProcessorContext mockContext) {
		VictoriaMapServices mockVictoriaMapServices = Mockito.mock(VictoriaMapServices.class);
		when(mockVictoriaMapServices.isWithinMga54NorthEast(Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(true);
		when(mockContext.getMapServices()).thenReturn(mockVictoriaMapServices);
	}
	
	private void givenMockZipFileFinder() throws Exception {
		DirectoryTreeReverseTraversalZipFileFinder mockZipFileFinder = 
				Mockito.mock(DirectoryTreeReverseTraversalZipFileFinder.class);
		when(mockZipFileFinder.findZipFile()).thenReturn("abc.zip");
		PowerMockito.whenNew(DirectoryTreeReverseTraversalZipFileFinder.class)
			.withArguments(Matchers.anyString())
			.thenReturn(mockZipFileFinder);
	}
}

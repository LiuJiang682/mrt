package au.gov.vic.ecodev.mrt.template.processor.ds4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
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
import au.gov.vic.ecodev.mrt.template.context.properties.SqlCriteria;
import au.gov.vic.ecodev.mrt.template.context.properties.StringListTemplateProperties;
import au.gov.vic.ecodev.mrt.template.criteria.TemplateCriteria;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.context.properties.ds4.DownHoleHoleIdNotInBoreHoleSearcher;
import au.gov.vic.ecodev.mrt.template.processor.context.properties.ds4.DownHoleSurveyedDepthGtTotalDepthSearcher;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class Ds4TemplateProcessorTest {

	@Test
	public void shouldProcessFiles() throws Exception {
		//Given
		Ds4TemplateProcessor testInstance = new Ds4TemplateProcessor();
		List<File> files = givenTestFileList();
		testInstance.setFileList(files);
		TemplateProcessorContext mockContext = Mockito.mock(TemplateProcessorContext.class);
		testInstance.setTemplateProcessorContent(mockContext);
		TestFixture.givenDs4TemplateProperties(mockContext);
		when(mockContext.saveDataBean(Matchers.any(Template.class))).thenReturn(true);
		StringListTemplateProperties stringListProperties = new StringListTemplateProperties(null);
		when(mockContext.search(Matchers.any(TemplateCriteria.class))).thenReturn(stringListProperties);
		//When
		testInstance.processFile();
		//Then
		ArgumentCaptor<Template> templateCaptor = ArgumentCaptor.forClass(Template.class);
		verify(mockContext).saveDataBean(templateCaptor.capture());
		assertThat(templateCaptor.getValue(), is(notNullValue()));
		ArgumentCaptor<TemplateCriteria> criteriaCaptor = ArgumentCaptor.forClass(TemplateCriteria.class);
		verify(mockContext, times(2)).search(criteriaCaptor.capture());
		List<TemplateCriteria> capturedCriterias = criteriaCaptor.getAllValues();
		assertThat(capturedCriterias.get(0).getSearcherClassName(), is(equalTo(DownHoleHoleIdNotInBoreHoleSearcher.class.getName())));
		assertThat(capturedCriterias.get(0).getTemplateName(), is(equalTo("DS4")));
		assertThat(((SqlCriteria)capturedCriterias.get(0)).getKey(), is(equalTo(0l)));
		assertThat(capturedCriterias.get(1).getSearcherClassName(), is(equalTo(DownHoleSurveyedDepthGtTotalDepthSearcher.class.getName())));
		assertThat(capturedCriterias.get(1).getTemplateName(), is(equalTo("DS4")));
		assertThat(((SqlCriteria)capturedCriterias.get(1)).getKey(), is(equalTo(0l)));
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionWhenFileListIsNull() throws TemplateProcessorException {
		//Given
		Ds4TemplateProcessor testInstance = new Ds4TemplateProcessor();
		List<File> files = null;
		testInstance.setFileList(files);
		//When
		testInstance.processFile();
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionWhenContextIsNull() throws Exception {
		Ds4TemplateProcessor testInstance = new Ds4TemplateProcessor();
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
		File ds4File = new File("src/test/resources/template/MtStavely_201703_03_Survey.txt");
		files.add(ds4File);
		return files;
	}
}

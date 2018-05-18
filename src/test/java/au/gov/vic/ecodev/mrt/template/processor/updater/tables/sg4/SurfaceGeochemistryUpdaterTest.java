package au.gov.vic.ecodev.mrt.template.processor.updater.tables.sg4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;

import au.gov.vic.ecodev.mrt.dao.sg4.SurfaceGeochemistryDao;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.model.sg4.SurfaceGeochemistry;
import au.gov.vic.ecodev.mrt.template.fields.Sg4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class SurfaceGeochemistryUpdaterTest {

	private SurfaceGeochemistryDao mockSurfaceGeochemistryDao;
	private Template mockTemplate;
	private SurfaceGeochemistryUpdater testInstance;
	
	@Test
	public void shouldUpdateTheDatabase() throws TemplateProcessorException {
		//Given
		givenTestInstance();
		when(mockTemplate.get(eq("H0203"))).thenReturn(TestFixture.getNumList());
		List<String> headers = TestFixture.getSg4MandatoryColumnsList();
		when(mockTemplate.get(eq("H1000"))).thenReturn(headers);
		givenMockTemplateData();
		List<Integer> managdatoryIndexList = new ArrayList<>();
		testInstance.init(managdatoryIndexList);
		//When
		testInstance.update(TestFixture.getSg4DataList());
		//Then
		ArgumentCaptor<SurfaceGeochemistry> surfaceGeochemistryCaptor = ArgumentCaptor
				.forClass(SurfaceGeochemistry.class);
		verify(mockSurfaceGeochemistryDao).updateOrSave(surfaceGeochemistryCaptor.capture());
		List<SurfaceGeochemistry> entities = surfaceGeochemistryCaptor.getAllValues();
		assertThat(entities.size(), is(equalTo(1)));
		assertThat(managdatoryIndexList.size(), is(equalTo(4)));
		assertThat(managdatoryIndexList.contains(0), is(true));
		assertThat(managdatoryIndexList.contains(1), is(true));
		assertThat(managdatoryIndexList.contains(2), is(true));
		assertThat(managdatoryIndexList.contains(3), is(true));
	}
	
	@Test
	public void shouldInitializedAllRequiredFieldsWhenInitMethodCalled() throws TemplateProcessorException {
		//Given
		givenTestInstance();
		List<String> headers = TestFixture.getSg4MandatoryColumnsList();
		when(mockTemplate.get(eq("H1000"))).thenReturn(headers);
		givenMockTemplateData();
		List<Integer> managdatoryIndexList = new ArrayList<>();
		//When
		testInstance.init(managdatoryIndexList);
		//Then
		Integer sampleIdIndex = Whitebox.getInternalState(testInstance, "sampleIdIndex");
		assertThat(sampleIdIndex, is(equalTo(0)));
		Integer eastingIndex = Whitebox.getInternalState(testInstance, "eastingIndex");
		assertThat(eastingIndex, is(equalTo(1)));
		Integer northingIndex = Whitebox.getInternalState(testInstance, "northingIndex");
		assertThat(northingIndex, is(equalTo(2)));
		Integer sampleTypeIndex = Whitebox.getInternalState(testInstance, "sampleTypeIndex");
		assertThat(sampleTypeIndex, is(equalTo(3)));
	}
	
	@Test
	public void shouldInitializedAllRequiredFieldsWhenInitMethodCalledWithAmg() throws TemplateProcessorException {
		//Given
		givenTestInstance();
		List<String> headers = TestFixture.getSg4MandatoryAmgColumnsList();
		when(mockTemplate.get(eq("H1000"))).thenReturn(headers);
		when(mockTemplate.get(Sg4ColumnHeaders.SAMPLE_ID.getCode()))
			.thenReturn(Arrays.asList(Sg4ColumnHeaders.SAMPLE_ID.getCode()));
		when(mockTemplate.get(Sg4ColumnHeaders.EASTING_AMG.getCode()))
			.thenReturn(Arrays.asList(Sg4ColumnHeaders.EASTING_AMG.getCode()));
		when(mockTemplate.get(Sg4ColumnHeaders.NORTHING_AMG.getCode()))
			.thenReturn(Arrays.asList(Sg4ColumnHeaders.NORTHING_AMG.getCode()));
		when(mockTemplate.get(Sg4ColumnHeaders.SAMPLE_TYPE.getCode()))
			.thenReturn(Arrays.asList(Sg4ColumnHeaders.SAMPLE_TYPE.getCode()));
		List<Integer> managdatoryIndexList = new ArrayList<>();
		//When
		testInstance.init(managdatoryIndexList);
		//Then
		Integer sampleIdIndex = Whitebox.getInternalState(testInstance, "sampleIdIndex");
		assertThat(sampleIdIndex, is(equalTo(0)));
		Integer eastingIndex = Whitebox.getInternalState(testInstance, "eastingIndex");
		assertThat(eastingIndex, is(equalTo(1)));
		Integer northingIndex = Whitebox.getInternalState(testInstance, "northingIndex");
		assertThat(northingIndex, is(equalTo(2)));
		Integer sampleTypeIndex = Whitebox.getInternalState(testInstance, "sampleTypeIndex");
		assertThat(sampleTypeIndex, is(equalTo(3)));
	}

	@Test
	public void shouldExtractNorthingMgaIndex() throws TemplateProcessorException {
		//Given
		givenTestInstance();
		List<String> headers = TestFixture.getSg4MandatoryColumnsList();
		//When
		int index = testInstance.extractNorthingIndex(headers);
		//Then
		assertThat(index, is(equalTo(2)));
	}
	
	@Test
	public void shouldExtractNorthingAmgIndex() throws TemplateProcessorException {
		//Given
		givenTestInstance();
		List<String> headers = TestFixture.getSg4MandatoryAmgColumnsList();
		//When
		int index = testInstance.extractNorthingIndex(headers);
		//Then
		assertThat(index, is(equalTo(2)));
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExcepiontWhenExtractNorthingIndexNoMgaOrAmgFound() throws TemplateProcessorException {
		//Given
		givenTestInstance();
		List<String> headers = TestFixture.getSg4MandatoryColumnsList();
		headers.set(2, "xxx");
		//When
		testInstance.extractNorthingIndex(headers);
		fail("Program reached unexpected point!");
	}
	
	@Test
	public void shouldExtractEastMgaIndex() throws TemplateProcessorException {
		//Given
		givenTestInstance();
		List<String> headers = TestFixture.getSg4MandatoryColumnsList();
		//When
		int index = testInstance.extractEastingIndex(headers);
		//Then
		assertThat(index, is(equalTo(1)));
	}
	
	@Test
	public void shouldExtractEastAmgIndex() throws TemplateProcessorException {
		//Given
		givenTestInstance();
		List<String> headers = TestFixture.getSg4MandatoryAmgColumnsList();
		//When
		int index = testInstance.extractEastingIndex(headers);
		//Then
		assertThat(index, is(equalTo(1)));
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExcepiontWhenExtractEastIndexNoMgaOrAmgFound() throws TemplateProcessorException {
		//Given
		givenTestInstance();
		List<String> headers = TestFixture.getSg4MandatoryColumnsList();
		headers.set(1, "xxx");
		//When
		testInstance.extractEastingIndex(headers);
		fail("Program reached unexpected point!");
	}
	
	private void givenTestInstance() {
		mockSurfaceGeochemistryDao = Mockito.mock(SurfaceGeochemistryDao.class);
		mockTemplate = Mockito.mock(Template.class);
		testInstance = new SurfaceGeochemistryUpdater(mockSurfaceGeochemistryDao,
				1l, mockTemplate);
	}
	
	private void givenMockTemplateData() {
		when(mockTemplate.get(Sg4ColumnHeaders.SAMPLE_ID.getCode()))
			.thenReturn(Arrays.asList(Sg4ColumnHeaders.SAMPLE_ID.getCode()));
		when(mockTemplate.get(Sg4ColumnHeaders.EASTING_MGA.getCode()))
			.thenReturn(Arrays.asList(Sg4ColumnHeaders.EASTING_MGA.getCode()));
		when(mockTemplate.get(Sg4ColumnHeaders.NORTHING_MGA.getCode()))
			.thenReturn(Arrays.asList(Sg4ColumnHeaders.NORTHING_MGA.getCode()));
		when(mockTemplate.get(Sg4ColumnHeaders.SAMPLE_TYPE.getCode()))
			.thenReturn(Arrays.asList(Sg4ColumnHeaders.SAMPLE_TYPE.getCode()));
	}
}

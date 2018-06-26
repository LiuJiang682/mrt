package au.gov.vic.ecodev.mrt.template.processor.updater.tables.dg4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
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

import au.gov.vic.ecodev.mrt.dao.dg4.GeoChemistryDao;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.model.dg4.GeoChemistry;
import au.gov.vic.ecodev.mrt.template.fields.Dg4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.fields.Dl4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class GeoChemistryUpdaterTest {

	@Test
	public void shouldUpdateTheDatabase() throws TemplateProcessorException {
		// Given
		GeoChemistryDao mockGeoChemistryDao = Mockito.mock(GeoChemistryDao.class);
		Template mockTemplate = Mockito.mock(Template.class);
		List<String> headers = TestFixture.getDg4ColumnHeaderList();
		when(mockTemplate.get(eq("H0203"))).thenReturn(TestFixture.getNumList());
		when(mockTemplate.get(eq("H1000"))).thenReturn(headers);
		when(mockTemplate.get(eq(Dg4ColumnHeaders.HOLE_ID.getCode())))
				.thenReturn(Arrays.asList(Dl4ColumnHeaders.HOLE_ID.getCode()));
		when(mockTemplate.get(eq(Dg4ColumnHeaders.SAMPLE_ID.getCode())))
				.thenReturn(Arrays.asList(Dg4ColumnHeaders.SAMPLE_ID.getCode()));
		when(mockTemplate.get(eq(Dg4ColumnHeaders.FROM.getCode())))
				.thenReturn(Arrays.asList(Dg4ColumnHeaders.FROM.getCode()));
		when(mockTemplate.get(eq(Dg4ColumnHeaders.TO.getCode())))
				.thenReturn(Arrays.asList(Dg4ColumnHeaders.TO.getCode()));
//		when(mockTemplate.get(eq(Dg4ColumnHeaders.DRILL_CODE.getCode())))
//				.thenReturn(Arrays.asList(Dg4ColumnHeaders.DRILL_CODE.getCode()));
		GeoChemistryUpdater testInstance = new GeoChemistryUpdater(mockGeoChemistryDao,
				1l, mockTemplate);
		List<Integer> managdatoryIndexList = new ArrayList<>();
		testInstance.init(managdatoryIndexList);
		// When
		testInstance.update(TestFixture.getDg4DataList());
		// Then
		ArgumentCaptor<GeoChemistry> geoChemistryCaptor = ArgumentCaptor.forClass(GeoChemistry.class);
		verify(mockGeoChemistryDao).updateOrSave(geoChemistryCaptor.capture());
		List<GeoChemistry> lithologies = geoChemistryCaptor.getAllValues();
		assertThat(lithologies.size(), is(equalTo(1)));
		assertMandatoryIndexList(managdatoryIndexList);
	}

	@Test
	public void shouldInitializedAllRequiredFieldsWhenInitMethodCalled() throws TemplateProcessorException {
		// Given
		GeoChemistryDao mockGeoChemistryDao = Mockito.mock(GeoChemistryDao.class);
		Template mockTemplate = Mockito.mock(Template.class);
		List<String> headers = TestFixture.getDg4ColumnHeaderList();
		when(mockTemplate.get(eq("H1000"))).thenReturn(headers);
		when(mockTemplate.get(eq(Dg4ColumnHeaders.HOLE_ID.getCode())))
				.thenReturn(Arrays.asList(Dl4ColumnHeaders.HOLE_ID.getCode()));
		when(mockTemplate.get(eq(Dg4ColumnHeaders.SAMPLE_ID.getCode())))
				.thenReturn(Arrays.asList(Dg4ColumnHeaders.SAMPLE_ID.getCode()));
		when(mockTemplate.get(eq(Dg4ColumnHeaders.FROM.getCode())))
				.thenReturn(Arrays.asList(Dg4ColumnHeaders.FROM.getCode()));
		when(mockTemplate.get(eq(Dg4ColumnHeaders.TO.getCode())))
				.thenReturn(Arrays.asList(Dg4ColumnHeaders.TO.getCode()));
//		when(mockTemplate.get(eq(Dg4ColumnHeaders.DRILL_CODE.getCode())))
//				.thenReturn(Arrays.asList(Dg4ColumnHeaders.DRILL_CODE.getCode()));
		GeoChemistryUpdater testInstance = new GeoChemistryUpdater(mockGeoChemistryDao, 1l, mockTemplate);
		List<Integer> managdatoryIndexList = new ArrayList<>();
		// When
		testInstance.init(managdatoryIndexList);
		// Then
		Integer holeIdIndex = Whitebox.getInternalState(testInstance, "holeIdIndex");
		assertThat(holeIdIndex, is(equalTo(0)));
		Integer sampleIdIndex = Whitebox.getInternalState(testInstance, "sampleIdIndex");
		assertThat(sampleIdIndex, is(equalTo(1)));
		Integer fromIndex = Whitebox.getInternalState(testInstance, "fromIndex");
		assertThat(fromIndex, is(equalTo(2)));
		Integer toIndex = Whitebox.getInternalState(testInstance, "toIndex");
		assertThat(toIndex, is(equalTo(3)));
//		Integer drillCodeIndex = Whitebox.getInternalState(testInstance, "drillCodeIndex");
//		assertThat(drillCodeIndex, is(equalTo(4)));
		assertMandatoryIndexList(managdatoryIndexList);
	}

	private void assertMandatoryIndexList(List<Integer> managdatoryIndexList) {
		assertThat(managdatoryIndexList.size(), is(equalTo(4)));
		assertThat(managdatoryIndexList.contains(0), is(true));
		assertThat(managdatoryIndexList.contains(1), is(true));
		assertThat(managdatoryIndexList.contains(2), is(true));
		assertThat(managdatoryIndexList.contains(3), is(true));
//		assertThat(managdatoryIndexList.contains(4), is(true));
	}
}

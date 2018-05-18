package au.gov.vic.ecodev.mrt.template.processor.updater.tables.dl4;

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

import au.gov.vic.ecodev.mrt.dao.dl4.LithologyDao;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.model.dl4.Lithology;
import au.gov.vic.ecodev.mrt.template.fields.Dl4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class LithologyUpdaterTest {

	@Test
	public void shouldUpdateTheDatabase() throws TemplateProcessorException {
		//Given
		LithologyDao mockLithologyDao = Mockito.mock(LithologyDao.class);
		Template mockTemplate = Mockito.mock(Template.class);
		List<String> headers = TestFixture.getDl4ColumnHeaderList();
		when(mockTemplate.get(eq("H1000"))).thenReturn(headers);
		when(mockTemplate.get(eq(Dl4ColumnHeaders.HOLE_ID.getCode())))
			.thenReturn(Arrays.asList(Dl4ColumnHeaders.HOLE_ID.getCode()));
		when(mockTemplate.get(eq(Dl4ColumnHeaders.DEPTH_FROM.getCode())))
			.thenReturn(Arrays.asList(Dl4ColumnHeaders.DEPTH_FROM.getCode()));
		List<Integer> mandatoryIndexList = new ArrayList<>();
		LithologyUpdater testInstance = new LithologyUpdater(mockLithologyDao, 1l, mockTemplate);
		testInstance.init(mandatoryIndexList);
		//When
		testInstance.update(TestFixture.getDList());
		//Then
		ArgumentCaptor<Lithology> lithologyCaptor = ArgumentCaptor.forClass(Lithology.class);
		verify(mockLithologyDao).updateOrSave(lithologyCaptor.capture());
		List<Lithology> lithologies = lithologyCaptor.getAllValues();
		assertThat(lithologies.size(), is(equalTo(1)));
		assessMandatoryIndexList(mandatoryIndexList);
	}
	
	@Test
	public void shouldInitializedAllRequiredFieldsWhenInitMethodCalled() throws TemplateProcessorException {
		// Given
		LithologyDao mockLithologyDao = Mockito.mock(LithologyDao.class);
		Template mockTemplate = Mockito.mock(Template.class);
		List<String> headers = TestFixture.getDl4ColumnHeaderList();
		when(mockTemplate.get(eq("H1000"))).thenReturn(headers);
		when(mockTemplate.get(eq(Dl4ColumnHeaders.HOLE_ID.getCode())))
			.thenReturn(Arrays.asList(Dl4ColumnHeaders.HOLE_ID.getCode()));
		when(mockTemplate.get(eq(Dl4ColumnHeaders.DEPTH_FROM.getCode())))
			.thenReturn(Arrays.asList(Dl4ColumnHeaders.DEPTH_FROM.getCode()));
		List<Integer> mandatoryIndexList = new ArrayList<>();
		LithologyUpdater testInstance = new LithologyUpdater(mockLithologyDao, 1l, mockTemplate);
		// When
		testInstance.init(mandatoryIndexList);
		// Then
		Integer holeIdIndex = Whitebox.getInternalState(testInstance, "holeIdIndex");
		assertThat(holeIdIndex, is(equalTo(0)));
		Integer depthFromIndex = Whitebox.getInternalState(testInstance, "depthFromIndex");
		assertThat(depthFromIndex, is(equalTo(1)));
		assessMandatoryIndexList(mandatoryIndexList);
	}

	private void assessMandatoryIndexList(List<Integer> mandatoryIndexList) {
		assertThat(mandatoryIndexList.size(), is(equalTo(2)));
		assertThat(mandatoryIndexList.get(0), is(equalTo(0)));
		assertThat(mandatoryIndexList.get(1), is(equalTo(1)));
	}
}

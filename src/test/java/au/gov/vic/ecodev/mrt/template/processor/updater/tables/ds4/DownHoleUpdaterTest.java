package au.gov.vic.ecodev.mrt.template.processor.updater.tables.ds4;

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

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.dao.ds4.DownHoleDao;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.model.ds4.DownHole;
import au.gov.vic.ecodev.mrt.template.fields.Ds4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class DownHoleUpdaterTest {

	private Template mockTemplate;
	
	@Test
	public void shouldUpdateTheDatabase() throws TemplateProcessorException {
		//Given
		DownHoleDao mockDownHoleDao = Mockito.mock(DownHoleDao.class);		
		List<String> headers = new ArrayList<>(TestFixture.getDsMandatoryColumnList());
		List<Integer> mandatoryIndexList = new ArrayList<>();
		givenMockTemplate(headers);
		when(mockTemplate.get(eq("H0203"))).thenReturn(TestFixture.getNumList());
		DownHoleUpdater testInstance = new DownHoleUpdater(mockDownHoleDao, 1l, mockTemplate);
		testInstance.init(mandatoryIndexList);
		//When
		testInstance.update(TestFixture.getDList());
		//Then
		ArgumentCaptor<DownHole> downHoleCaptor = ArgumentCaptor.forClass(DownHole.class);
		verify(mockDownHoleDao).updateOrSave(downHoleCaptor.capture());
		List<DownHole> downHoles = downHoleCaptor.getAllValues();
		assertThat(downHoles.size(), is(equalTo(1)));
	}
	
	@Test
	public void shouldInitTheRelatedFieldWhenInitMethodCalled() throws TemplateProcessorException {
		//Given
		DownHoleDao mockDownHoleDao = Mockito.mock(DownHoleDao.class);		
		List<String> headers = new ArrayList<>(TestFixture.getDsMandatoryColumnList());
		List<Integer> mandatoryIndexList = new ArrayList<>();
		headers.add(Strings.AZIMUTH_TRUE);
		givenMockTemplate(headers);
		when(mockTemplate.get(eq(Strings.AZIMUTH_TRUE)))
			.thenReturn(Arrays.asList(Strings.AZIMUTH_TRUE));
		DownHoleUpdater testInstance = new DownHoleUpdater(mockDownHoleDao, 1l, mockTemplate);
		//When
		testInstance.init(mandatoryIndexList);
		//Then
		Integer holeIdIndex = Whitebox.getInternalState(testInstance, "holeIdIndex");
		assertThat(holeIdIndex, is(equalTo(0)));
		Integer surveyedDepthIndex = Whitebox.getInternalState(testInstance, "surveyedDepthIndex");
		assertThat(surveyedDepthIndex, is(equalTo(1)));
		Integer azimuthMagIndex = Whitebox.getInternalState(testInstance, "azimuthMagIndex");
		assertThat(azimuthMagIndex, is(equalTo(3)));
		Integer dipIndex = Whitebox.getInternalState(testInstance, "dipIndex");
		assertThat(dipIndex, is(equalTo(2)));
		Integer azimuthTrueIndex = Whitebox.getInternalState(testInstance, "azimuthTrueIndex");
		assertThat(azimuthTrueIndex, is(equalTo(4)));
		assertThat(mandatoryIndexList.size(), is(equalTo(5)));
		assertThat(mandatoryIndexList.contains(0), is(true));
		assertThat(mandatoryIndexList.contains(1), is(true));
		assertThat(mandatoryIndexList.contains(2), is(true));
		assertThat(mandatoryIndexList.contains(3), is(true));
		assertThat(mandatoryIndexList.contains(4), is(true));
	}
	
	@Test
	public void shouldInitTheAzimuthMagWhenAzimuthMagProvidedDuringInitMethodCalled() throws TemplateProcessorException {
		//Given
		Template mockTemplate = Mockito.mock(Template.class);
		DownHoleDao mockBoreHoleDao = Mockito.mock(DownHoleDao.class);		
		List<String> headers = new ArrayList<>(TestFixture.getDsMandatoryColumnList());
		headers.add(Strings.AZIMUTH_TRUE);
		List<Integer> mandatoryIndexList = new ArrayList<>();
		when(mockTemplate.get(eq("H1000"))).thenReturn(headers);
		when(mockTemplate.get(eq(Ds4ColumnHeaders.HOLE_ID.getCode())))
			.thenReturn(Arrays.asList(Ds4ColumnHeaders.HOLE_ID.getCode()));
		when(mockTemplate.get(eq(Ds4ColumnHeaders.SURVEYED_DEPTH.getCode())))
			.thenReturn(Arrays.asList(Ds4ColumnHeaders.SURVEYED_DEPTH.getCode()));
		when(mockTemplate.get(eq(Ds4ColumnHeaders.DIP.getCode())))
			.thenReturn(Arrays.asList(Ds4ColumnHeaders.DIP.getCode()));
		when(mockTemplate.get(eq(Strings.AZIMUTH_TRUE)))
			.thenReturn(Arrays.asList(Strings.AZIMUTH_TRUE));
		DownHoleUpdater testInstance = new DownHoleUpdater(mockBoreHoleDao, 1l, mockTemplate);
		//When
		testInstance.init(mandatoryIndexList);
		//Then
		Integer holeIdIndex = Whitebox.getInternalState(testInstance, "holeIdIndex");
		assertThat(holeIdIndex, is(equalTo(0)));
		Integer surveyedDepthIndex = Whitebox.getInternalState(testInstance, "surveyedDepthIndex");
		assertThat(surveyedDepthIndex, is(equalTo(1)));
		Integer azimuthMagIndex = Whitebox.getInternalState(testInstance, "azimuthMagIndex");
		assertThat(azimuthMagIndex, is(equalTo(Numeral.NOT_FOUND)));
		Integer dipIndex = Whitebox.getInternalState(testInstance, "dipIndex");
		assertThat(dipIndex, is(equalTo(2)));
		Integer azimuthTrueIndex = Whitebox.getInternalState(testInstance, "azimuthTrueIndex");
		assertThat(azimuthTrueIndex, is(equalTo(Numeral.FOUR)));
		assertThat(mandatoryIndexList.size(), is(equalTo(4)));
		assertThat(mandatoryIndexList.contains(0), is(true));
		assertThat(mandatoryIndexList.contains(1), is(true));
		assertThat(mandatoryIndexList.contains(2), is(true));
		assertThat(mandatoryIndexList.contains(4), is(true));
	}
	
	@Test
	public void shouldInitTheAzimuthTrueWhenAzimuthTrueProvidedDuringInitMethodCalled() throws TemplateProcessorException {
		//Given
		DownHoleDao mockBoreHoleDao = Mockito.mock(DownHoleDao.class);		
		List<String> headers = new ArrayList<>(TestFixture.getDsMandatoryColumnList());
		headers.add(Strings.AZIMUTH_TRUE);
		List<Integer> mandatoryIndexList = new ArrayList<>();
		givenMockTemplate(headers);
		DownHoleUpdater testInstance = new DownHoleUpdater(mockBoreHoleDao, 1l, mockTemplate);
		//When
		testInstance.init(mandatoryIndexList);
		//Then
		Integer holeIdIndex = Whitebox.getInternalState(testInstance, "holeIdIndex");
		assertThat(holeIdIndex, is(equalTo(0)));
		Integer surveyedDepthIndex = Whitebox.getInternalState(testInstance, "surveyedDepthIndex");
		assertThat(surveyedDepthIndex, is(equalTo(1)));
		Integer azimuthMagIndex = Whitebox.getInternalState(testInstance, "azimuthMagIndex");
		assertThat(azimuthMagIndex, is(equalTo(3)));
		Integer dipIndex = Whitebox.getInternalState(testInstance, "dipIndex");
		assertThat(dipIndex, is(equalTo(2)));
		Integer azimuthTrueIndex = Whitebox.getInternalState(testInstance, "azimuthTrueIndex");
		assertThat(azimuthTrueIndex, is(equalTo(Numeral.NOT_FOUND)));
		assertThat(mandatoryIndexList.size(), is(equalTo(4)));
		assertThat(mandatoryIndexList.contains(0), is(true));
		assertThat(mandatoryIndexList.contains(1), is(true));
		assertThat(mandatoryIndexList.contains(2), is(true));
		assertThat(mandatoryIndexList.contains(3), is(true));
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionWhenBothAzimuthIndexMissingDuringInitMethodCalled() throws TemplateProcessorException {
		//Given
		mockTemplate = Mockito.mock(Template.class);
		DownHoleDao mockBoreHoleDao = Mockito.mock(DownHoleDao.class);		
		List<String> headers = new ArrayList<>(TestFixture.getDsMandatoryColumnList());
		headers.add(Strings.AZIMUTH_TRUE);
		List<Integer> mandatoryIndexList = new ArrayList<>();
		when(mockTemplate.get(eq("H1000"))).thenReturn(headers);
		when(mockTemplate.get(eq(Ds4ColumnHeaders.HOLE_ID.getCode())))
			.thenReturn(Arrays.asList(Ds4ColumnHeaders.HOLE_ID.getCode()));
		when(mockTemplate.get(eq(Ds4ColumnHeaders.SURVEYED_DEPTH.getCode())))
			.thenReturn(Arrays.asList(Ds4ColumnHeaders.SURVEYED_DEPTH.getCode()));
		when(mockTemplate.get(eq(Ds4ColumnHeaders.DIP.getCode())))
			.thenReturn(Arrays.asList(Ds4ColumnHeaders.DIP.getCode()));
		DownHoleUpdater testInstance = new DownHoleUpdater(mockBoreHoleDao, 1l, mockTemplate);
		//When
		testInstance.init(mandatoryIndexList);
		fail("Program reached unexpected point!");
	}
	
	private void givenMockTemplate(List<String> headers) {
		mockTemplate = Mockito.mock(Template.class);
		when(mockTemplate.get(eq("H1000"))).thenReturn(headers);
		when(mockTemplate.get(eq(Ds4ColumnHeaders.HOLE_ID.getCode())))
			.thenReturn(Arrays.asList(Ds4ColumnHeaders.HOLE_ID.getCode()));
		when(mockTemplate.get(eq(Ds4ColumnHeaders.SURVEYED_DEPTH.getCode())))
			.thenReturn(Arrays.asList(Ds4ColumnHeaders.SURVEYED_DEPTH.getCode()));
		when(mockTemplate.get(eq(Ds4ColumnHeaders.AZIMUTH_MAG.getCode())))
			.thenReturn(Arrays.asList(Ds4ColumnHeaders.AZIMUTH_MAG.getCode()));
		when(mockTemplate.get(eq(Ds4ColumnHeaders.DIP.getCode())))
			.thenReturn(Arrays.asList(Ds4ColumnHeaders.DIP.getCode()));
	}
}

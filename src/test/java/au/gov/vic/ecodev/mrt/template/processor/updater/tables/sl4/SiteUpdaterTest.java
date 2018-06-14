package au.gov.vic.ecodev.mrt.template.processor.updater.tables.sl4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.dao.sl4.SiteDao;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.sl4.SiteUpdater;

public class SiteUpdaterTest {

	private SiteUpdater testInstance;
	
	private Template mockTemplate;
	private SiteDao mockSiteDao;
	
	@Test
	public void shouldReturnEastingMGAIndex() throws TemplateProcessorException {
		// Given
		givenTestInstance();
		// When
		int eastingIndex = testInstance.extractEastingIndex(TestFixture.getColumnHeaderList());
		// Then
		assertThat(eastingIndex, is(equalTo(1)));
	}
	
	@Test
	public void shouldReturnEastingAMGIndex() throws TemplateProcessorException {
		// Given
		givenTestInstance();
		// When
		int eastingIndex = testInstance.extractEastingIndex(TestFixture.getAMGColumnHeaderList());
		// Then
		assertThat(eastingIndex, is(equalTo(1)));
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionEastingIndexWhenHeaderIsAbsent() throws TemplateProcessorException {
		// Given
		givenTestInstance();
		List<String> headers = new ArrayList<>(TestFixture.getColumnHeaderList());
		headers.remove(1);
		// When
		testInstance.extractEastingIndex(headers);
		fail("Program reached unexpected point!");
	}
	
	@Test
	public void shouldReturnNorthingMGAIndex() throws TemplateProcessorException {
		// Given
		givenTestInstance();
		// When
		int eastingIndex = testInstance.extractNorthingIndex(TestFixture.getColumnHeaderList());
		// Then
		assertThat(eastingIndex, is(equalTo(2)));
	}
	
	@Test
	public void shouldReturnNorthingAMGIndex() throws TemplateProcessorException {
		// Given
		givenTestInstance();
		// When
		int eastingIndex = testInstance.extractNorthingIndex(TestFixture.getAMGColumnHeaderList());
		// Then
		assertThat(eastingIndex, is(equalTo(2)));
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionNorthingIndexWhenHeaderIsAbsent() throws TemplateProcessorException {
		// Given
		givenTestInstance();
		List<String> headers = new ArrayList<>(TestFixture.getColumnHeaderList());
		headers.remove(2);
		// When
		testInstance.extractNorthingIndex(headers);
		fail("Program reached unexpected point!");
	}
	
	@Test
	public void shouldReturnProjectName() {
		// Given
		givenTestInstance();
		when(mockTemplate.get(eq("H0102"))).thenReturn(TestFixture.getProjectNameList());
		// When
		String prospect = testInstance.extractProjectNameFromTemplate(mockTemplate);
		// Then
		assertThat(prospect, is(equalTo("Kryptonite")));
	}

	@Test
	public void shouldReturnNullAsProjectNameWhenProjectNameListIsNull() {
		// Given
		givenTestInstance();
		when(mockTemplate.get(eq("H0102"))).thenReturn(null);
		// When
		String prospect = testInstance.extractProjectNameFromTemplate(mockTemplate);
		// Then
		assertThat(prospect, is(nullValue()));
	}

	@Test
	public void shouldReturnNullAsProjectNameWhenProjectNameListIsOnlyOneEntry() {
		// Given
		givenTestInstance();
		List<String> oneEntryList = new ArrayList<>();
		oneEntryList.add("abc");
		when(mockTemplate.get(eq("H0102"))).thenReturn(oneEntryList);
		// When
		String prospect = testInstance.extractProjectNameFromTemplate(mockTemplate);
		// Then
		assertThat(prospect, is(nullValue()));
	}
	
//	@Test
//	public void shouldReturnProjectZone() {
//		// Given
//		givenTestInstance();
//		when(mockTemplate.get(eq("H0531"))).thenReturn(getProjectZoneList());
//		// When
//		BigDecimal amgZone = testInstance.extractAmgZoneFromTemplate(mockTemplate);
//		// Then
//		assertThat(amgZone, is(equalTo(new BigDecimal("54"))));
//	}
//	
//	@Test
//	public void shouldReturnNullAsProjectZoneWhenListContainsNotNumberValue() {
//		// Given
//		givenTestInstance();
//		List<String> projectZoneList = new ArrayList<>();
//		projectZoneList.add("Prjection_zone");
//		projectZoneList.add("abc");
//		when(mockTemplate.get(eq("H0531"))).thenReturn(projectZoneList);
//		// When
//		BigDecimal projectZone = testInstance.extractAmgZoneFromTemplate(mockTemplate);
//		// Then
//		assertThat(projectZone, is(nullValue()));
//	}
//
//	@Test
//	public void shouldReturnNullAsProjectZoneWhenListIsNull() {
//		// Given
//		givenTestInstance();
//		List<String> projectZoneList = null;
//		when(mockTemplate.get(eq("H0531"))).thenReturn(projectZoneList);
//		// When
//		BigDecimal projectZone = testInstance.extractAmgZoneFromTemplate(mockTemplate);
//		// Then
//		assertThat(projectZone, is(nullValue()));
//	}
//
//	@Test
//	public void shouldReturnNullAsProjectzoneWhenListContainsOnlyOneValue() {
//		// Given
//		givenTestInstance();
//		List<String> numList = new ArrayList<>();
//		numList.add("6");
//		when(mockTemplate.get(eq("H0531"))).thenReturn(numList);
//		// When
//		BigDecimal projectZone = testInstance.extractAmgZoneFromTemplate(mockTemplate);
//		// Then
//		assertThat(projectZone, is(nullValue()));
//	}
	
	@Test
	public void shouldReturnLocnAccFromTemplate() {
		// Given
		givenTestInstance();
		when(mockTemplate.get(eq("H0506"))).thenReturn(getLocnAccList());
		// When
		BigDecimal locnAcc = testInstance.extractLocnAccFromTemplate(mockTemplate);
		// Then
		assertThat(locnAcc, is(new BigDecimal("68")));
	}
	
	@Test
	public void shouldReturnNullAsLocnAccWhenListContainsOnlyOneValue() {
		// Given
		givenTestInstance();
		List<String> locnAccList = new ArrayList<>();
		locnAccList.add("6");
		when(mockTemplate.get(eq("H0506"))).thenReturn(locnAccList);
		// When
		BigDecimal locnAcc = testInstance.extractLocnAccFromTemplate(mockTemplate);
		// Then
		assertThat(locnAcc, is(nullValue()));
	}

	@Test
	public void shouldReturnNullAsLocnAccWhenListContainsNotNumberValue() {
		// Given
		givenTestInstance();
		List<String> locnAccList = new ArrayList<>();
		locnAccList.add("Locn_acc");
		locnAccList.add("abc");
		when(mockTemplate.get(eq("H0506"))).thenReturn(locnAccList);
		// When
		BigDecimal locnAcc = testInstance.extractLocnAccFromTemplate(mockTemplate);
		// Then
		assertThat(locnAcc, is(nullValue()));
	}

	@Test
	public void shouldReturnNullAsLocnAccWhenListIsNull() {
		// Given
		givenTestInstance();
		List<String> locnAccList = null;
		when(mockTemplate.get(eq("H0506"))).thenReturn(locnAccList);
		// When
		BigDecimal locnAcc = testInstance.extractLocnAccFromTemplate(mockTemplate);
		// Then
		assertThat(locnAcc, is(nullValue()));
	}
	
	@Test
	public void shouldReturnLocnDatumCdFromTemplate() {
		// Given
		givenTestInstance();
		when(mockTemplate.get(eq("H0501"))).thenReturn(getLocnDatumCdList());
		// When
		String locnDatumCd = testInstance.extractLocnDatumCdFromTemplate(mockTemplate);
		// Then
		assertThat(locnDatumCd, is(equalTo("GDA94")));
	}
	
	@Test
	public void shouldReturnNullAsLocnDatumCdWhenProjectNameListIsNull() {
		// Given
		givenTestInstance();
		when(mockTemplate.get(eq("H0501"))).thenReturn(null);
		// When
		String locnDatumCd = testInstance.extractLocnDatumCdFromTemplate(mockTemplate);
		// Then
		assertThat(locnDatumCd, is(nullValue()));
	}

	@Test
	public void shouldReturnNullAsLocnDatumCdWhenProjectNameListIsOnlyOneEntry() {
		// Given
		givenTestInstance();
		List<String> oneEntryList = new ArrayList<>();
		oneEntryList.add("abc");
		when(mockTemplate.get(eq("H0501"))).thenReturn(oneEntryList);
		// When
		String locnDatumCd = testInstance.extractLocnDatumCdFromTemplate(mockTemplate);
		// Then
		assertThat(locnDatumCd, is(nullValue()));
	}
	
	@Test
	public void shouldReturnElevDatumCdFromTemplate() {
		// Given
		givenTestInstance();
		when(mockTemplate.get(eq("H0502"))).thenReturn(getElevDatumCdList());
		// When
		String elevDatumCd = testInstance.extractElevDatumCdFromTemplate(mockTemplate);
		// Then
		assertThat(elevDatumCd, is(equalTo("AHD")));
	}
	
	@Test
	public void shouldReturnNullAsElevDatumCdWhenProjectNameListIsNull() {
		// Given
		givenTestInstance();
		when(mockTemplate.get(eq("H0502"))).thenReturn(null);
		// When
		String locnDatumCd = testInstance.extractElevDatumCdFromTemplate(mockTemplate);
		// Then
		assertThat(locnDatumCd, is(nullValue()));
	}

	@Test
	public void shouldReturnNullAsElevDatumCdWhenProjectNameListIsOnlyOneEntry() {
		// Given
		givenTestInstance();
		List<String> oneEntryList = new ArrayList<>();
		oneEntryList.add("abc");
		when(mockTemplate.get(eq("H0502"))).thenReturn(oneEntryList);
		// When
		String locnDatumCd = testInstance.extractElevDatumCdFromTemplate(mockTemplate);
		// Then
		assertThat(locnDatumCd, is(nullValue()));
	}
	
	@Test
	public void shouldReturnOptionalRecord() {
		//Given
		givenTestInstance();
		//When
		BigDecimal optionalNumber = testInstance.extractOptionalNumberFromRecord(TestFixture.getDList(), 2);
		//Then
		assertThat(optionalNumber, is(equalTo(new BigDecimal("5845150"))));
	}
	
	@Test
	public void shouldReturnEastingRecord() {
		//Given
		givenTestInstance();
		//When
		BigDecimal easting = testInstance.extractEastingFromRecord(TestFixture.getDList(), 1);
		//Then
		assertThat(easting, is(equalTo(new BigDecimal("630500"))));
	}
	
	@Test
	public void shouldReturnNorthingRecord() {
		//Given
		givenTestInstance();
		//When
		BigDecimal northing = testInstance.extractNorthingFromRecord(TestFixture.getDList(), 2);
		//Then
		assertThat(northing, is(equalTo(new BigDecimal("5845150"))));
	}
	
	@Test
	public void shouldReturnNullAsEastingWhenEastingStringIsNull() throws TemplateProcessorException {
		// Given
		givenTestInstance();
		List<String> d1 = new ArrayList<>(TestFixture.getDList());
		d1.set(1, null);
		// When
		BigDecimal easting =testInstance.extractEastingFromRecord(d1, 1);
		//Then
		assertThat(easting, is(nullValue()));
	}
	
	@Test
	public void shouldReturnNullAsEastingWhenEastingStringIsRubbish() throws TemplateProcessorException {
		// Given
		givenTestInstance();
		List<String> d1 = new ArrayList<>(TestFixture.getDList());
		d1.set(1, "abcv");
		// When
		BigDecimal easting =testInstance.extractEastingFromRecord(d1, 1);
		//Then
		assertThat(easting, is(nullValue()));
	}

	@Test
	public void shouldReturnNullAsNorthingWhenNorthingStringIsNull() throws TemplateProcessorException {
		// Given
		givenTestInstance();
		List<String> d1 = new ArrayList<>(TestFixture.getDList());
		d1.set(2, null);
		// When
		BigDecimal easting =testInstance.extractNorthingFromRecord(d1, 2);
		//Then
		assertThat(easting, is(nullValue()));
	}
	
	@Test
	public void shouldReturnNullAsNorthingWhenNorthingStringIsRubbish() throws TemplateProcessorException {
		// Given
		givenTestInstance();
		List<String> d1 = new ArrayList<>(TestFixture.getDList());
		d1.set(2, "abcv");
		// When
		BigDecimal easting =testInstance.extractNorthingFromRecord(d1, 2);
		//Then
		assertThat(easting, is(nullValue()));
	}
	
	@Test
	public void shouldReturnNullAsOptionalWhenOptionalStringIsNull() throws TemplateProcessorException {
		// Given
		givenTestInstance();
		List<String> d1 = new ArrayList<>(TestFixture.getDList());
		d1.set(2, null);
		// When
		BigDecimal easting =testInstance.extractOptionalNumberFromRecord(d1, 2);
		//Then
		assertThat(easting, is(nullValue()));
	}
	
	@Test
	public void shouldReturnNullAsOptionalWhenOptionalStringIsRubbish() throws TemplateProcessorException {
		// Given
		givenTestInstance();
		List<String> d1 = new ArrayList<>(TestFixture.getDList());
		d1.set(2, "abcv");
		// When
		BigDecimal easting =testInstance.extractOptionalNumberFromRecord(d1, 2);
		//Then
		assertThat(easting, is(nullValue()));
	}
	
	@Test
	public void shouldReturnNullAsOptionalWhenOptionalIndexIsRubbish() throws TemplateProcessorException {
		// Given
		givenTestInstance();
		List<String> d1 = new ArrayList<>(TestFixture.getDList());
		// When
		BigDecimal easting =testInstance.extractOptionalNumberFromRecord(d1, -1);
		//Then
		assertThat(easting, is(nullValue()));
	}
	
	@Test
	public void shouldInitialisedAllIndex() throws TemplateProcessorException {
		// Given
		givenTestInstance();
		when(mockTemplate.get(eq("H1000"))).thenReturn(TestFixture.getColumnHeaderList());
		List<Integer> mandatoryFieldsIndexList = new ArrayList<>();
		// When
		testInstance.init(mandatoryFieldsIndexList);
		// Then
		assertThat(CollectionUtils.isEmpty(mandatoryFieldsIndexList), is(false));
		assertThat(mandatoryFieldsIndexList.size(), is(equalTo(3)));
		assertThat(mandatoryFieldsIndexList.contains(new Integer(0)), is(true));
		assertThat(mandatoryFieldsIndexList.contains(new Integer(1)), is(true));
		assertThat(mandatoryFieldsIndexList.contains(new Integer(2)), is(true));
	}
	
	@Test
	public void shouldInitialisedAllAmgIndex() throws TemplateProcessorException {
		// Given
		givenTestInstance();
		when(mockTemplate.get(eq("H1000"))).thenReturn(TestFixture.getAMGColumnHeaderList());
		List<Integer> mandatoryFieldsIndexList = new ArrayList<>();
		// When
		testInstance.init(mandatoryFieldsIndexList);
		// Then
		assertThat(CollectionUtils.isEmpty(mandatoryFieldsIndexList), is(false));
		assertThat(mandatoryFieldsIndexList.size(), is(equalTo(5)));
		assertThat(mandatoryFieldsIndexList.contains(new Integer(0)), is(true));
		assertThat(mandatoryFieldsIndexList.contains(new Integer(1)), is(true));
		assertThat(mandatoryFieldsIndexList.contains(new Integer(2)), is(true));
		assertThat(mandatoryFieldsIndexList.contains(new Integer(7)), is(true));
		assertThat(mandatoryFieldsIndexList.contains(new Integer(8)), is(true));
	}
	
	@Test
	public void shouldInitialisedH0531Field() throws TemplateProcessorException {
		// Given
		givenTestInstance();
		when(mockTemplate.get(eq("H1000"))).thenReturn(TestFixture.getAMGColumnHeaderList());
		when(mockTemplate.get(eq("H0531"))).thenReturn(TestFixture.getProjectZoneList());
		List<Integer> mandatoryFieldsIndexList = new ArrayList<>();
		// When
		testInstance.init(mandatoryFieldsIndexList);
		// Then
		BigDecimal amgZone = Whitebox.getInternalState(testInstance, "amgZone");
		assertThat(amgZone, is(equalTo(new BigDecimal("54"))));
	}
	
	private void givenTestInstance() {
		mockTemplate = Mockito.mock(Template.class);
		mockSiteDao = Mockito.mock(SiteDao.class);
		testInstance = new SiteUpdater(1l, mockTemplate, mockSiteDao);
	}
	
	private List<String> getLocnAccList() {
		List<String> locnAccList = new ArrayList<>();
		locnAccList.add("Locn_acc");
		locnAccList.add("68");
		return locnAccList;
	}
	
	private List<String> getLocnDatumCdList() {
		List<String> locnDatumCdList = new ArrayList<>();
		locnDatumCdList.add("Geodetic_datum");
		locnDatumCdList.add("GDA94");
		return locnDatumCdList;
	}
	
	private List<String> getElevDatumCdList() {
		List<String> elevDatumCdList = new ArrayList<>();
		elevDatumCdList.add("Vertical_datum");
		elevDatumCdList.add("AHD");
		return elevDatumCdList;
	}
	
	
}

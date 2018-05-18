package au.gov.vic.ecodev.mrt.template.processor.updater.tables.sl4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.dao.sl4.BoreHoleDao;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.sl4.BoreHoleUpdater;

public class BoreHoleUpdaterTest {

	@Test
	public void shouldInitTheRelatedFieldWhenInitMethodCalled() throws TemplateProcessorException {
		//Given
		Template mockTemplate = Mockito.mock(Template.class);
		BoreHoleDao mockBoreHoleDao = Mockito.mock(BoreHoleDao.class);		
		when(mockTemplate.get(eq("H1000"))).thenReturn(TestFixture.getColumnHeaderList());
		when(mockTemplate.get(eq("H0200"))).thenReturn(TestFixture.getDateList());
		when(mockTemplate.get(eq("H0201"))).thenReturn(TestFixture.getDateList());
		List<Integer> mandatoryFieldIndexList = new ArrayList<>();
		BoreHoleUpdater testInstance = new BoreHoleUpdater(1l, mockTemplate, mockBoreHoleDao,
				new HashMap<String, Long>());
		//When
		testInstance.init(mandatoryFieldIndexList);
		//Then
		Integer holeIdIndex = Whitebox.getInternalState(testInstance, "holeIdIndex");
		assertThat(holeIdIndex, is(equalTo(0)));
		Integer mdDepthIndex = Whitebox.getInternalState(testInstance, "mdDepthIndex");
		assertThat(mdDepthIndex, is(equalTo(4)));
		Integer drillingCodeIndex = Whitebox.getInternalState(testInstance, "drillingCodeIndex");
		assertThat(drillingCodeIndex, is(equalTo(5)));
		Integer azimuthMagIndex = Whitebox.getInternalState(testInstance, "azimuthMagIndex");
		assertThat(azimuthMagIndex, is(equalTo(7)));
		Date startDate = Whitebox.getInternalState(testInstance, "drillingStartDate");
		assertThat(startDate, is(notNullValue()));
		Date endDate = Whitebox.getInternalState(testInstance, "drillingCompletionDate");
		assertThat(endDate, is(notNullValue()));
		assertThat(CollectionUtils.isEmpty(mandatoryFieldIndexList), is(false));
		assertThat(mandatoryFieldIndexList.size(), is(equalTo(4)));
		assertThat(mandatoryFieldIndexList.contains(new Integer(3)), is(true));
		assertThat(mandatoryFieldIndexList.contains(new Integer(4)), is(true));
		assertThat(mandatoryFieldIndexList.contains(new Integer(5)), is(true));
		assertThat(mandatoryFieldIndexList.contains(new Integer(7)), is(true));
	}
	
	@Test
	public void shouldReturnDrillingDetailsIdWhenDrillingCodeProvided() throws TemplateProcessorException {
		//Given
		Template mockTemplate = Mockito.mock(Template.class);
		BoreHoleDao mockBoreHoleDao = Mockito.mock(BoreHoleDao.class);		
		when(mockTemplate.get(eq("H1000"))).thenReturn(TestFixture.getColumnHeaderList());
		Map<String, Long> drillingCodes = new HashMap<>();
		drillingCodes.put("DDT", 123l);
		BoreHoleUpdater testInstance = new BoreHoleUpdater(1l, mockTemplate, mockBoreHoleDao,
				drillingCodes);
		List<Integer> mandatoryFieldIndexList = new ArrayList<>();
		testInstance.init(mandatoryFieldIndexList);
		List<String> dataRecordList = TestFixture.getDList();
		//When
		long drillingDetailsId = testInstance.getDillingDetailsId(dataRecordList);
		//Then
		assertThat(drillingDetailsId, is(equalTo(123l)));
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaisingExceptionWhenDrillingCodeIsNotProvidedInDataRecord() throws TemplateProcessorException {
		//Given
		Template mockTemplate = Mockito.mock(Template.class);
		BoreHoleDao mockBoreHoleDao = Mockito.mock(BoreHoleDao.class);		
		when(mockTemplate.get(eq("H1000"))).thenReturn(TestFixture.getColumnHeaderList());
		Map<String, Long> drillingCodes = new HashMap<>();
		drillingCodes.put("DDT", 123l);
		BoreHoleUpdater testInstance = new BoreHoleUpdater(1l, mockTemplate, mockBoreHoleDao,
				drillingCodes);
		List<Integer> mandatoryFieldIndexList = new ArrayList<>();
		testInstance.init(mandatoryFieldIndexList);
		List<String> dataRecordList = null;
		//When
		testInstance.getDillingDetailsId(dataRecordList);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaisingExceptionWhenDrillingCodeIsNotProvidedInDrillingCodesMap() throws TemplateProcessorException {
		//Given
		Template mockTemplate = Mockito.mock(Template.class);
		BoreHoleDao mockBoreHoleDao = Mockito.mock(BoreHoleDao.class);		
		when(mockTemplate.get(eq("H1000"))).thenReturn(TestFixture.getColumnHeaderList());
		Map<String, Long> drillingCodes = new HashMap<>();
		drillingCodes.put("DD", 123l);
		BoreHoleUpdater testInstance = new BoreHoleUpdater(1l, mockTemplate, mockBoreHoleDao,
				drillingCodes);
		List<Integer> mandatoryFieldIndexList = new ArrayList<>();
		testInstance.init(mandatoryFieldIndexList);
		List<String> dataRecordList = TestFixture.getDList();
		//When
		testInstance.getDillingDetailsId(dataRecordList);
		fail("Program reached unexpected point!");
	}
}

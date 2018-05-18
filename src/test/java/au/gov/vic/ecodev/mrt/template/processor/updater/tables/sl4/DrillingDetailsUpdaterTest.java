package au.gov.vic.ecodev.mrt.template.processor.updater.tables.sl4;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.dao.sl4.DrillingDetailsDao;
import au.gov.vic.ecodev.mrt.dao.sl4.DrillingDetailsDaoImpl;
import au.gov.vic.ecodev.mrt.model.sl4.DrillingDetails;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Entity;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.persistent.Dao;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.sl4.DrillingDetailsUpdater;

public class DrillingDetailsUpdaterTest {

	private List<Dao> daos;
	private DrillingDetailsUpdater drillingDetailsUpdater;
	private Template mockTemplate;
	private Map<String, Long> drillingCodes;
	
	@Test
	public void shouldUpdateTheDatabase() throws TemplateProcessorException {
		//Given
		givenMockDaos();
		DrillingDetailsDao mockDrillingDetailsDao = Mockito.mock(DrillingDetailsDao.class);
		daos.add(mockDrillingDetailsDao);
		DrillingDetails mockDrillingDetails = Mockito.mock(DrillingDetails.class);
		when(mockDrillingDetails.getDrillType()).thenReturn("RC");
		when(mockDrillingDetails.getId()).thenReturn(12345l);
		when(mockDrillingDetailsDao.getByDrillingTypeAndCompany(eq("RC"), eq("Drill Well Pty Ltd")))
			.thenReturn(mockDrillingDetails);
		when(mockDrillingDetailsDao.updateOrSave(Matchers.any(Entity.class)))
			.thenReturn(true);
		givenTestInstance(daos);
		when(mockTemplate.get("H0400")).thenReturn(getDrillingCodeList());
		when(mockTemplate.get("H0401")).thenReturn(getDrillingCompanyList());
		List<String> drillingDescList = getDrillingDescList();
//		drillingDescList.add("def");
		when(mockTemplate.get("H0402")).thenReturn(drillingDescList);
		//When
		drillingDetailsUpdater.update();
		//Then
		verify(mockDrillingDetailsDao, times(2)).getByDrillingTypeAndCompany(Matchers.anyString(), Matchers.anyString());
		verify(mockDrillingDetailsDao, times(2)).updateOrSave(Matchers.any(Entity.class));
		ArgumentCaptor<String> descCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockDrillingDetails).setDrillDescription(descCaptor.capture());
		assertThat(descCaptor.getValue(), is(equalTo("def")));
		assertThat(drillingCodes.size(), is(equalTo(2)));
		drillingCodes.entrySet().forEach(e ->{System.out.println(e.getKey() + " " + e.getValue());});
		assertThat(drillingCodes.get("DD"), is(notNullValue()));
		assertThat(drillingCodes.get("RC"), is(notNullValue()));
	}

	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionWhenDrillingDetailsDaoNotFound() throws TemplateProcessorException {
		//Given
		givenMockDaos();
		givenTestInstance(daos);
		//When
		drillingDetailsUpdater.getDrillingDetailsDao();
		fail("Program reached unexpected point!");
	}
	
	@Test
	public void shouldReturnDrillingDetailsDao() throws TemplateProcessorException {
		//Given
		givenDaos();
		givenTestInstance(daos);
		//When
		DrillingDetailsDao  drillingDetailsDao = drillingDetailsUpdater.getDrillingDetailsDao();
		//Then
		assertThat(drillingDetailsDao, is(notNullValue()));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		givenDaos();
		givenTestInstance(daos);
		//When
		//Then
		assertThat(drillingDetailsUpdater, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenDaoListIsEmpty() {
		//Given
		List<Dao> daos = null;
		Template template = null;
		Map<String, Long> drillingCodes = null;
		//When
		new DrillingDetailsUpdater(daos, template, drillingCodes);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenTemplateIsNull() {
		//Given
		givenDaos();
		Template template = null;
		Map<String, Long> drillingCodes = null;
		//When
		new DrillingDetailsUpdater(daos, template, drillingCodes);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenDrillingCodesIsNull() {
		//Given
		givenDaos();
		Template template = Mockito.mock(Template.class);
		Map<String, Long> drillingCodes = null;
		//When
		new DrillingDetailsUpdater(daos, template, drillingCodes);
		fail("Program reached unexpected point!");
	}
	
	private void givenTestInstance(List<Dao> daos) {
		mockTemplate = Mockito.mock(Template.class);
		drillingCodes = new HashMap<>();
		drillingDetailsUpdater = new DrillingDetailsUpdater(daos, mockTemplate, drillingCodes);
	}
	
	private void givenDaos() {
		daos = new ArrayList<>();
		daos.add(new DrillingDetailsDaoImpl());
	}
	
	private void givenMockDaos() {
		daos = new ArrayList<>();
		daos.add(Mockito.mock(Dao.class));
	}
	
	private List<String> getDrillingCodeList() {
		List<String> drillingCodeList = getOneDrillingCodeList();
		
		drillingCodeList.add("DD");
		drillingCodeList.add("RC");
		return drillingCodeList;
	}
	
	private List<String> getOneDrillingCodeList() {
		List<String> drillingCodeList = new ArrayList<>();
		drillingCodeList.add("Drill_code");
		
		return drillingCodeList;
	}
	
	private List<String> getDrillingCompanyList() {
		List<String> drillingCompanyList = getOneDrillingCompanyList();
		
		drillingCompanyList.add("Drill Faster Pty Ltd");
		drillingCompanyList.add("Drill Well Pty Ltd");
		return drillingCompanyList;
	}
	
	private List<String> getOneDrillingCompanyList() {
		List<String> drillingCompanyList = new ArrayList<>();
		drillingCompanyList.add("Drill_contractor");
		
		return drillingCompanyList;
	}
	
	private List<String> getDrillingDescList() {
		List<String> drillingDescList = getOneDrillingDescList();
		
		drillingDescList.add("def");
		
		return drillingDescList;
	}
	
	private List<String> getOneDrillingDescList() {
		List<String> drillingDescList = new ArrayList<>();
		drillingDescList.add("Description");
		drillingDescList.add("abc");
		
		return drillingDescList;
	}
}

package au.gov.vic.ecodev.mrt.template.processor.updater.sl4;

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
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.dao.TemplateOptionalFieldDao;
import au.gov.vic.ecodev.mrt.dao.TemplateOptionalFieldDaoImpl;
import au.gov.vic.ecodev.mrt.dao.sl4.BoreHoleDao;
import au.gov.vic.ecodev.mrt.dao.sl4.BoreHoleDaoImpl;
import au.gov.vic.ecodev.mrt.dao.sl4.SiteDao;
import au.gov.vic.ecodev.mrt.dao.sl4.SiteDaoImpl;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.model.TemplateOptionalField;
import au.gov.vic.ecodev.mrt.model.sl4.BoreHole;
import au.gov.vic.ecodev.mrt.model.sl4.Site;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.persistent.Dao;

public class Sl4DateRecordUpdaterTest {

	private Sl4DataRecordUpdater testInstance;
	private Template mockTemplate;
	private Map<String, Long> drillingCodes;

	@Test
	public void shouldUpdateTheDataBase() throws TemplateProcessorException {
		// Given
		List<Dao> daos = getDaos();
		SiteDao mockSiteDao = Mockito.mock(SiteDaoImpl.class);
		daos.add(mockSiteDao);
		BoreHoleDao mockBoreHoleDao = Mockito.mock(BoreHoleDaoImpl.class);
		daos.add(mockBoreHoleDao);
		TemplateOptionalFieldDao mockTemplateOptionalFieldDao = Mockito.mock(TemplateOptionalFieldDaoImpl.class);
		daos.add(mockTemplateOptionalFieldDao);
		givenTestInstance(daos);
		when(mockTemplate.get(eq("H0203"))).thenReturn(TestFixture.getNumList());
		when(mockTemplate.get(eq("H1000"))).thenReturn(TestFixture.getColumnHeaderListWithOptionalFields());
		when(mockTemplate.get(eq("H1001"))).thenReturn(TestFixture.getSl4H1001List());
		when(mockTemplate.get(eq("H1004"))).thenReturn(TestFixture.getSl4H1004List());
		when(mockTemplate.get(eq("H0531"))).thenReturn(TestFixture.getProjectZoneList());
		when(mockTemplate.get(eq("D1"))).thenReturn(TestFixture.getDListWithOptionalFields());
		when(mockTemplate.get(eq("D2"))).thenReturn(TestFixture.getDListWithOptionalFields());
		when(mockTemplate.get(eq("D3"))).thenReturn(TestFixture.getDListWithOptionalFields());
		// When
		testInstance.update();
		// Then
		ArgumentCaptor<Site> siteCaptor = ArgumentCaptor.forClass(Site.class);
		ArgumentCaptor<BoreHole> boreHoleCaptor = ArgumentCaptor.forClass(BoreHole.class);
		ArgumentCaptor<TemplateOptionalField> optionalFieldCaptor = 
				ArgumentCaptor.forClass(TemplateOptionalField.class);
		verify(mockSiteDao, times(3)).updateOrSave(siteCaptor.capture());
		verify(mockBoreHoleDao, times(3)).updateOrSave(boreHoleCaptor.capture());
		verify(mockTemplateOptionalFieldDao, times(24)).updateOrSave(optionalFieldCaptor.capture());
		Site capturedSite = siteCaptor.getValue();
		assertThat(capturedSite, is(notNullValue()));
		BoreHole capturedBoreHole = boreHoleCaptor.getValue();
		assertThat(capturedBoreHole, is(notNullValue()));
		TemplateOptionalField capturedTemplateOptionalField = optionalFieldCaptor.getValue();
		assertThat(capturedTemplateOptionalField, is(notNullValue()));
	}
	
	@Test
	public void shouldReturnSiteDao() throws TemplateProcessorException {
		//Given
		List<Dao> daos = getDaos();
		daos.add(Mockito.mock(SiteDao.class));
		givenTestInstance(daos);
		//When
		SiteDao  siteDao = testInstance.getSiteDao();
		//Then
		assertThat(siteDao, is(notNullValue()));
	}

	@Test
	public void shouldReturnInstance() {
		// Given
		List<Dao> daos = getDaos();
		givenTestInstance(daos);
		// Then
		assertThat(testInstance, is(notNullValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenDaoIsNull() {
		// Given
		List<Dao> daos = null;
		long sessionId = 1l;
		Template mockTemplate = null;
		Map<String, Long> drillingCodes = null;
		// When
		testInstance = new Sl4DataRecordUpdater(daos, sessionId, mockTemplate, drillingCodes);
		fail("Program reached unexpected point!");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenTemplateIsNull() {
		// Given
		List<Dao> daos = getDaos();
		long sessionId = 1l;
		Template mockTemplate = null;
		Map<String, Long> drillingCodes = null;
		// When
		testInstance = new Sl4DataRecordUpdater(daos, sessionId, mockTemplate, drillingCodes);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenDrillingCodesIsNull() {
		// Given
		List<Dao> daos = getDaos();
		long sessionId = 1l;
		Template mockTemplate = Mockito.mock(Template.class);
		Map<String, Long> drillingCodes = null;
		// When
		testInstance = new Sl4DataRecordUpdater(daos, sessionId, mockTemplate, drillingCodes);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionWhenNoSiteDaoFound() throws TemplateProcessorException {
		//Given
		givenTestInstance(getDaos());
		//When
		testInstance.getSiteDao();
		fail("Program reached unexpected point!");
	}
	
	@Test
	public void shouldReturnBoreHoleDao() throws TemplateProcessorException {
		//Given
		List<Dao> daos = getDaos();
		daos.add(Mockito.mock(BoreHoleDao.class));
		givenTestInstance(daos);
		//When
		BoreHoleDao  boreHoleDao = testInstance.getBoreHoleDao();
		//Then
		assertThat(boreHoleDao, is(notNullValue()));
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionWhenNoBoreHoleDaoFound() throws TemplateProcessorException {
		//Given
		givenTestInstance(getDaos());
		//When
		testInstance.getBoreHoleDao();
		fail("Program reached unexpected point!");
	}
	
	@Test
	public void shouldReturnTemplateOptionalFieldDao() throws TemplateProcessorException {
		//Given
		List<Dao> daos = getDaos();
		daos.add(Mockito.mock(TemplateOptionalFieldDao.class));
		givenTestInstance(daos);
		//When
		TemplateOptionalFieldDao  templateOptionalFieldDao = testInstance.getTemplateOptionalFieldDao();
		//Then
		assertThat(templateOptionalFieldDao, is(notNullValue()));
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionWhenNoTemplateOptionalFieldDaoFound() 
			throws TemplateProcessorException {
		//Given
		givenTestInstance(getDaos());
		//When
		testInstance.getTemplateOptionalFieldDao();
		fail("Program reached unexpected point!");
	}

	private List<Dao> getDaos() {
		List<Dao> daos = new ArrayList<>();
		daos.add(Mockito.mock(Dao.class));
		return daos;
	}

	private void givenTestInstance(List<Dao> daos) {
		long sessionId = 1l;
		mockTemplate = Mockito.mock(Template.class);
		drillingCodes = new HashMap<>();
		drillingCodes.put("DDT", 123l);
		testInstance = new Sl4DataRecordUpdater(daos, sessionId, mockTemplate, drillingCodes);
	}
}

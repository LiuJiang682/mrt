package au.gov.vic.ecodev.mrt.template.processor.updater.dg4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import au.gov.vic.ecodev.mrt.dao.TemplateMandatoryHeaderFieldDao;
import au.gov.vic.ecodev.mrt.dao.TemplateMandatoryHeaderFieldDaoImpl;
import au.gov.vic.ecodev.mrt.dao.TemplateOptionalFieldDao;
import au.gov.vic.ecodev.mrt.dao.TemplateOptionalFieldDaoImpl;
import au.gov.vic.ecodev.mrt.dao.dg4.GeoChemistryDao;
import au.gov.vic.ecodev.mrt.dao.dg4.GeoChemistryDaoImpl;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.fields.Dg4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.persistent.Dao;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.TemplateHeaderOptionalFieldUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.TemplateOptionalFieldUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.dg4.GeoChemistryUpdater;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Dg4TemplateUpdater.class })
public class Dg4TemplateUpdaterTest {

	private Dg4TemplateUpdater testInstance;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shouldExecuteUpdateMethod() throws Exception {
		// Given
		testInstance = new Dg4TemplateUpdater();
		long sessionId = 1l;
		Template mockTemplate = Mockito.mock(Template.class);
		when(mockTemplate.get(eq("H0203"))).thenReturn(TestFixture.getNumList());
		when(mockTemplate.get(eq("H1000"))).thenReturn(TestFixture.getDg4ColumnHeaderList());
		when(mockTemplate.get(eq(Dg4ColumnHeaders.HOLE_ID.getCode())))
			.thenReturn(Arrays.asList("Hole_id"));
		when(mockTemplate.get(eq(Dg4ColumnHeaders.SAMPLE_ID.getCode())))
			.thenReturn(Arrays.asList("Sample ID"));
		when(mockTemplate.get(eq(Dg4ColumnHeaders.FROM.getCode())))
			.thenReturn(Arrays.asList("Depth From"));
		when(mockTemplate.get(eq(Dg4ColumnHeaders.TO.getCode())))
			.thenReturn(Arrays.asList("Depth To"));
		GeoChemistryDao mockGeoChemistryDao = Mockito.mock(GeoChemistryDao.class);
		TemplateMandatoryHeaderFieldDao mockTemplateMandatoryHeaderFieldDao =
				Mockito.mock(TemplateMandatoryHeaderFieldDao.class);
		TemplateOptionalFieldDao mockTemplateOptionalFieldDao = 
				Mockito.mock(TemplateOptionalFieldDao.class);
		testInstance.setDaos(Arrays.asList(mockGeoChemistryDao, 
				mockTemplateMandatoryHeaderFieldDao,
				mockTemplateOptionalFieldDao));
		GeoChemistryUpdater mockGeoChemistryUpdater = Mockito.mock(GeoChemistryUpdater.class);
		PowerMockito.whenNew(GeoChemistryUpdater.class)
			.withArguments(eq(mockGeoChemistryDao), eq(sessionId), eq(mockTemplate))
			.thenReturn(mockGeoChemistryUpdater);
		PowerMockito.doCallRealMethod().when(mockGeoChemistryUpdater)
			.init(Matchers.anyList());
		Whitebox.setInternalState(mockGeoChemistryUpdater, "template", mockTemplate);
		TemplateOptionalFieldUpdater mockTemplateOptionalFieldUpdater = 
				Mockito.mock(TemplateOptionalFieldUpdater.class);
		PowerMockito.whenNew(TemplateOptionalFieldUpdater.class)
			.withArguments(eq(sessionId), eq(mockTemplate), eq(mockTemplateOptionalFieldDao))
			.thenReturn(mockTemplateOptionalFieldUpdater);
		TemplateHeaderOptionalFieldUpdater mockTemplateHeaderOptionalFieldUpdater = 
				Mockito.mock(TemplateHeaderOptionalFieldUpdater.class);
		PowerMockito.whenNew(TemplateHeaderOptionalFieldUpdater.class)
			.withArguments(eq(sessionId), eq(mockTemplate), 
					Matchers.any(List.class),
					eq(mockTemplateOptionalFieldDao), Matchers.any(List.class))
			.thenReturn(mockTemplateHeaderOptionalFieldUpdater);
		// When
		testInstance.update(sessionId, mockTemplate);
		// Then
		ArgumentCaptor<List> indexCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockGeoChemistryUpdater).init(indexCaptor.capture());
		List capturedIndexList = indexCaptor.getAllValues();
		assertThat(capturedIndexList, is(notNullValue()));
		assertThat(capturedIndexList.size(), is(equalTo(1)));
		
		ArgumentCaptor<Integer> myIndexCaptor = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<List> listCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockGeoChemistryUpdater, times(3)).update(listCaptor.capture(), myIndexCaptor.capture());
		List capturedList = listCaptor.getAllValues();
		assertThat(capturedList, is(notNullValue()));
		assertThat(capturedList.size(), is(equalTo(3)));
		List indexList = myIndexCaptor.getAllValues();
		assertThat(indexList, is(notNullValue()));
		assertThat(indexList.size(), is(equalTo(3)));
		for (int i = 0; i < 3; i++) {
			assertThat(indexList.get(i), is(equalTo(i + 1)));
		}
		verifyNoMoreInteractions(mockGeoChemistryUpdater);
		
		ArgumentCaptor<List> optionalListCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockTemplateOptionalFieldUpdater).init(optionalListCaptor.capture());
		List capturedMIndexList = optionalListCaptor.getAllValues();
		assertThat(capturedMIndexList.size(), is(equalTo(1)));
		
		ArgumentCaptor<List> optionalDataListDataCaptor = ArgumentCaptor.forClass(List.class);
		ArgumentCaptor<Integer> rowNumberCaptor = ArgumentCaptor.forClass(Integer.class);
		verify(mockTemplateOptionalFieldUpdater, times(3))
			.update(optionalDataListDataCaptor.capture(), rowNumberCaptor.capture());
		List capturedOptionalDataList = optionalDataListDataCaptor.getAllValues();
		assertThat(capturedOptionalDataList.size(), is(equalTo(3)));
		List capturedRowNumberList = rowNumberCaptor.getAllValues();
		assertThat(capturedRowNumberList.size(), is(equalTo(3)));
		verify(mockTemplateOptionalFieldUpdater).flush();
		verifyNoMoreInteractions(mockTemplateOptionalFieldUpdater);
		
		PowerMockito.verifyNew(GeoChemistryUpdater.class)
			.withArguments(eq(mockGeoChemistryDao), eq(sessionId), eq(mockTemplate));
		
		PowerMockito.verifyNew(TemplateOptionalFieldUpdater.class)
			.withArguments(eq(sessionId), eq(mockTemplate), eq(mockTemplateOptionalFieldDao));
		
		PowerMockito.verifyNoMoreInteractions();
	}
	
	@Test
	public void shouldReturnGeoChemistryDao() throws TemplateProcessorException {
		//Given
		testInstance = new Dg4TemplateUpdater();
		GeoChemistryDao mockGeoChemistryDao = Mockito.mock(GeoChemistryDao.class);
		testInstance.setDaos(Arrays.asList(mockGeoChemistryDao));
		//When
		GeoChemistryDao dao = testInstance.getGeoChemistryDao();
		//Then
		assertThat(dao, is(notNullValue()));
		assertThat(dao, is(equalTo(mockGeoChemistryDao)));
	}
	
	@Test
	public void shouldReturnTemplateOptionalFieldDao() throws TemplateProcessorException {
		//Given
		testInstance = new Dg4TemplateUpdater();
		TemplateOptionalFieldDao mockTemplateOptionalFieldDao = Mockito.mock(TemplateOptionalFieldDao.class);
		testInstance.setDaos(Arrays.asList(mockTemplateOptionalFieldDao));
		//When
		TemplateOptionalFieldDao dao = testInstance.getTemplateOptionalFieldDao();
		//Then
		assertThat(dao, is(notNullValue()));
		assertThat(dao, is(equalTo(mockTemplateOptionalFieldDao)));
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionWhenGeoChemistryDaoNotFound() throws TemplateProcessorException {
		//Given
		testInstance = new Dg4TemplateUpdater();
		Dao mockDao = Mockito.mock(Dao.class);
		testInstance.setDaos(Arrays.asList(mockDao));
		//When
		testInstance.getGeoChemistryDao();
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionWhenTemplateOptionalFieldDaoNotFound() throws TemplateProcessorException {
		//Given
		testInstance = new Dg4TemplateUpdater();
		Dao mockDao = Mockito.mock(Dao.class);
		testInstance.setDaos(Arrays.asList(mockDao));
		//When
		testInstance.getTemplateOptionalFieldDao();
		fail("Program reached unexpected point!");
	}
	
	@Test
	public void shouldReturnListOfDaoClasses() {
		// Given
		testInstance = new Dg4TemplateUpdater();
		// When
		List<Class<? extends Dao>> daos = testInstance.getDaoClasses();
		// Then
		assertThat(daos, is(notNullValue()));
		assertThat(daos.size(), is(equalTo(3)));
		assertThat(daos.get(0), is(equalTo(GeoChemistryDaoImpl.class)));
		assertThat(daos.get(1), is(equalTo(TemplateMandatoryHeaderFieldDaoImpl.class)));
		assertThat(daos.get(2), is(equalTo(TemplateOptionalFieldDaoImpl.class)));
	}
}

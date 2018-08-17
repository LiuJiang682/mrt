package au.gov.vic.ecodev.mrt.template.processor.updater.dl4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

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

import au.gov.vic.ecodev.mrt.dao.TemplateMandatoryHeaderFieldDao;
import au.gov.vic.ecodev.mrt.dao.TemplateMandatoryHeaderFieldDaoImpl;
import au.gov.vic.ecodev.mrt.dao.TemplateOptionalFieldDao;
import au.gov.vic.ecodev.mrt.dao.TemplateOptionalFieldDaoImpl;
import au.gov.vic.ecodev.mrt.dao.dl4.LithologyDao;
import au.gov.vic.ecodev.mrt.dao.dl4.LithologyDaoImpl;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.persistent.Dao;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.TemplateHeaderOptionalFieldUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.TemplateOptionalFieldUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.dl4.LithologyUpdater;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Dl4TemplateUpdater.class })
public class Dl4TemplateupdaterTest {

	private Dl4TemplateUpdater testInstance;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shouldExecuteUpdateMethod() throws Exception {
		// Given
		testInstance = new Dl4TemplateUpdater();
		long sessionId = 1l;
		Template mockTemplate = Mockito.mock(Template.class);
		when(mockTemplate.get(eq("H0203"))).thenReturn(TestFixture.getNumList());
		when(mockTemplate.get(eq("H1000"))).thenReturn(TestFixture.getDl4ColumnHeaderList());
		when(mockTemplate.get(eq("H1001"))).thenReturn(TestFixture.getDl4H1001List());
		when(mockTemplate.get(eq("H1004"))).thenReturn(TestFixture.getDl4H1004List());
		LithologyDao mockLithologyDao = Mockito.mock(LithologyDao.class);
		TemplateMandatoryHeaderFieldDao mockTemplateMandatoryHeaderFieldDao =
				Mockito.mock(TemplateMandatoryHeaderFieldDao.class);
		TemplateOptionalFieldDao mockTemplateOptionalFieldDao = 
				Mockito.mock(TemplateOptionalFieldDao.class);
		testInstance.setDaos(Arrays.asList(mockLithologyDao,
				mockTemplateMandatoryHeaderFieldDao,
				mockTemplateOptionalFieldDao));
		LithologyUpdater mockLithologyUpdater = Mockito.mock(LithologyUpdater.class);
		PowerMockito.whenNew(LithologyUpdater.class)
			.withArguments(eq(mockLithologyDao), eq(sessionId), eq(mockTemplate))
			.thenReturn(mockLithologyUpdater);
		TemplateOptionalFieldUpdater mockTemplateOptionalFieldUpdater = 
				Mockito.mock(TemplateOptionalFieldUpdater.class);
		PowerMockito.whenNew(TemplateOptionalFieldUpdater.class)
			.withArguments(eq(sessionId), eq(mockTemplate), eq(mockTemplateOptionalFieldDao))
			.thenReturn(mockTemplateOptionalFieldUpdater);
		TemplateHeaderOptionalFieldUpdater mockTemplateHeaderOptionalFieldUpdater = 
				Mockito.mock(TemplateHeaderOptionalFieldUpdater.class);
		PowerMockito.whenNew(TemplateHeaderOptionalFieldUpdater.class)
			.withArguments(eq(sessionId), eq(mockTemplate), 
					eq(mockTemplateMandatoryHeaderFieldDao),
					eq(mockTemplateOptionalFieldDao), Matchers.any(List.class))
			.thenReturn(mockTemplateHeaderOptionalFieldUpdater);
		// When
		testInstance.update(sessionId, mockTemplate);
		// Then
		ArgumentCaptor<List> listCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockLithologyUpdater).init(listCaptor.capture());
		List capturedIndexList = listCaptor.getAllValues();
		assertThat(capturedIndexList.size(), is(equalTo(1)));
		ArgumentCaptor<List> listDataCaptor = ArgumentCaptor.forClass(List.class);
		ArgumentCaptor<Integer> indexCaptor = ArgumentCaptor.forClass(Integer.class);
		verify(mockLithologyUpdater, times(3)).update(listDataCaptor.capture(), indexCaptor.capture());
		List capturedDataList = listDataCaptor.getAllValues();
		assertThat(capturedDataList.size(), is(equalTo(3)));
		List indexList = indexCaptor.getAllValues();
		assertThat(indexList, is(notNullValue()));
		assertThat(indexList.size(), is(equalTo(3)));
		for (int i = 0; i < 3; i++) {
			assertThat(indexList.get(i), is(equalTo(i + 1)));
		}
		verifyNoMoreInteractions(mockLithologyUpdater);
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
		PowerMockito.verifyNew(LithologyUpdater.class)
			.withArguments(eq(mockLithologyDao), eq(sessionId), eq(mockTemplate));
		PowerMockito.verifyNew(TemplateOptionalFieldUpdater.class)
			.withArguments(eq(sessionId), eq(mockTemplate), eq(mockTemplateOptionalFieldDao));
		PowerMockito.verifyNew(TemplateHeaderOptionalFieldUpdater.class)
			.withArguments(eq(sessionId), eq(mockTemplate), 
					eq(mockTemplateMandatoryHeaderFieldDao),
					eq(mockTemplateOptionalFieldDao), Matchers.any(List.class));
		verify(mockTemplateHeaderOptionalFieldUpdater).update();
		PowerMockito.verifyNoMoreInteractions();
	}
	
	@Test
	public void shouldReturnLithologyDao() throws TemplateProcessorException {
		// Given
		testInstance = new Dl4TemplateUpdater();
		LithologyDao mockLithologyDao = Mockito.mock(LithologyDao.class);
		testInstance.setDaos(Arrays.asList(mockLithologyDao));
		// When
		LithologyDao dao = testInstance.getLithologyDao();
		// Then
		assertThat(dao, is(notNullValue()));
		assertThat(dao, is(equalTo(mockLithologyDao)));
	}
	
	@Test
	public void shouldReturnTemplateOptionalFieldDao() throws TemplateProcessorException {
		// Given
		testInstance = new Dl4TemplateUpdater();
		TemplateOptionalFieldDao mockTemplateOptionalFieldDao = Mockito.mock(TemplateOptionalFieldDao.class);
		testInstance.setDaos(Arrays.asList(mockTemplateOptionalFieldDao));
		// When
		TemplateOptionalFieldDao dao = testInstance.getTemplateOptionalFieldDao();
		// Then
		assertThat(dao, is(notNullValue()));
		assertThat(dao, is(equalTo(mockTemplateOptionalFieldDao)));
	}
	
	@Test
	public void shouldReturnListOfDaoClasses() {
		// Given
		testInstance = new Dl4TemplateUpdater();
		// When
		List<Class<? extends Dao>> daos = testInstance.getDaoClasses();
		// Then
		assertThat(daos, is(notNullValue()));
		assertThat(daos.size(), is(equalTo(3)));
		assertThat(daos.get(0), is(equalTo(LithologyDaoImpl.class)));
		assertThat(daos.get(1), is(equalTo(TemplateMandatoryHeaderFieldDaoImpl.class)));
		assertThat(daos.get(2), is(equalTo(TemplateOptionalFieldDaoImpl.class)));
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionWhenLithologyDaoNotFound() throws TemplateProcessorException {
		// Given
		testInstance = new Dl4TemplateUpdater();
		Dao mockDao = Mockito.mock(Dao.class);
		testInstance.setDaos(Arrays.asList(mockDao));
		// When
		testInstance.getLithologyDao();
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionWhenTemplateOptionalFieldDaoNotFound() throws TemplateProcessorException {
		// Given
		testInstance = new Dl4TemplateUpdater();
		Dao mockDao = Mockito.mock(Dao.class);
		testInstance.setDaos(Arrays.asList(mockDao));
		// When
		testInstance.getTemplateOptionalFieldDao();
		fail("Program reached unexpected point!");
	}
}

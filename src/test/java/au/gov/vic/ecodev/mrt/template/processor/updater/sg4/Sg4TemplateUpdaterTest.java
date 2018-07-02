package au.gov.vic.ecodev.mrt.template.processor.updater.sg4;

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
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import au.gov.vic.ecodev.mrt.dao.TemplateOptionalFieldDao;
import au.gov.vic.ecodev.mrt.dao.TemplateOptionalFieldDaoImpl;
import au.gov.vic.ecodev.mrt.dao.sg4.SurfaceGeochemistryDao;
import au.gov.vic.ecodev.mrt.dao.sg4.SurfaceGeochemistryDaoImpl;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.persistent.Dao;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.TemplateOptionalFieldUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.sg4.SurfaceGeochemistryUpdater;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Sg4TemplateUpdater.class })
public class Sg4TemplateUpdaterTest {

	private Sg4TemplateUpdater testInstance;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shouldExecuteUpdateMethod() throws Exception {
		// Given
		givenTestInstance();
		long sessionId = 1l;
		Template mockTemplate = Mockito.mock(Template.class);
		when(mockTemplate.get(eq("H0203"))).thenReturn(TestFixture.getNumList());
		when(mockTemplate.get(eq("H1000"))).thenReturn(TestFixture.getSg4MandatoryColumnsList());
		SurfaceGeochemistryDao mockSurfaceGeochemistryDao = Mockito.mock(SurfaceGeochemistryDao.class);
		TemplateOptionalFieldDao mockTemplateOptionalFieldDao = 
				Mockito.mock(TemplateOptionalFieldDao.class);
		testInstance.setDaos(Arrays.asList(mockSurfaceGeochemistryDao, mockTemplateOptionalFieldDao));
		SurfaceGeochemistryUpdater mockSurfaceGeochemistryUpdater = Mockito.mock(SurfaceGeochemistryUpdater.class);
		PowerMockito.whenNew(SurfaceGeochemistryUpdater.class)
				.withArguments(eq(mockSurfaceGeochemistryDao), eq(sessionId), eq(mockTemplate))
				.thenReturn(mockSurfaceGeochemistryUpdater);
		TemplateOptionalFieldUpdater mockTemplateOptionalFieldUpdater = 
				Mockito.mock(TemplateOptionalFieldUpdater.class);
		PowerMockito.whenNew(TemplateOptionalFieldUpdater.class)
			.withArguments(eq(sessionId), eq(mockTemplate), eq(mockTemplateOptionalFieldDao))
			.thenReturn(mockTemplateOptionalFieldUpdater);
		// When
		testInstance.update(sessionId, mockTemplate);
		// Then
		ArgumentCaptor<List> indexCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockSurfaceGeochemistryUpdater).init(indexCaptor.capture());
		List capturedIndexList = indexCaptor.getAllValues();
		assertThat(capturedIndexList, is(notNullValue()));
		assertThat(capturedIndexList.size(), is(equalTo(1)));
		
		ArgumentCaptor<List> listCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockSurfaceGeochemistryUpdater, times(3)).update(listCaptor.capture());
		List capturedList = listCaptor.getAllValues();
		assertThat(capturedList, is(notNullValue()));
		assertThat(capturedList.size(), is(equalTo(3)));
		
		verifyNoMoreInteractions(mockSurfaceGeochemistryUpdater);
		
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
		
		PowerMockito.verifyNew(SurfaceGeochemistryUpdater.class)
			.withArguments(eq(mockSurfaceGeochemistryDao), eq(sessionId), eq(mockTemplate));
		
		PowerMockito.verifyNew(TemplateOptionalFieldUpdater.class)
			.withArguments(eq(sessionId), eq(mockTemplate), eq(mockTemplateOptionalFieldDao));
		
		PowerMockito.verifyNoMoreInteractions();
	}

	@Test
	public void shouldReturnSurfaceGeochemistryDao() throws TemplateProcessorException {
		// Given
		givenTestInstance();
		SurfaceGeochemistryDao mockSurfaceGeochemistryDao = Mockito.mock(SurfaceGeochemistryDao.class);
		testInstance.setDaos(Arrays.asList(mockSurfaceGeochemistryDao));
		// When
		SurfaceGeochemistryDao dao = testInstance.getSurfaceGeochemistryDao();
		// Then
		assertThat(dao, is(notNullValue()));
		assertThat(dao, is(equalTo(mockSurfaceGeochemistryDao)));
	}

	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionWhenSurfaceGeochemistryDaoNotFound() throws TemplateProcessorException {
		// Given
		givenTestInstance();
		Dao mockDao = Mockito.mock(Dao.class);
		testInstance.setDaos(Arrays.asList(mockDao));
		// When
		testInstance.getSurfaceGeochemistryDao();
		fail("Program reached unexpected point!");
	}

	@Test
	public void shouldReturnListOfDaoClasses() {
		// Given
		givenTestInstance();
		// When
		List<Class<? extends Dao>> clsList = testInstance.getDaoClasses();
		// Then
		assertThat(clsList, is(notNullValue()));
		assertThat(clsList.size(), is(equalTo(2)));
		assertThat(clsList.get(0), is(equalTo(SurfaceGeochemistryDaoImpl.class)));
		assertThat(clsList.get(1), is(equalTo(TemplateOptionalFieldDaoImpl.class)));
	}

	private void givenTestInstance() {
		testInstance = new Sg4TemplateUpdater();
	}
}

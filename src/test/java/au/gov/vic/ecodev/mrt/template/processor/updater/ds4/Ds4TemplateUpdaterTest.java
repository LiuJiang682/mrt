package au.gov.vic.ecodev.mrt.template.processor.updater.ds4;

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

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import au.gov.vic.ecodev.mrt.dao.TemplateOptionalFieldDao;
import au.gov.vic.ecodev.mrt.dao.TemplateOptionalFieldDaoImpl;
import au.gov.vic.ecodev.mrt.dao.ds4.DownHoleDao;
import au.gov.vic.ecodev.mrt.dao.ds4.DownHoleDaoImpl;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.persistent.Dao;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.TemplateHeaderOptionalFieldUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.TemplateOptionalFieldUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.ds4.DownHoleUpdater;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Ds4TemplateUpdater.class })
public class Ds4TemplateUpdaterTest {

	private Ds4TemplateUpdater testInstance;
	private List<Dao> daos;
	private long sessionId;
	private Template mockTemplate;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shouldExecuteUpdateMethod() throws Exception {
		// Given
		givenTestInstance();
		when(mockTemplate.get(eq("H0203"))).thenReturn(TestFixture.getNumList());
		when(mockTemplate.get(eq("H1000"))).thenReturn(TestFixture.getDsMandatoryColumnList());
		when(mockTemplate.get(eq("H1001"))).thenReturn(TestFixture.getDs4H1001List());
		when(mockTemplate.get(eq("H1004"))).thenReturn(TestFixture.getDs4H1004List());
		when(mockTemplate.get(eq("D1"))).thenReturn(TestFixture.getDListWithOptionalFields());
		when(mockTemplate.get(eq("D2"))).thenReturn(TestFixture.getDListWithOptionalFields());
		when(mockTemplate.get(eq("D3"))).thenReturn(TestFixture.getDListWithOptionalFields());
		DownHoleDao mockDownHoleDao = givenMockDownHoleDao();
		TemplateOptionalFieldDao mockTemplateOptionalFieldDao = givenMockTemplateOptionalFieldDao();
		DownHoleUpdater mockDownHoleUpdater = Mockito.mock(DownHoleUpdater.class);
		PowerMockito.whenNew(DownHoleUpdater.class)
			.withArguments(eq(mockDownHoleDao), eq(sessionId), eq(mockTemplate))
			.thenReturn(mockDownHoleUpdater);
		TemplateOptionalFieldUpdater mockTemplateOptionalFieldUpdater = 
				Mockito.mock(TemplateOptionalFieldUpdater.class);
		PowerMockito.whenNew(TemplateOptionalFieldUpdater.class)
			.withArguments(eq(sessionId), eq(mockTemplate), eq(mockTemplateOptionalFieldDao))
			.thenReturn(mockTemplateOptionalFieldUpdater);
		TemplateHeaderOptionalFieldUpdater mockTemplateHeaderOptionalFieldUpdater = 
				Mockito.mock(TemplateHeaderOptionalFieldUpdater.class);
		PowerMockito.whenNew(TemplateHeaderOptionalFieldUpdater.class)
			.withArguments(eq(sessionId), eq(mockTemplate), eq(mockTemplateOptionalFieldDao), Matchers.any(List.class))
			.thenReturn(mockTemplateHeaderOptionalFieldUpdater);
		// When
		testInstance.update(sessionId, mockTemplate);
		// Then
		ArgumentCaptor<List> mandatroyIndexListCaptor = ArgumentCaptor.forClass(List.class);
		ArgumentCaptor<List> mandatoryListCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockDownHoleUpdater).init(mandatroyIndexListCaptor.capture());
		verify(mockDownHoleUpdater, times(3)).update(mandatoryListCaptor.capture());
		ArgumentCaptor<List> listIndexCaptor = ArgumentCaptor.forClass(List.class);
		ArgumentCaptor<List> listCaptor = ArgumentCaptor.forClass(List.class);
		ArgumentCaptor<Integer> intCaptor = ArgumentCaptor.forClass(Integer.class);
		verify(mockTemplateOptionalFieldUpdater).init(listIndexCaptor.capture());
		verify(mockTemplateOptionalFieldUpdater, times(3)).update(listCaptor.capture(), intCaptor.capture());
		List<List> capturedIndexList = mandatroyIndexListCaptor.getAllValues();
		assertThat(capturedIndexList, is(notNullValue()));
		assertThat(capturedIndexList.size(), is(equalTo(1)));
		List<List> capturedMList = mandatoryListCaptor.getAllValues();
		assertThat(capturedMList, is(notNullValue()));
		assertThat(capturedMList.size(), is(equalTo(3)));
		List<List> capturedIList = listIndexCaptor.getAllValues();
		assertThat(capturedIList, is(notNullValue()));
		assertThat(capturedIList.size(), is(equalTo(1)));
		List<List> capturedList = listCaptor.getAllValues();
		assertThat(capturedList, is(notNullValue()));
		assertThat(capturedList.size(), is(equalTo(3)));
		PowerMockito.verifyNew(DownHoleUpdater.class)
			.withArguments(eq(mockDownHoleDao), eq(sessionId), eq(mockTemplate));
		verifyNoMoreInteractions(mockDownHoleUpdater);
		verify(mockTemplateOptionalFieldUpdater).flush();
		verifyNoMoreInteractions(mockTemplateOptionalFieldUpdater);
		PowerMockito.verifyNew(TemplateHeaderOptionalFieldUpdater.class)
			.withArguments(eq(sessionId), eq(mockTemplate), eq(mockTemplateOptionalFieldDao), Matchers.any(List.class));
		verify(mockTemplateHeaderOptionalFieldUpdater).update();
		PowerMockito.verifyNoMoreInteractions();
	}

	@Test
	public void shouldReturnDownHoleDao() throws TemplateProcessorException {
		// Given
		givenTestInstance();
		DownHoleDao mockDownHoleDao = givenMockDownHoleDao();
		// When
		DownHoleDao dao = testInstance.getDownHoleDao();
		// Then
		assertThat(dao, is(notNullValue()));
		assertThat(dao, is(equalTo(mockDownHoleDao)));
	}
	
	@Test
	public void shouldReturnTemplateOptionalFieldDao() throws TemplateProcessorException {
		// Given
		givenTestInstance();
		TemplateOptionalFieldDao mockTemplateOptionalFieldDao = givenMockTemplateOptionalFieldDao();
		// When
		TemplateOptionalFieldDao dao = testInstance.getTemplateOptionalFieldDao();
		// Then
		assertThat(dao, is(notNullValue()));
		assertThat(dao, is(equalTo(mockTemplateOptionalFieldDao)));
	}

	@Test
	public void shouldReturnListOfDaoClasses() {
		// Given
		testInstance = new Ds4TemplateUpdater();
		// When
		List<Class<? extends Dao>> daos = testInstance.getDaoClasses();
		// Then
		assertThat(daos, is(notNullValue()));
		assertThat(daos.size(), is(equalTo(2)));
		assertThat(daos.get(0), is(equalTo(DownHoleDaoImpl.class)));
		assertThat(daos.get(1), is(equalTo(TemplateOptionalFieldDaoImpl.class)));
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionWhenDownHoleDaoNotFound() throws TemplateProcessorException {
		// Given
		givenTestInstance();
		// When
		testInstance.getDownHoleDao();
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionWhenTemplateOptionalFieldDaoNotFound() throws TemplateProcessorException {
		// Given
		givenTestInstance();
		// When
		testInstance.getTemplateOptionalFieldDao();
		fail("Program reached unexpected point!");
	}
	
	private void givenTestInstance() {
		daos = new ArrayList<>();
		daos.add(Mockito.mock(Dao.class));
		testInstance = new Ds4TemplateUpdater();
		testInstance.setDaos(daos);
		sessionId = 1l;
		mockTemplate = Mockito.mock(Template.class);
	}
	
	private DownHoleDao givenMockDownHoleDao() {
		DownHoleDao mockDownHoleDao = Mockito.mock(DownHoleDao.class);
		daos.add(mockDownHoleDao);
		return mockDownHoleDao;
	}
	
	private TemplateOptionalFieldDao givenMockTemplateOptionalFieldDao() {
		TemplateOptionalFieldDao mockTemplateOptionalFieldDao = Mockito.mock(TemplateOptionalFieldDao.class);
		daos.add(mockTemplateOptionalFieldDao);
		return mockTemplateOptionalFieldDao;
	}
}

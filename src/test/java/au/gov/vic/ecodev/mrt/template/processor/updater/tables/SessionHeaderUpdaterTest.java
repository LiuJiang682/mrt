package au.gov.vic.ecodev.mrt.template.processor.updater.tables;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.dao.SessionHeaderDao;
import au.gov.vic.ecodev.mrt.model.SessionHeader;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.persistent.Dao;

public class SessionHeaderUpdaterTest {

	private SessionHeaderUpdater testInstance;
	private Template mockTemplate;
	private SessionHeaderDao mockSessionHeaderDao ;

	@Test
	public void shouldUpdateTheRecord() throws TemplateProcessorException {
		// Given
		List<Dao> daos = givenMockSessionHeaderDao();
		givenTestInstance(daos);
		SessionHeader mockSessionHeader = Mockito.mock(SessionHeader.class);
		when(mockSessionHeaderDao.get(Matchers.anyLong())).thenReturn(mockSessionHeader);
		List<String> h0100List = getH0100List();
		when(mockTemplate.get(eq("H0100"))).thenReturn(h0100List);
		List<String> h0101List = getH0101List();
		when(mockTemplate.get("H0101")).thenReturn(h0101List);
		when(mockTemplate.get("H0003")).thenReturn(getH0003List());
		when(mockTemplate.get("H0102")).thenReturn(getH0102List());
		//when
		testInstance.update();
		//Then
		ArgumentCaptor<SessionHeader> sessionHeaderCaptor = ArgumentCaptor.forClass(SessionHeader.class);
		verify(mockSessionHeaderDao).updateOrSave(sessionHeaderCaptor.capture());
		SessionHeader capturedSessionHeader = sessionHeaderCaptor.getValue();
		assertThat(capturedSessionHeader, is(equalTo(mockSessionHeader)));
		
		ArgumentCaptor<String> tenementCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockSessionHeader).setTenement(tenementCaptor.capture());
		String tenement = tenementCaptor.getValue();
		assertThat(tenement, is(equalTo("123")));
		
		ArgumentCaptor<String> tenementHolderCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockSessionHeader).setTenementHolder(tenementHolderCaptor.capture());
		String tenementHolder = tenementHolderCaptor.getValue();
		assertThat(tenementHolder, is(equalTo("456")));
		
		ArgumentCaptor<Date> reportingDateCaptor = ArgumentCaptor.forClass(Date.class);
		verify(mockSessionHeader).setReportingDate(reportingDateCaptor.capture());
		Date reportingDate = reportingDateCaptor.getValue();
		assertThat(reportingDate, is(notNullValue()));
		Calendar c = Calendar.getInstance();
		c.setTime(reportingDate);
		assertThat(c.get(Calendar.YEAR), is(equalTo(2012)));
		assertThat(c.get(Calendar.MONTH), is(equalTo(Calendar.NOVEMBER)));
		assertThat(c.get(Calendar.DAY_OF_MONTH), is(equalTo(12)));
		
		ArgumentCaptor<String> projectCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockSessionHeader).setProjectName(projectCaptor.capture());
		String project = projectCaptor.getValue();
		assertThat(project, is(equalTo("myProject")));
	}

	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionWhenNoSessionHeaderDaoFound() throws TemplateProcessorException {
		// Given
		List<Dao> daos = new ArrayList<>();
		daos.add(Mockito.mock(Dao.class));
		givenTestInstance(daos);
		// When
		testInstance.getSessionHeaderDao();
		fail("Program reached unexpected point!");
	}

	@Test
	public void shouldReturnSessionHeaderDao() throws TemplateProcessorException {
		List<Dao> daos = givenMockSessionHeaderDao();
		givenTestInstance(daos);
		// When
		SessionHeaderDao sessionHeaderDao = testInstance.getSessionHeaderDao();
		// Then
		assertThat(sessionHeaderDao, is(equalTo(mockSessionHeaderDao)));
	}

	@Test
	public void shouldReturnInstance() {
		// Given
		givenTestInstance(getDaos());
		// When
		// Then
		assertThat(testInstance, is(notNullValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenDaoListIsNull() {
		// Given
		List<Dao> daos = null;
		Template template = null;
		// When
		new SessionHeaderUpdater(daos, 0L, template);
		fail("Program reached unexpected point!");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenTemplateIsNull() {
		// Given
		List<Dao> daos = getDaos();
		Template template = null;
		// When
		new SessionHeaderUpdater(daos, 0L, template);
		fail("Program reached unexpected point!");
	}

	private List<Dao> getDaos() {
		List<Dao> daos = new ArrayList<>();
		daos.add(Mockito.mock(Dao.class));
		return daos;
	}

	private void givenTestInstance(List<Dao> daos) {
		mockTemplate = Mockito.mock(Template.class);

		testInstance = new SessionHeaderUpdater(daos, 1l, mockTemplate);
	}
	
	private List<Dao> givenMockSessionHeaderDao() {
		List<Dao> daos = getDaos();
		mockSessionHeaderDao = Mockito.mock(SessionHeaderDao.class);
		daos.add((Dao) mockSessionHeaderDao);
		return daos;
	}
	
	private List<String> getH0100List() {
		List<String> h0100List = new ArrayList<>();
		h0100List.add("abc");
		h0100List.add("123");
		return h0100List;
	}
	
	private List<String> getH0101List() {
		List<String> h0101List = new ArrayList<>();
		h0101List.add("def");
		h0101List.add("456");
		return h0101List;
	}
	
	private List<String> getH0003List() {
		List<String> h0003List = new ArrayList<>();
		h0003List.add("ghi");
		h0003List.add("12-NOV-12");
		return h0003List;
	}
	
	private List<String> getH0102List() {
		List<String> h0102List = new ArrayList<>();
		h0102List.add("def");
		h0102List.add("myProject");
		return h0102List;
	}
}

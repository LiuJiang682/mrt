package au.gov.vic.ecodev.mrt.data.record.cleaner;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.data.record.cleaner.persistent.PersistentService;
import au.gov.vic.ecodev.mrt.data.record.cleaner.persistent.model.Session;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;

public class DbSessionCleanerTest {

	private DbSessionCleaner testInstance;
	private Session session;
	private PersistentService mockPersistentServices;
	private Map<String, List<String>> templateClassListMap;
	
	@Test
	public void shouldCleanTheSession() {
		//Given
		givenTestInstance();
		when(mockPersistentServices.getDisplayProperties(Matchers.anyString()))
			.thenReturn(TestFixture.MRT_DISPLAY_PROPERTIES_JSON);
		//When
		testInstance.clean();
		//Then
		ArgumentCaptor<String> templateCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockPersistentServices).getDisplayProperties(templateCaptor.capture());
		List<String> captured = templateCaptor.getAllValues();
		assertThat(captured, is(notNullValue()));
		assertThat(captured.size(), is(equalTo(1)));
		assertThat(captured.get(0), is(equalTo("MRT")));
		ArgumentCaptor<String> classCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Long> sessionCaptor = ArgumentCaptor.forClass(Long.class);
		verify(mockPersistentServices, times(6)).deleteByTableNameAndSessionId(classCaptor.capture(), sessionCaptor.capture());
		List<String> capturedClass = classCaptor.getAllValues();
		assertThat(capturedClass, is(notNullValue()));
		assertThat(capturedClass.size(), is(equalTo(6)));
		List<Long> capturedSessionIds = sessionCaptor.getAllValues();
		assertThat(capturedSessionIds, is(notNullValue()));
		assertThat(capturedSessionIds.size(), is(equalTo(6)));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		givenTestInstance();
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenSessionIsNull() {
		//Given 
		session = null;
		mockPersistentServices = null;
		templateClassListMap = null;
		//When
		new DbSessionCleaner(session, mockPersistentServices, templateClassListMap);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenPersistentServiceIsNull() {
		//Given 
		session = TestFixture.getMrtSession();
		mockPersistentServices = null;
		templateClassListMap = null;
		//When
		new DbSessionCleaner(session, mockPersistentServices, templateClassListMap);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenTemplateClassListMapIsNull() {
		//Given 
		session = TestFixture.getMrtSession();
		mockPersistentServices = Mockito.mock(PersistentService.class);
		templateClassListMap = null;
		//When
		new DbSessionCleaner(session, mockPersistentServices, templateClassListMap);
		fail("Program reached unexpected point!");
	}

	private void givenTestInstance() {
		session = TestFixture.getMrtSession();
		mockPersistentServices = Mockito.mock(PersistentService.class);
		templateClassListMap = new HashMap<>();
		testInstance = new DbSessionCleaner(session, mockPersistentServices, templateClassListMap);
	}
}

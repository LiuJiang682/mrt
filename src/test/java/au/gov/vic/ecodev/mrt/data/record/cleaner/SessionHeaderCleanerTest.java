package au.gov.vic.ecodev.mrt.data.record.cleaner;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.data.record.cleaner.persistent.PersistentService;
import au.gov.vic.ecodev.mrt.data.record.cleaner.persistent.model.Session;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;


public class SessionHeaderCleanerTest {

	private SessionHeaderCleaner testInstance;
	private PersistentService mockPersistentService;
	
	@Test
	public void shouldCleanTheSessionHeader() {
		//Given
		givenTestInstance();
		//When
		testInstance.clean();
		//Then
		ArgumentCaptor<Long> sessionIdCaptor = ArgumentCaptor.forClass(Long.class);
		verify(mockPersistentService).deleteSessionHeaderById(sessionIdCaptor.capture());
		List<Long> sessionIds = sessionIdCaptor.getAllValues();
		assertThat(sessionIds, is(notNullValue()));
		assertThat(sessionIds.size(), is(equalTo(1)));
		assertThat(sessionIds.get(0), is(equalTo(100l)));
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
		Session session = null;
		mockPersistentService = null;
		//When
		new SessionHeaderCleaner(session, mockPersistentService);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenPersistentServiceIsNull() {
		//Given
		Session session = TestFixture.getMrtSession();
		mockPersistentService = null;
		//When
		new SessionHeaderCleaner(session, mockPersistentService);
		fail("Program reached unexpected point!");
	}

	private void givenTestInstance() {
		mockPersistentService = Mockito.mock(PersistentService.class);
		testInstance = new SessionHeaderCleaner(TestFixture.getMrtSession(), 
				mockPersistentService);
	}
}

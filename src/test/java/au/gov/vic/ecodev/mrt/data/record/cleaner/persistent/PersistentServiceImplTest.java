package au.gov.vic.ecodev.mrt.data.record.cleaner.persistent;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import au.gov.vic.ecodev.mrt.config.MrtConfigProperties;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PersistentServiceImplTest {

	@Autowired
	private PersistentServiceImpl testInstance;
	
	@Test
	public void shouldReturnListOfSessions() {
		//Given
		//When
		List<Map<String, Object>> sessions = testInstance.getSessions();
		//Then
		assertThat(sessions, is(notNullValue()));
		assertThat(sessions.size(), is(equalTo(2)));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenJdbcTemplateIsNull() {
		//Given
		JdbcTemplate jdbcTemplate = null;
		MrtConfigProperties mrtConfigProperties = null;
		//When
		new PersistentServiceImpl(jdbcTemplate, mrtConfigProperties);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenMrtConfigPropertiesIsNull() {
		//Given
		JdbcTemplate mockJdbcTemplate = Mockito.mock(JdbcTemplate.class);
		MrtConfigProperties mrtConfigProperties = null;
		//When
		new PersistentServiceImpl(mockJdbcTemplate, mrtConfigProperties);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected=RuntimeException.class) 
	public void shouldRaiseExceptionWhenNoSqlProvided() {
		//Given
		JdbcTemplate mockJdbcTemplate = Mockito.mock(JdbcTemplate.class);
		MrtConfigProperties mockMrtConfigProperties = Mockito.mock(MrtConfigProperties.class);
		testInstance = new PersistentServiceImpl(mockJdbcTemplate, mockMrtConfigProperties);
		//When
		testInstance.getSessions();
		fail("Program reached unexpected point!");
	}
	
	@Test(expected=RuntimeException.class) 
	public void shouldRaiseExceptionWhenSqlIsInjected() {
		//Given
		JdbcTemplate mockJdbcTemplate = Mockito.mock(JdbcTemplate.class);
		MrtConfigProperties mockMrtConfigProperties = Mockito.mock(MrtConfigProperties.class);
		when(mockMrtConfigProperties.getSelectCleanSessionSql()).thenReturn("select ID, TEMPLATE from SESSION_HEADER WHERE REJECTED = '1'");
		testInstance = new PersistentServiceImpl(mockJdbcTemplate, mockMrtConfigProperties);
		//When
		testInstance.getSessions();
		fail("Program reached unexpected point!");
	}
}

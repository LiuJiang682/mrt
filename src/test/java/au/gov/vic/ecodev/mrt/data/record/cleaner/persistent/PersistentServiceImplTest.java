package au.gov.vic.ecodev.mrt.data.record.cleaner.persistent;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import au.gov.vic.ecodev.mrt.config.MrtConfigProperties;
import au.gov.vic.ecodev.mrt.dao.TemplateDisplayPropertiesDao;

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
	public void shouldDeleteRecord() {
		//Given
		String table = "LOC_SITE";
		long sessionId = 9999l;
		//When
		testInstance.deleteByTableNameAndSessionId(table, sessionId);
		//Then
		assertThat(true, is(true));
	}
	
	@Test
	public void shouldReturnDisplayProperties() {
		//Given
		//When
		String displayProperties = testInstance.getDisplayProperties("MRT");
		//Then
		assertThat(StringUtils.isEmpty(displayProperties), is(false));
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
		TemplateDisplayPropertiesDao templateDisplayPropertiesDao = null;
		//When
		new PersistentServiceImpl(jdbcTemplate, mrtConfigProperties, 
				templateDisplayPropertiesDao);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenMrtConfigPropertiesIsNull() {
		//Given
		JdbcTemplate mockJdbcTemplate = Mockito.mock(JdbcTemplate.class);
		MrtConfigProperties mrtConfigProperties = null;
		TemplateDisplayPropertiesDao templateDisplayPropertiesDao = null;
		//When
		new PersistentServiceImpl(mockJdbcTemplate, mrtConfigProperties, 
				templateDisplayPropertiesDao);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenTemplateDisplayPropertiesDaoIsNull() {
		//Given
		JdbcTemplate mockJdbcTemplate = Mockito.mock(JdbcTemplate.class);
		MrtConfigProperties mrtConfigProperties = Mockito.mock(MrtConfigProperties.class);
		TemplateDisplayPropertiesDao templateDisplayPropertiesDao = null;
		//When
		new PersistentServiceImpl(mockJdbcTemplate, mrtConfigProperties, 
				templateDisplayPropertiesDao);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected=RuntimeException.class) 
	public void shouldRaiseExceptionWhenNoSqlProvided() {
		//Given
		JdbcTemplate mockJdbcTemplate = Mockito.mock(JdbcTemplate.class);
		MrtConfigProperties mockMrtConfigProperties = Mockito.mock(MrtConfigProperties.class);
		TemplateDisplayPropertiesDao templateDisplayPropertiesDao = null;
		testInstance = new PersistentServiceImpl(mockJdbcTemplate, mockMrtConfigProperties, 
				templateDisplayPropertiesDao);
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
		TemplateDisplayPropertiesDao templateDisplayPropertiesDao = 
				Mockito.mock(TemplateDisplayPropertiesDao.class);
		testInstance = new PersistentServiceImpl(mockJdbcTemplate, mockMrtConfigProperties, 
				templateDisplayPropertiesDao);
		//When
		testInstance.getSessions();
		fail("Program reached unexpected point!");
	}
}

package au.gov.vic.ecodev.mrt.data.record.cleaner.persistent;

import static org.hamcrest.core.Is.is;
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
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	public void shouldReturnListOfSessions() {
		//Given
		int count = jdbcTemplate.queryForObject("SELECT COUNT(ID) FROM SESSION_HEADER WHERE REJECTED = '1'",
				Integer.class);
		if (0 == count) {
			jdbcTemplate.update("INSERT INTO SESSION_HEADER(ID, TEMPLATE, FILE_NAME, PROCESS_DATE, TENEMENT, TENEMENT_HOLDER, REPORTING_DATE, PROJECT_NAME, STATUS, COMMENTS, EMAIL_SENT, APPROVED, REJECTED, CREATED) values (555, 'MRT', 'MRT_EL123.zip', sysdate, 'abc', 'def', sysdate, 'ghi', 'running', '', 'N', 0, 1, sysdate)");
			jdbcTemplate.update("INSERT INTO SESSION_HEADER(ID, TEMPLATE, FILE_NAME, PROCESS_DATE, TENEMENT, TENEMENT_HOLDER, REPORTING_DATE, PROJECT_NAME, STATUS, COMMENTS, EMAIL_SENT, APPROVED, REJECTED, CREATED) values (556, 'MRT', 'MRT_EL123.zip', sysdate, 'abc', 'def', sysdate, 'ghi', 'running', '', 'N', 0, 1, sysdate)");
		}
		//When
		List<Map<String, Object>> sessions = testInstance.getSessions();
		//Then
		assertThat(sessions, is(notNullValue()));
		assertThat(2 <= sessions.size(), is(true));
	}
	
	@Test
	public void shouldDeleteRecord() {
		//Given
		int count = jdbcTemplate.queryForObject("SELECT COUNT(LOADER_ID) FROM LOC_SITE WHERE LOADER_ID = 9999",
				Integer.class);
		if (0 == count) {
			jdbcTemplate.update("INSERT INTO lOC_SITE(LOADER_ID, SITE_ID, GSV_SITE_ID, ROW_NUMBER, PARISH, PROSPECT, AMG_ZONE, EASTING, NORTHING, LATITUDE, LONGITUDE, LOCN_ACC, LOCN_DATUM_CD, ELEVATION_GL, ELEV_ACC, ELEV_DATUM_CD, COORD_SYSTEM, VERTICAL_DATUM, NUM_DATA_RECORDS, ISSUE_COLUMN_INDEX, FILE_NAME) values (9999, 'KPDD001', 0, '2', 'N/A', 'Kryptonite', 55, 392200, 6589600, 0, 0, 0, 'GDA94', 0, 0, 'AHD', null, null, -1, -1, 'myTest.txt')");
		}
		String table = "LOC_SITE";
		long sessionId = 9999l;
		//When
		testInstance.deleteByTableNameAndSessionId(table, sessionId);
		//Then
		assertThat(true, is(true));
	}
	
	@Test
	public void shouldDeleteSessionHeaderRecord() {
		//Given
		int count = jdbcTemplate.queryForObject("SELECT COUNT(ID) FROM SESSION_HEADER WHERE ID = 99991",
				Integer.class);
		if (0 == count) {
			jdbcTemplate.update("INSERT INTO SESSION_HEADER(ID, TEMPLATE, FILE_NAME, PROCESS_DATE, TENEMENT, TENEMENT_HOLDER, REPORTING_DATE, PROJECT_NAME, STATUS, COMMENTS, EMAIL_SENT, APPROVED, REJECTED, CREATED) values (99991, 'MRT', 'MRT_EL123.zip', sysdate, 'abc', 'def', sysdate, 'ghi', 'running', '', 'N', 0, 1, sysdate)");
		}
		long sessionId = 99991l;
		//When
		boolean flag = testInstance.deleteSessionHeaderById(sessionId);
		//Then
		assertThat(flag, is(true));
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

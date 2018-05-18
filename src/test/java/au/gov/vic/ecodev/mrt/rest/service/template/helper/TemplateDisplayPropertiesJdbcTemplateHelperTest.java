package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TemplateDisplayPropertiesJdbcTemplateHelperTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	public void shouldReturnList() {
		//Given
		String template = "LOC_SITE";
		String fields = "SITE_ID,EASTING,NORTHING,LATITUDE,LONGITUDE";
		long batchId = 1l;
		TemplateDisplayPropertiesJdbcTemplateHelper testInstance = new TemplateDisplayPropertiesJdbcTemplateHelper(jdbcTemplate);
		//When
		List<Map<String, Object>> results = testInstance.getList(template, fields, batchId);
		//Then
		assertThat(results, is(notNullValue()));
		assertThat(results.size(), is(equalTo(1)));
		Map<String, Object> dataMap = results.get(0);
		assertThat(dataMap.size(), is(equalTo(5)));
		assertThat(dataMap.get("SITE_ID"), is(equalTo("KPDD001")));
		assertThat(dataMap.get("EASTING"), is(equalTo(new BigDecimal("392200"))));
		assertThat(dataMap.get("NORTHING"), is(equalTo(new BigDecimal("6589600"))));
		assertThat(dataMap.get("LATITUDE"), is(equalTo(BigDecimal.ZERO)));
		assertThat(dataMap.get("LONGITUDE"), is(equalTo(BigDecimal.ZERO)));
	}
	
	@Test
	public void shouldReturnListWithSqlTag() {
		//Given
		String template = "DH_BOREHOLE";
		String fields = "DRILL_TYPE,DEPTH,ELEVATION_KB,AZIMUTH_MAG-SQL:SELECT b.DRILL_TYPE, a.DEPTH, a.ELEVATION_KB, a.AZIMUTH_MAG FROM DH_BOREHOLE a, DH_DRILLING_DETAILS b where a.DILLING_DETAILS_ID = b.ID AND a.LOADER_ID = ?";
		long batchId = 1l;
		TemplateDisplayPropertiesJdbcTemplateHelper testInstance = new TemplateDisplayPropertiesJdbcTemplateHelper(jdbcTemplate);
		//When
		List<Map<String, Object>> results = testInstance.getList(template, fields, batchId);
		//Then
		assertThat(results, is(notNullValue()));
		assertThat(results.size(), is(equalTo(1)));
		Map<String, Object> dataMap = results.get(0);
		assertThat(dataMap.size(), is(equalTo(4)));
		assertThat(dataMap.get("DRILL_TYPE"), is(equalTo("DD")));
		assertThat(dataMap.get("DEPTH"), is(equalTo(new BigDecimal("210"))));
		assertThat(dataMap.get("ELEVATION_KB"), is(equalTo(new BigDecimal("320"))));
		assertThat(dataMap.get("AZIMUTH_MAG"), is(nullValue()));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		//When
		TemplateDisplayPropertiesJdbcTemplateHelper testInstance = new TemplateDisplayPropertiesJdbcTemplateHelper(jdbcTemplate);
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenJdbcTemplateIsNull() {
		//Given
		JdbcTemplate jdbcTemplate = null;
		//When
		new TemplateDisplayPropertiesJdbcTemplateHelper(jdbcTemplate);
		fail("Program reached unexpected point!");
	}
}

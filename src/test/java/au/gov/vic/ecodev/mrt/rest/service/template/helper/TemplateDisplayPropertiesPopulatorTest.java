package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TemplateDisplayPropertiesPopulatorTest {

	private static final String CLASS_FIELD_JSON = "{\"SL4\":[{\"LOC_SITE\":\"SITE_ID,EASTING,NORTHING,LATITUDE,LONGITUDE\"},{\"DH_BOREHOLE\":\"DRILL_TYPE,DEPTH,ELEVATION_KB,AZIMUTH_MAG-SQL:SELECT b.DRILL_TYPE, a.DEPTH, a.ELEVATION_KB, a.AZIMUTH_MAG FROM DH_BOREHOLE a, DH_DRILLING_DETAILS b where a.DILLING_DETAILS_ID = b.ID AND a.LOADER_ID = ?\"}],\"DS4\":[{\"DH_DOWNHOLE\":\"SURVEYED_DEPTH,AZIMUTH_MAG,DIP\"}]}";
//	private static final String CLASS_FIELD_JSON = "{\"SL4\":[{\"LOC_SITE\":\"SITE_ID,EASTING,NORTHING,LATITUDE,LONGITUDE\"},{\"DH_BOREHOLE\":\"DILLING_DETAILS_ID,DEPTH,ELEVATION_KB,AZIMUTH_MAG\"}],\"DS4\":[{\"DH_DOWNHOLE\":\"SURVEYED_DEPTH,AZIMUTH_MAG,DIP\"}]}";
	
	@Autowired
	private TemplateDisplayPropertiesPopulator testInstance;
	
	@Test
	public void shouldReturnSl4DisplayProperties() throws Exception {
		//Given
		Map<String, List<Map<String, Object>>> resultMap = new HashMap<>();
		List<String> classesList = Arrays.asList("SL4");
		Map<String, Object> classFieldMap = getClassFieldMap();
		//When
		testInstance.doPopulation(resultMap, classesList, classFieldMap, 1l);
		//Then
		assertThat(resultMap.isEmpty(), is(false));
		assertThat(resultMap.size(), is(equalTo(2)));
		assertData(resultMap, "SL4_D1");
	}
	
	@Test
	public void shouldReturn2Sl4DisplayProperties() throws Exception {
		//Given
		Map<String, List<Map<String, Object>>> resultMap = new HashMap<>();
		List<String> classesList = Arrays.asList("SL4");
		Map<String, Object> classFieldMap = getClassFieldMap();
		//When
		testInstance.doPopulation(resultMap, classesList, classFieldMap, 12l);
		//Then
		assertThat(resultMap.isEmpty(), is(false));
		assertThat(resultMap.size(), is(equalTo(3)));
		assertData(resultMap, "SL4_D1");
		assertData(resultMap, "SL4_D2");
	}
	
//	@Ignore
	@Test
	public void shouldReturnSl4Ds4DisplayProperties() throws Exception {
		//Given
		Map<String, List<Map<String, Object>>> resultMap = new HashMap<>();
		List<String> classesList = Arrays.asList("SL4", "DS4");
		Map<String, Object> classFieldMap = getClassFieldMap();
		//When
		testInstance.doPopulation(resultMap, classesList, classFieldMap, 1l);
		//Then
		assertThat(resultMap.isEmpty(), is(false));
		assertThat(resultMap.size(), is(equalTo(4)));
		assertThat(resultMap.keySet().contains("SL4_D1"), is(true));
		assertThat(CollectionUtils.isEmpty(resultMap.get("SL4_D1")), is(false));
		assertThat(resultMap.keySet().contains("DS4_D1"), is(true));
		assertThat(CollectionUtils.isEmpty(resultMap.get("DS4_D1")), is(false));
	}

	@Test
	public void shouldReturnInstance() {
		//Given
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenDaoIsNull() {
		//Given
		JdbcTemplate jdbcTemplate = null;
		//When
		new TemplateDisplayPropertiesPopulator(jdbcTemplate);
		fail("Program reached unexpected point!");
	}
	
	@SuppressWarnings("unchecked")
	private static Map<String, Object> getClassFieldMap() throws Exception {
		return new ObjectMapper().readValue(CLASS_FIELD_JSON, HashMap.class);
	}
	
	private void assertData(Map<String, List<Map<String, Object>>> resultMap, String key) {
		assertThat(resultMap.keySet().contains(key), is(true));
		List<Map<String, Object>> data = resultMap.get(key);
		assertThat(CollectionUtils.isEmpty(data), is(false));
		assertThat(data.size(), is(equalTo(1)));
		Map<String, Object> dataMap = data.get(0);
		assertThat(MapUtils.isEmpty(dataMap), is(false));
		assertThat(dataMap.size(), is(equalTo(10)));
	}
}

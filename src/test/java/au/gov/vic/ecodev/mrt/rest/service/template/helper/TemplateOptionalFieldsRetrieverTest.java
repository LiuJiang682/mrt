package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TemplateOptionalFieldsRetrieverTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private TemplateOptionalFieldsRetriever testInstance;
	
	@Test
	public void shouldReturnOptionalFields() {
		//Given
		givenTestInstance();
		Map<String, List<Map<String, Object>>> resultMap = new HashMap<>();
		long batchId = 1l;
		String template = "SL4";
		//When
		testInstance.extractOptionalFields(resultMap, batchId, template);
		//Then
		assertThat(MapUtils.isNotEmpty(resultMap), is(true));
		assertThat(resultMap.size(), is(equalTo(1)));
		List<Map<String, Object>> list = resultMap.get(template + "_D1");
		assertThat(CollectionUtils.isEmpty(list), is(false));
		assertThat(list.size(), is(equalTo(1)));
		Map<String, Object> optionalFields = list.get(0);
		assertThat(MapUtils.isNotEmpty(optionalFields), is(true));
		assertThat(optionalFields.size(), is(equalTo(1)));
		assertThat(optionalFields.keySet().iterator().next(), is(equalTo("Au")));
		assertThat(optionalFields.values().iterator().next(), is(equalTo("0.01")));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		givenTestInstance();
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenJdbcTemplateIsNull() {
		//Given
		JdbcTemplate jdbcTemplate = null;
		//When
		new TemplateOptionalFieldsRetriever(jdbcTemplate);
		fail("Program reached unexpected point!");
	}

	private void givenTestInstance() {
		testInstance = new TemplateOptionalFieldsRetriever(jdbcTemplate);
	}
}

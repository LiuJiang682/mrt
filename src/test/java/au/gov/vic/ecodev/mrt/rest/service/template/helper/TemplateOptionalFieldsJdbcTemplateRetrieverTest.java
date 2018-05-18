package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TemplateOptionalFieldsJdbcTemplateRetrieverTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private TemplateOptionalFieldsJdbcTemplateRetriever testInstance;
	
	@Test
	public void shouldReturnListOfOptionalFields() {
		//Given
		givenTestInstance();
		String template = "SL4";
		long batchId = 1l;
		//When
		List<Map<String, Object>> optionalFields = testInstance.getList(template, batchId);
		//Then
		assertThat(CollectionUtils.isEmpty(optionalFields), is(false));
	}
	
	@Test
	public void shouldReturnListOfHeaders() {
		//Given
		givenTestInstance();
		String template = "SL4";
		long batchId = 100L;
		//When
		String headers = testInstance.getHeaders(template, batchId);
		//Then
		assertThat(StringUtils.isEmpty(headers), is(false));
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
		new TemplateOptionalFieldsJdbcTemplateRetriever(jdbcTemplate);
		fail("Program reached unexpected point!");
	}

	private void givenTestInstance() {
		this.testInstance = new TemplateOptionalFieldsJdbcTemplateRetriever(jdbcTemplate);
	}
}

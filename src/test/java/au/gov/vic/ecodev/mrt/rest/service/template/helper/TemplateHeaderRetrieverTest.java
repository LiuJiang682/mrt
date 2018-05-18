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

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TemplateHeaderRetrieverTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private TemplateHeaderRetriever testInstance;
	
	@Test
	public void shouldReturnTemplateHeaders() {
		//Given
		givenTestInstance();
		Map<String, List<Map<String, Object>>> resultMap = new HashMap<>();
		long batchId = 100l;
		String template = "SL4";
		//When
		testInstance.extractTemplateHeaders(resultMap, batchId, template);
		//Then
		assertThat(MapUtils.isNotEmpty(resultMap), is(true));
		assertThat(resultMap.size(), is(equalTo(1)));
		List<Map<String, Object>> headerList = resultMap.get(template + "_Headers");
		assertThat(CollectionUtils.isEmpty(headerList), is(false));
		assertThat(headerList.size(), is(equalTo(1)));
		Map<String, Object> headers = headerList.get(Numeral.ZERO);
		assertThat(MapUtils.isNotEmpty(headers), is(true));
		assertThat(headers.size(), is(equalTo(17)));
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
		//when
		new TemplateHeaderRetriever(jdbcTemplate);
		fail("Program reached unexpected point!");
	}

	private void givenTestInstance() {
		testInstance = new TemplateHeaderRetriever(jdbcTemplate);
	}
}

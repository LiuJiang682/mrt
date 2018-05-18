package au.gov.vic.ecodev.mrt.template.processor.file.validator.sg4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.model.sg4.Sg4Template;

public class H1003ValidatorTest {

	private H1003Validator testInstance;

	@Test
	public void shouldReturnEmptyMessageWhenH1001ValueProvided() {
		// Given
		String[] strs = TestFixture.getDg4H1003Data();
		testInstance = new H1003Validator();
		testInstance.init(strs);
		Map<String, List<String>> templateParamMap = new HashMap<>();
		templateParamMap.put(Strings.TEMPLATE_PROP_SG4_H1001_MANDATORY_FIELDS_HEADER,
				TestFixture.getDg4H1001MadatoryFieldHeadersList());
		templateParamMap.put(Strings.COLUMN_HEADERS, TestFixture.getDg4FullColumnHeaderList());
		Template dataBean = new Sg4Template();
		// When
		Optional<List<String>> resultOptional = testInstance.validate(templateParamMap, dataBean);
		assertThat(resultOptional.isPresent(), is(false));
	}
	
	@Test
	public void shouldReturnEmptyMessageWhenDataIsNull() {
		// Given
		String[] strs = null;
		testInstance = new H1003Validator();
		testInstance.init(strs);
		Map<String, List<String>> templateParamMap = new HashMap<>();
		templateParamMap.put(Strings.TEMPLATE_PROP_SG4_H1001_MANDATORY_FIELDS_HEADER,
				TestFixture.getDg4H1001MadatoryFieldHeadersList());
		templateParamMap.put(Strings.COLUMN_HEADERS, TestFixture.getDg4FullColumnHeaderList());
		Template dataBean = new Sg4Template();
		// When
		Optional<List<String>> resultOptional = testInstance.validate(templateParamMap, dataBean);
		assertThat(resultOptional.isPresent(), is(false));
	}

	@Test
	public void shouldReturnMessageWhenH1001ValueIsNotProvided() {
		// Given
		String[] strs = { "H1001", null, null, null, null, null, null, null, "ppm", "ppm", "ppm", "ppm" };
		testInstance = new H1003Validator();
		testInstance.init(strs);
		Map<String, List<String>> templateParamMap = new HashMap<>();
		templateParamMap.put(Strings.TEMPLATE_PROP_SG4_H1001_MANDATORY_FIELDS_HEADER,
				TestFixture.getDg4H1001MadatoryFieldHeadersList());
		templateParamMap.put(Strings.COLUMN_HEADERS, TestFixture.getDg4FullColumnHeaderList());
		Template dataBean = new Sg4Template();
		// When
		Optional<List<String>> resultOptional = testInstance.validate(templateParamMap, dataBean);
		assertThat(resultOptional.isPresent(), is(true));
		List<String> messages = resultOptional.get();
		assertThat(messages, is(notNullValue()));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Au requires value in H1003")));
	}
}

package au.gov.vic.ecodev.mrt.template.processor.file.validator.sg4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0800Validator;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.model.sg4.Sg4Template;

public class H1002ValidatorTest {

	@Test
	public void shouldReturnEmptyMessageWhenH1002ValueExistInH0800() {
		//Given
		H1002Validator testInstance = givenTestInstance();
		Map<String, List<String>> templateParamMap = new HashMap<>();
		templateParamMap.put(Strings.COLUMN_HEADERS, TestFixture.getDg4FullColumnHeaderList());
		templateParamMap.put(Strings.TITLE_PREFIX + H0800Validator.ASSAY_CODE_TITLE,
				Arrays.asList("BLEG", "ICP-OES", "AR"));
		Template dataBean = new Sg4Template();
		//When
		Optional<List<String>> errorMessages = testInstance.validate(templateParamMap, dataBean);
		//Then
		assertThat(errorMessages.isPresent(), is(false));
		List<String> h1002List = dataBean.get("H1002");
		assertThat(h1002List, is(notNullValue()));
		assertThat(h1002List.size(), is(equalTo(17)));
	}
	
	@Test
	public void shouldReturnAssayCodeNotFoundMessageWhenH1002ValueNotExistInH0800() {
		H1002Validator testInstance = givenTestInstance();
		Map<String, List<String>> templateParamMap = new HashMap<>();
		templateParamMap.put(Strings.COLUMN_HEADERS, TestFixture.getDg4FullColumnHeaderList());
		templateParamMap.put(Strings.TITLE_PREFIX + H0800Validator.ASSAY_CODE_TITLE,
				Arrays.asList("BLEG", "ICP-OES"));
		Template dataBean = new Sg4Template();
		//When
		Optional<List<String>> errorMessages = testInstance.validate(templateParamMap, dataBean);
		//Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: AR is NOT included in H0800")));
		List<String> h1002List = dataBean.get("H1002");
		assertThat(h1002List, is(nullValue()));
	}
	
	@Test
	public void shouldReturnNoAssayCodeFromTemplateParamMapMessageWhenH0800IsNotProvided() {
		//Given
		H1002Validator testInstance = givenTestInstance();
		Map<String, List<String>> templateParamMap = new HashMap<>();
		Template dataBean = new Sg4Template();
		//When
		Optional<List<String>> errorMessages = testInstance.validate(templateParamMap, dataBean);
		//Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: No H0800 data provided")));
		List<String> h1002List = dataBean.get("H1002");
		assertThat(h1002List, is(nullValue()));
	}
	
	@Test
	public void shouldReturnEmptyMessageWhenH1002ValueIsNull() {
		//Given
		String[] strs = null;
		H1002Validator testInstance = new H1002Validator();
		testInstance.init(strs);
		Map<String, List<String>> templateParamMap = new HashMap<>();
		templateParamMap.put(Strings.TITLE_PREFIX + H0800Validator.ASSAY_CODE_TITLE,
				Arrays.asList("BLEG", "ICP-OES"));
		Template dataBean = new Sg4Template();
		//When
		Optional<List<String>> errorMessages = testInstance.validate(templateParamMap, dataBean);
		//Then
		assertThat(errorMessages.isPresent(), is(false));
		List<String> h1002List = dataBean.get("H1002");
		assertThat(h1002List, is(nullValue()));
	}
	
	@Test
	public void shouldReturnMissingValueMessageWhenH1002ValueIsNull() {
		//Given
		String[] strs = {"H1002", null, null, null, null, null, null,  "AR", "ICP-OES", "ICP-OES", "ICP-OES"};
		H1002Validator testInstance = new H1002Validator();
		testInstance.init(strs);
		Map<String, List<String>> templateParamMap = new HashMap<>();
		templateParamMap.put(Strings.COLUMN_HEADERS, TestFixture.getDg4FullColumnHeaderList());
		templateParamMap.put(Strings.TEMPLATE_PROP_SG4_H1001_MANDATORY_FIELDS_HEADER,
				TestFixture.getDg4H1001MadatoryFieldHeadersList());
		templateParamMap.put(Strings.TITLE_PREFIX + H0800Validator.ASSAY_CODE_TITLE,
				Arrays.asList("BLEG", "AR", "ICP-OES"));
		Template dataBean = new Sg4Template();
		//When
		Optional<List<String>> errorMessages = testInstance.validate(templateParamMap, dataBean);
		//Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is("ERROR: Column Au requires a value in H1002"));
	}

	private H1002Validator givenTestInstance() {
		String[] strs = {"H1002", null, null, null, null, null, null, null, "BLEG", "AR", "ICP-OES", "ICP-OES", "ICP-OES", null, null, null, null, null};
		H1002Validator testInstance = new H1002Validator();
		testInstance.init(strs);
		return testInstance;
	}
}

package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;

public class H0700FieldsArraySizeValidatorTest {
	
	private H0700FieldsArraySizeValidator testInstance;

	@Test
	public void shouldReturnMessageWhenListSizeIsInConsistent() {
		//Given
		Map<String, List<String>> templateParams = new HashMap<>();
		templateParams.put(Strings.TITLE_PREFIX + H0700Validator.SAMPLE_PREP_CODE_TITLE, 
				TestFixture.getHeadlessDrillingCode());
		String[] strs = {"H0701", H0701Validator.DESCRIPTION_TITLE, "abc"};
		testInstance = new H0700FieldsArraySizeValidator(strs, templateParams);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H0700 and H0701 have inconsistent list size: 2 and 1")));
	}

	@Test
	public void shouldReturnMessageWhenListSizeIsLessThanTwo() {
		//Given
		Map<String, List<String>> templateParams = new HashMap<>();
		templateParams.put(Strings.TITLE_PREFIX + H0700Validator.SAMPLE_PREP_CODE_TITLE, 
				TestFixture.getOneDrillingCodeList());
		String[] strs = {"H0701", H0701Validator.DESCRIPTION_TITLE};
		testInstance = new H0700FieldsArraySizeValidator(strs, templateParams);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H0700 and H0701 have inconsistent list size: 1 and 0")));
	}
	
	@Test
	public void shouldReturnMessageWhenListSizeIsZero() {
		//Given
		String[] strs = null;
		Map<String, List<String>> templateParams = new HashMap<>();
		testInstance = new H0700FieldsArraySizeValidator(strs, templateParams);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H0700 and H0701 all have zero entry")));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		String[] strs = {"H0602", "Description", "Diamond drilling", "Reverse Circulation Drilling"};
		Map<String, List<String>> templateParams = new HashMap<>();
		templateParams.put("H0600", Arrays.asList("Drill_code", "DD", "RC"));
		templateParams.put("H0601", Arrays.asList("Drill_contractor", "Drill Faster Pty Ltd", "Drill Well Pty Ltd"));
		//When
		testInstance = new H0700FieldsArraySizeValidator(strs, templateParams);
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenTemplateParamMapIsNull() {
		//Given
		String[] strs = null;
		Map<String, List<String>> templateParams = null;
		//When
		new H0700FieldsArraySizeValidator(strs, templateParams);
		fail("Program reached unexpected point!");
	}
}

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

public class H0800FieldsArraySizeValidatorTest {
	
	private H0800FieldsArraySizeValidator testInstance;

	@Test
	public void shouldReturnMessageWhenListSizeIsInConsistent() {
		//Given
		Map<String, List<String>> templateParams = new HashMap<>();
		templateParams.put(Strings.TITLE_PREFIX + H0800Validator.ASSAY_CODE_TITLE, 
				TestFixture.getHeadlessDrillingCode());
		templateParams.put(Strings.TITLE_PREFIX + H0801Validator.ASSAY_COMPANY_TITLE, 
				TestFixture.getHeadlessDrillingCompanyList());
		String[] strs = {"H0802", "Assay_description", "abc"};
		testInstance = new H0800FieldsArraySizeValidator(strs, templateParams);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H0800, H0801 and H0802 have inconsistent list size: 2, 2 and 1")));
	}

	@Test
	public void shouldReturnMessageWhenListSizeIsLessThanTwo() {
		//Given
		Map<String, List<String>> templateParams = new HashMap<>();
		templateParams.put(Strings.TITLE_PREFIX + H0800Validator.ASSAY_CODE_TITLE, 
				TestFixture.getOneDrillingCodeList());
		templateParams.put(Strings.TITLE_PREFIX + H0801Validator.ASSAY_COMPANY_TITLE, 
				TestFixture.getOneDrillingCompanyList());
		String[] strs = {"H0802", "Assay_description"};
		testInstance = new H0800FieldsArraySizeValidator(strs, templateParams);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H0800, H0801 and H0802 have inconsistent list size: 1, 1 and 0")));
	}
	
	@Test
	public void shouldReturnMessageWhenListSizeIsZero() {
		//Given
		String[] strs = null;
		Map<String, List<String>> templateParams = new HashMap<>();
		testInstance = new H0800FieldsArraySizeValidator(strs, templateParams);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H0800, H0801 and H0802 all have zero entry")));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		String[] strs = {"H0802", "Assay_description", "Diamond drilling", "Reverse Circulation Drilling"};
		Map<String, List<String>> templateParams = new HashMap<>();
		templateParams.put("H0800", Arrays.asList("Assay_code", "DD", "RC"));
		templateParams.put("H0801", Arrays.asList("Assay_company", "Drill Faster Pty Ltd", "Drill Well Pty Ltd"));
		//When
		testInstance = new H0800FieldsArraySizeValidator(strs, templateParams);
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenTemplateParamMapIsNull() {
		//Given
		String[] strs = null;
		Map<String, List<String>> templateParams = null;
		//When
		new H0800FieldsArraySizeValidator(strs, templateParams);
		fail("Program reached unexpected point!");
	}
}

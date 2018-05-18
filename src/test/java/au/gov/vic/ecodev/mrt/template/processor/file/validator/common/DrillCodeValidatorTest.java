package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.fields.SL4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.DrillCodeValidator;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0400Validator;

public class DrillCodeValidatorTest {

	@Test
	public void shouldNoReturnMessageWhenDrillCodeIsExistInParamMap() {
		//Given
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "-90", "270" };
		int lineNumber = 6;
		List<String> headers = TestFixture.getColumnHeaderList();
		Map<String, List<String>> paramMap = new HashMap<>();
		paramMap.put(Strings.TITLE_PREFIX 
				+ H0400Validator.DRILL_CODE_TITLE, Arrays.asList("DD", "RC"));
		List<String> messages = new ArrayList<>();
		//When
		new DrillCodeValidator(datas, lineNumber, headers, 
				paramMap, SL4ColumnHeaders.DRILL_CODE.getCode()).validate(messages);
		//Then
		assertThat(CollectionUtils.isEmpty(messages), is(true));
	}
	
	@Test
	public void shouldReturnMessageWhenNoDrillCodeInParamMap() {
		//Given
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "-90", "270" };
		int lineNumber = 6;
		List<String> headers = TestFixture.getColumnHeaderList();
		Map<String, List<String>> paramMap = new HashMap<>();
		List<String> messages = new ArrayList<>();
		//When
		new DrillCodeValidator(datas, lineNumber, headers, paramMap, 
			 SL4ColumnHeaders.DRILL_CODE.getCode()).validate(messages);
		//Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("Line 6: No Drill_Code exist at H0400!")));
	}
	
	@Test
	public void shouldReturnMessageWhenDrillCodeIsNotExistInParamMap() {
		//Given
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "AD", "-90", "270" };
		int lineNumber = 6;
		List<String> headers = TestFixture.getColumnHeaderList();
		Map<String, List<String>> paramMap = new HashMap<>();
		paramMap.put(Strings.TITLE_PREFIX 
				+ H0400Validator.DRILL_CODE_TITLE, Arrays.asList("DD", "RC"));
		List<String> messages = new ArrayList<>();
		//When
		new DrillCodeValidator(datas, lineNumber, headers, paramMap, 
				 SL4ColumnHeaders.DRILL_CODE.getCode()).validate(messages);
		//Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("Line 6's Drill_Code column must exist in H0400! But got: AD")));
	}
}

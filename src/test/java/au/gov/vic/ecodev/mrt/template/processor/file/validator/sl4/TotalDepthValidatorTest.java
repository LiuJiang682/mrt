package au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
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

public class TotalDepthValidatorTest {

	@Test
	public void shouldReturnNoMessageWhenTotalDepthIsCorrectRange() {
		// Given
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "-90", "270" };
		int lineNumber = 6;
		TotalDepthValidator testInstance = new TotalDepthValidator(datas, lineNumber, 
				TestFixture.getColumnHeaderList());
		List<String> messages = new ArrayList<>();
		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(true));
	}
	
	@Test
	public void shouldReturnMessageWhenTotalDepthIsNotNumber() {
		// Given
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "dd", "DD", "90.000001", "270" };
		Map<String, List<String>> params = new HashMap<>();
		params.put(Strings.DIP_PRECISION, Arrays.asList("6"));
		int lineNumber = 6;
		TotalDepthValidator testInstance = new TotalDepthValidator(datas, lineNumber, 
				TestFixture.getColumnHeaderList());
		List<String> messages = new ArrayList<>();
		
		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0),
				is(equalTo("Line 6: Total Hole Depth is expected as a number, but got: dd")));
	}
	
	@Test
	public void shouldReturnMissingTotalDepthMessageWhenTotalDepthColumnHeaderIsNotProvided() {
		//Given
		TotalDepthValidator testInstance = new TotalDepthValidator(null, 0, new ArrayList<String>());
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages, is(notNullValue()));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("Line 0: Missing Total Hole Depth column")));
	}
	
}

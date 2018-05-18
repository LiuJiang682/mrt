package au.gov.vic.ecodev.mrt.template.processor.file.validator.dl4;

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

import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.fields.Dl4ColumnHeaders;

public class DepthFromNumberValidatorTest {

	@Test
	public void shouldReturnInstance() {
		//Given
		String[] datas = { "D", "KPDD001", "3" };
		Map<String, List<String>> params = new HashMap<>();
		params.put(Dl4ColumnHeaders.DEPTH_FROM.getCode(), 
				Arrays.asList(Dl4ColumnHeaders.DEPTH_FROM.getCode()));
		int lineNumber = 6;
		//When
		DepthFromNumberValidator testInstance = new DepthFromNumberValidator(datas, lineNumber,
				TestFixture.getDl4ColumnHeaderList(), params);
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test
	public void shouldReturnMissingDepthFromDataMessageWhenDepthFromColumnHeaderIsNotProvided() {
		// Given
		String[] strs = { "D", "KPDD001", "3" };
		Map<String, List<String>> params = new HashMap<>();
		params.put(Dl4ColumnHeaders.DEPTH_FROM.getCode(), 
				Arrays.asList(Dl4ColumnHeaders.DEPTH_FROM.getCode()));
		List<String> headers = new ArrayList<>(TestFixture.getDl4ColumnHeaderList());
		headers.set(1, "xxx");
		DepthFromNumberValidator testInstance = new DepthFromNumberValidator(strs, 0, headers, params);
		List<String> messages = new ArrayList<>();
		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.get(0), is(equalTo("ERROR: Line 0: Template Dl4 missing Depth_from column")));
	}
	
	@Test
	public void shouldReturnInvalidNumberFormatMessage() {
		//Given
		String[] datas = { "D", "KPDD001", "a" };
		Map<String, List<String>> params = new HashMap<>();
		params.put(Dl4ColumnHeaders.DEPTH_FROM.getCode(), 
				Arrays.asList(Dl4ColumnHeaders.DEPTH_FROM.getCode()));
		int lineNumber = 6;
		DepthFromNumberValidator testInstance = new DepthFromNumberValidator(datas, lineNumber,
				TestFixture.getDl4ColumnHeaderList(), params);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages, is(notNullValue()));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line 6: Template Dl4 Depth_from column is expected as a number, but got: a")));
	}
	
	@Test
	public void shouldReturnMissingDepthFromMessageWhenDepthFromParamIsNotProvidedInParamMap() {
		// Given
		Map<String, List<String>> params = new HashMap<>();
		DepthFromNumberValidator testInstance = new DepthFromNumberValidator(null, 0, 
				TestFixture.getDl4ColumnHeaderList(), params);
		List<String> messages = new ArrayList<>();
		// When
		testInstance.validate(messages);
		// Then
		assertThat(messages, is(notNullValue()));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line 0: Template Dl4 missing Depth_from column label from parameter")));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenTemplateParameterMapIsNull() {
		// Given
		Map<String, List<String>> params = null;
		// When
		new DepthFromNumberValidator(null, 0, new ArrayList<String>(), params);
		fail("Program reached unexpected point!");
	}
}

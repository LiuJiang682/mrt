package au.gov.vic.ecodev.mrt.template.processor.file.validator.ds4;

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
import au.gov.vic.ecodev.mrt.template.fields.Ds4ColumnHeaders;

public class AzimuthValidatorTest {

	@Test
	public void shouldReturnNoMessageWhenAzimuthMagIsInCorrectRange() {
		// Given
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "90", "360.000000" };
		Map<String, List<String>> params = new HashMap<>();
		params.put(Strings.AZIMUTH_MAG_PRECISION, Arrays.asList("6"));
		params.put(Ds4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
		int lineNumber = 6;
		AzimuthValidator testInstance = new AzimuthValidator(datas, lineNumber, TestFixture.getColumnHeaderList(),
				params);
		List<String> messages = new ArrayList<>();

		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(true));
	}
	
	@Test
	public void shouldReturnNoMessageWhenAzimuthTrueIsInCorrectRange() {
		// Given
		String[] datas = { "D", "KPDD001", "320", "90", "360.000000" };
		Map<String, List<String>> params = new HashMap<>();
		params.put(Strings.AZIMUTH_MAG_PRECISION, Arrays.asList("6"));
		params.put(Strings.AZIMUTH_TRUE, givenAzimuthTrueList());
		int lineNumber = 6;
		AzimuthValidator testInstance = new AzimuthValidator(datas, lineNumber, 
				TestFixture.getDs4AzimuthTrueColumnsList(),
				params);
		List<String> messages = new ArrayList<>();

		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(true));
	}

	@Test
	public void shouldReturnMessageWhenAzimuthMagIsGreaterThen360() {
		// Given
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "90", "360.000001" };
		Map<String, List<String>> params = new HashMap<>();
		params.put(Strings.AZIMUTH_MAG_PRECISION, Arrays.asList("6"));
		params.put(Ds4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
		int lineNumber = 6;
		AzimuthValidator testInstance = new AzimuthValidator(datas, lineNumber, TestFixture.getColumnHeaderList(),
				params);
		List<String> messages = new ArrayList<>();

		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0),
				is(equalTo("Line 6: Azimuth_MAG is expected as a number between 0 to 360, but got: 360.000001")));
	}

	@Test
	public void shouldReturnMessageWhenAzimuthMagIsLessThenZero() {
		// Given
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "90", "-0.000001" };
		Map<String, List<String>> params = new HashMap<>();
		params.put(Strings.AZIMUTH_MAG_PRECISION, Arrays.asList("6"));
		int lineNumber = 6;
		params.put(Ds4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
		AzimuthValidator testInstance = new AzimuthValidator(datas, lineNumber, TestFixture.getColumnHeaderList(),
				params);
		List<String> messages = new ArrayList<>();

		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0),
				is(equalTo("Line 6: Azimuth_MAG is expected as a number between 0 to 360, but got: -0.000001")));
	}

	@Test
	public void shouldReturnMissingAzmithMagDataMessageWhenAzimuthMagColumnHeaderIsNotProvided() {
		// Given
		String[] strs = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "90", "270" };
		Map<String, List<String>> params = new HashMap<>();
		params.put(Ds4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
		List<String> headers = new ArrayList<>(TestFixture.getColumnHeaderList());
		headers.set(7, "xxx");
		AzimuthValidator testInstance = new AzimuthValidator(strs, 0, headers, params);
		List<String> messages = new ArrayList<>();
		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.get(0), is(equalTo("ERROR: Line 0:  Missing Azimuth_MAG")));
	}

	@Test
	public void shouldReturnMessageWhenAzimuthTrueIsGreaterThen360() {
		// Given
		String[] datas = { "D", "KPDD001", "320", "90", "360.000001" };
		Map<String, List<String>> params = new HashMap<>();
		params.put(Strings.AZIMUTH_MAG_PRECISION, Arrays.asList("6"));
		params.put(Strings.AZIMUTH_TRUE, givenAzimuthTrueList());
		int lineNumber = 6;
		AzimuthValidator testInstance = new AzimuthValidator(datas, lineNumber,
				TestFixture.getDs4AzimuthTrueColumnsList(), params);
		List<String> messages = new ArrayList<>();

		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0),
				is(equalTo("Line 6: Azimuth_TRUE is expected as a number between 0 to 360, but got: 360.000001")));
	}

	@Test
	public void shouldReturnMessageWhenAzimuthTrueIsLessThenZero() {
		// Given
		String[] datas = { "D", "KPDD001", "320", "90", "-0.000001" };
		Map<String, List<String>> params = new HashMap<>();
		params.put(Strings.AZIMUTH_MAG_PRECISION, Arrays.asList("6"));
		int lineNumber = 6;
		params.put(Strings.AZIMUTH_TRUE, givenAzimuthTrueList());
		AzimuthValidator testInstance = new AzimuthValidator(datas, lineNumber,
				TestFixture.getDs4AzimuthTrueColumnsList(), params);
		List<String> messages = new ArrayList<>();

		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0),
				is(equalTo("Line 6: Azimuth_TRUE is expected as a number between 0 to 360, but got: -0.000001")));
	}

	@Test
	public void shouldReturnMissingAzmithTrueDataMessageWhenAzimuthMagColumnHeaderIsNotProvided() {
		// Given
		String[] strs = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "90", "270" };
		Map<String, List<String>> params = new HashMap<>();
		params.put(Strings.AZIMUTH_TRUE, givenAzimuthTrueList());
		List<String> headers = new ArrayList<>(TestFixture.getColumnHeaderList());
		headers.set(7, "xxx");
		AzimuthValidator testInstance = new AzimuthValidator(strs, 0, headers, params);
		List<String> messages = new ArrayList<>();
		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.get(0), is(equalTo("ERROR: Line 0:  Missing Azimuth_TRUE")));
	}

	@Test
	public void shouldReturnMissingAzmithMessageWhenAzimuthParamIsNotProvidedInParamMap() {
		// Given
		Map<String, List<String>> params = new HashMap<>();
		AzimuthValidator testInstance = new AzimuthValidator(null, 0, TestFixture.getColumnHeaderList(), params);
		List<String> messages = new ArrayList<>();
		// When
		testInstance.validate(messages);
		// Then
		assertThat(messages, is(notNullValue()));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line 0:  Missing either Azimuth_MAG or Azimuth_TRUE")));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenTemplateParameterMapIsNull() {
		// Given
		Map<String, List<String>> params = null;
		// When
		new AzimuthValidator(null, 0, new ArrayList<String>(), params);
		fail("Program reached unexpected point!");
	}

	private List<String> givenAzimuthTrueList() {
		List<String> azimuthMagList = new ArrayList<>();
		azimuthMagList.add(Strings.AZIMUTH_TRUE);
		return azimuthMagList;
	}
}

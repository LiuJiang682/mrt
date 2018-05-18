package au.gov.vic.ecodev.mrt.template.processor.file.validator.dg4;

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
import au.gov.vic.ecodev.mrt.template.fields.Dg4ColumnHeaders;

public class FromValidatorTest {

	private Map<String, List<String>> params;
	
	@Test
	public void shouldReturnNoMessageWhenFromDataIsValid() {
		//Given
		givenMandatoryFields();
		String[] strs = { "D", "KPDD001",  "A",  "0", "1", "dd", "370" };
		List<String> columnList = new ArrayList<>(params.get(Strings.COLUMN_HEADERS));
		FromValidator testInstance = new FromValidator(strs, 0, columnList, params);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(CollectionUtils.isEmpty(messages), is(true));
		List<String> fromList = params.get(Dg4ColumnHeaders.FROM.getCode());
		assertThat(fromList, is(notNullValue()));
		assertThat(fromList.size(), is(equalTo(1)));
		assertThat(fromList.get(0), is(equalTo("0")));
	}
	
	@Test
	public void shouldReturnMissingColumnHeaderMessageWhenFromHeaderMissing() {
		//Given
		givenMandatoryFields();
		String[] strs = { "D", "KPDD001",  "A",  "0", "1", "dd", "370" };
		List<String> columnList = new ArrayList<>(params.get(Strings.COLUMN_HEADERS));
		columnList.set(2, "abvc");
		FromValidator testInstance = new FromValidator(strs, 0, columnList, params);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line 0: Template DG4 missing From column")));
	}
	
	@Test
	public void shouldReturnInvalidFromMessageWhenFromDataIsInvalid() {
		//Given
		givenMandatoryFields();
		String[] strs = { "D", "KPDD001",  "A",  "A", "1", "dd", "370" };
		List<String> columnList = new ArrayList<>(params.get(Strings.COLUMN_HEADERS));
		FromValidator testInstance = new FromValidator(strs, 0, columnList, params);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line 0: Template DG4 From column is expected as a number, but got: A")));
	}
	
	@Test
	public  void shouldReturnInstance() {
		//Given
		givenMandatoryFields();
		String[] strs = { "D", "KPDD001",  "A",  "0", "1", "dd", "370" };
		List<String> columnList = params.get(Strings.COLUMN_HEADERS);
		//When
		FromValidator testInstance = new FromValidator(strs, 0, columnList, params);
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenColumnHeaderIsNull() {
		//Given
		String[] strs = null;
		List<String> columnList = null;
		//When
		new FromValidator(strs, 0, columnList, params);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenParamsIsNull() {
		//Given
		givenMandatoryFields();
		String[] strs = null;
		List<String> columnList = params.get(Strings.COLUMN_HEADERS);
		//When
		new FromValidator(strs, 0, columnList, null);
		fail("Program reached unexpected point!");
	}
	
	private void givenMandatoryFields() {
		String[] mandatoryFields = TestFixture.getDg4MandatoryColumns();
		params = new HashMap<>();
		params.put(Strings.COLUMN_HEADERS, Arrays.asList(mandatoryFields));
	}
}

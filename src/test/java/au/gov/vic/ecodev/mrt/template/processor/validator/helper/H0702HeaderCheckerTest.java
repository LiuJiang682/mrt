package au.gov.vic.ecodev.mrt.template.processor.validator.helper;

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
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0702Validator;

public class H0702HeaderCheckerTest {

	private H0702HeaderChecker testInstance;
	
	@Test
	public void shouldReturnMissingJobNoHeaderMessageWhenH0702IsMultiplyAndNoJobNoHeader() {
		// Given
		String[] strs = { "H1000", "Hole_id", "from", "To", "Drill_code", "Sample_id" };
		givenTestInstance();
		Map<String, List<String>> params = new HashMap<>();
		params.put(Strings.TITLE_PREFIX 
				+ H0702Validator.JOB_NO_TITLE, Arrays.asList("Multiply"));
		List<String> messages = new ArrayList<>();
		// When
		testInstance.doOptionalFieldHeaderCheck(messages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Missing Job_no/batch_no header when H0702 value is Multiply")));
	}
	
	@Test
	public void shouldReturnEmptyMessageWhenH0702IsMultiplyAndJobNoHeaderProvided() {
		// Given
		String[] strs = { "H1000", "Hole_id", "from", "To", "Drill_code", "Sample_id", "Job_no" };
		givenTestInstance();
		Map<String, List<String>> params = new HashMap<>();
		params.put(Strings.TITLE_PREFIX 
				+ H0702Validator.JOB_NO_TITLE, Arrays.asList("Multiply"));
		List<String> messages = new ArrayList<>();
		// When
		testInstance.doOptionalFieldHeaderCheck(messages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(true));
	}
	
	@Test
	public void shouldReturnEmptyMessageWhenH0702IsMultiplyAndBatchNoNoHeaderProvided() {
		// Given
		String[] strs = { "H1000", "Hole_id", "from", "To", "Drill_code", "Sample_id", "Batch_no" };
		givenTestInstance();
		Map<String, List<String>> params = new HashMap<>();
		params.put(Strings.TITLE_PREFIX 
				+ H0702Validator.JOB_NO_TITLE, Arrays.asList("Multiply"));
		List<String> messages = new ArrayList<>();
		// When
		testInstance.doOptionalFieldHeaderCheck(messages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(true));
	}
	
	@Test
	public void shouldReturnTrueWhenBatchPartialHeaderProvided() {
		// Given
		String[] strs = { "H1000", "Hole_id", "from", "To", "Drill_code", "Sample_id", "noBatch_no" };
		givenTestInstance();
		// When
		boolean flag = testInstance.matchesAny(Arrays.asList(strs), "batch");
		// Then
		assertThat(flag, is(true));
	}
	
	private void givenTestInstance() {
		testInstance = new H0702HeaderChecker();
	}
}

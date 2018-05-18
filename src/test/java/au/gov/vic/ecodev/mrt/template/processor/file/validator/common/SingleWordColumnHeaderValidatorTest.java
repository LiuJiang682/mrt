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

public class SingleWordColumnHeaderValidatorTest {

	private static final String TEST_ROW_H1000 = "H1000";
	private static final String TEST_TEMPLATE_DG4 = "DG4";
	private SingleWordColumnHeaderValidator testInstance;
	
	@Test
	public void shouldReturnEmptyMessageWhenCodeExistInHeaders() {
		//Given
		String code = "Hole_id";
		String[] strs = { TEST_ROW_H1000, code, "Depth From", "Azimuth_MAG", "Dip"};
		testInstance = new SingleWordColumnHeaderValidator(TEST_TEMPLATE_DG4, 
				TEST_ROW_H1000, code);
		List<String> messages = new ArrayList<>();
		Map<String, List<String>> templateParamMap = new HashMap<>();
		//When
		testInstance.validate(messages, templateParamMap, Arrays.asList(strs));
		//Then
		assertThat(CollectionUtils.isEmpty(messages), is(true));
	}
	
	@Test
	public void shouldReturnMissingHoleIdMessageWhenCodeNotExistInHeaders() {
		//Given
		String code = "Hole_id";
		String[] strs = { TEST_ROW_H1000, "Hole_idd", "Depth From", "Azimuth_MAG", "Dip"};
		testInstance = new SingleWordColumnHeaderValidator(TEST_TEMPLATE_DG4, 
				TEST_ROW_H1000, code);
		List<String> messages = new ArrayList<>();
		Map<String, List<String>> templateParamMap = new HashMap<>();
		//When
		testInstance.validate(messages, templateParamMap, Arrays.asList(strs));
		//Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Template DG4 H1000 row requires the Hole_id column")));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		String code = "abc";
		//When
		testInstance = new SingleWordColumnHeaderValidator(TEST_TEMPLATE_DG4, 
				TEST_ROW_H1000, code);
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenCodeIsNull() {
		//Given
		String code = null;
		//When
		new SingleWordColumnHeaderValidator(TEST_TEMPLATE_DG4, 
				TEST_ROW_H1000, code);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenRowIsNull() {
		//Given
		String code = null;
		//When
		new SingleWordColumnHeaderValidator(TEST_TEMPLATE_DG4, 
				null, code);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenTemplateIsNull() {
		//Given
		String code = null;
		//When
		new SingleWordColumnHeaderValidator(null, 
				null, code);
		fail("Program reached unexpected point!");
	}
}

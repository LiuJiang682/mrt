package au.gov.vic.ecodev.mrt.template.processor.file.validator.common.helper;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;

public class PartialFileNameFlagHelperTest {

	@Test
	public void shouldReturnTrueWhenTrueProvidedInParams() {
		//Given
		Map<String, List<String>> params = new HashMap<>();
		params.put(Strings.PARTIAL_FILE_NAME_KEY, Arrays.asList("true"));
		//When
		boolean flag = new PartialFileNameFlagHelper(params).getPartilaFileNameFlag();
		//Then
		assertThat(flag, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenFalseProvidedInParams() {
		//Given
		Map<String, List<String>> params = new HashMap<>();
		params.put(Strings.PARTIAL_FILE_NAME_KEY, Arrays.asList("false"));
		//When
		boolean flag = new PartialFileNameFlagHelper(params).getPartilaFileNameFlag();
		//Then
		assertThat(flag, is(false));
	}
	
	@Test
	public void shouldReturnInstanceWhenParamsProvided() {
		//Given
		Map<String, List<String>> params = new HashMap<>();
		//When
		PartialFileNameFlagHelper testInstance = new PartialFileNameFlagHelper(params);
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenParamsIsNull() {
		//Given
		Map<String, List<String>> params = null;
		//When
		new PartialFileNameFlagHelper(params);
		fail("Program reached unexpected point!");
	}
}

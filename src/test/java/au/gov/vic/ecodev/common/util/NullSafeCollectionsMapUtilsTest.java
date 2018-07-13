package au.gov.vic.ecodev.common.util;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class NullSafeCollectionsMapUtilsTest {

	private static final String TEST_MAP_KEY = "currentLine";

	@Test
	public void shouldReturnIntWhenDataProvided() {
		//Given
		Map<String, List<String>> map = new HashMap<>();
		map.put(TEST_MAP_KEY, Arrays.asList("6"));
		//When
		int lineNumber = new NullSafeCollectionsMapUtils().parseInt(map, TEST_MAP_KEY);
		//Then
		assertThat(lineNumber, is(equalTo(6)));
	}
	
	@Test
	public void shouldReturnNotFoundWhenMapIsNull() {
		//Given
		Map<String, List<String>> map = null;
		//When
		int lineNumber = new NullSafeCollectionsMapUtils().parseInt(map, TEST_MAP_KEY);
		//Then
		assertThat(lineNumber, is(equalTo(-1)));
	}
	
	@Test
	public void shouldReturnNotFoundWhenMapIsNotContainMapKey() {
		//Given
		Map<String, List<String>> map = new HashMap<>();
		//When
		int lineNumber = new NullSafeCollectionsMapUtils().parseInt(map, TEST_MAP_KEY);
		//Then
		assertThat(lineNumber, is(equalTo(-1)));
	}
	
	@Test
	public void shouldReturnNotFoundWhenMapIsContainInvalidValue() {
		//Given
		Map<String, List<String>> map = new HashMap<>();
		map.put(TEST_MAP_KEY, Arrays.asList("6ab"));
		//When
		int lineNumber = new NullSafeCollectionsMapUtils().parseInt(map, TEST_MAP_KEY);
		//Then
		assertThat(lineNumber, is(equalTo(-1)));
	}
}

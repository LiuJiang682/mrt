package au.gov.vic.ecodev.mrt.template.processor.file.validator.common.helper;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class StringListHelperTest {

	@Test
	public void shouldReturnOneElementList() {
		// Given
		List<String> data = Arrays.asList("Tenement_no", "EL123");
		// When
		List<String> plainData = new StringListHelper().trimOffTheTitle(data);
		// Then
		assertThat(plainData, is(notNullValue()));
		assertThat(plainData.size(), is(equalTo(1)));
		assertThat(plainData.get(0), is(equalTo("EL123")));
	}
	
	@Test
	public void shouldReturnTwoElementList() {
		// Given
		List<String> data = Arrays.asList("Tenement_no", "EL123", "EL456");
		// When
		List<String> plainData = new StringListHelper().trimOffTheTitle(data);
		// Then
		assertThat(plainData, is(notNullValue()));
		assertThat(plainData.size(), is(equalTo(2)));
		assertThat(plainData.get(0), is(equalTo("EL123")));
		assertThat(plainData.get(1), is(equalTo("EL456")));
	}
	
	@Test
	public void shouldReturnNullListWhenOnlyOneElementProvided() {
		// Given
		List<String> data = Arrays.asList("Tenement_no");
		// When
		List<String> plainData = new StringListHelper().trimOffTheTitle(data);
		// Then
		assertThat(plainData, is(nullValue()));
	}

	@Test
	public void shouldreturnNullListWhenNullListProvided() {
		// Given
		List<String> data = null;
		// When
		List<String> plainData = new StringListHelper().trimOffTheTitle(data);
		// Then
		assertThat(plainData, is(nullValue()));
	}
}

package au.gov.vic.ecodev.mrt.template.processor.model;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class MrtTemplateBaseTest {

	@Test
	public void shouldReturnListOfData() {
		//Given
		MrtTemplateBase testInstance = givenTestInstance();
		//When
		List<String> retrievedList = testInstance.get("1");
		//Then
		assertThat(retrievedList, is(notNullValue()));
	}
	
	@Test
	public void shouldReturnNullListWhenKeyNoExist() {
		//Given
		MrtTemplateBase testInstance = new MrtTemplateBase();
		//When
		List<String> retrievedList = testInstance.get("1");
		//Then
		assertThat(retrievedList, is(nullValue()));
	}
	
	@Test
	public void shouldReturnListOfExistingKeys() {
		//Given
		MrtTemplateBase testInstance = givenTestInstance();
		//When
		List<String> keys = testInstance.getKeys();
		//Then
		assertThat(keys, is(notNullValue()));
		assertThat(keys.size(), is(equalTo(1)));
		assertThat(keys.get(0), is(equalTo("1")));
	}
	
	private MrtTemplateBase givenTestInstance() {
		MrtTemplateBase testInstance = new MrtTemplateBase();
		List<String> dataList = Arrays.asList("abc");
		testInstance.put("1", dataList);
		return testInstance;
	}
}

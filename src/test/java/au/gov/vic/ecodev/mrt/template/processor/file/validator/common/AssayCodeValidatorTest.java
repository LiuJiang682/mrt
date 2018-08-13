package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class AssayCodeValidatorTest {

	private AssayCodeValidator testInstance;
	
	@Test
	public void shouldReturnEmptyMessageWhenH1002ValueExistInH0800() {
		//Given
		List<String> assayCodeList = Arrays.asList("AU-TL43", "ME-ICP41");
		givenTestInstance(assayCodeList);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.doAssayCodeLookUpValidation(messages);
		//Then
		assertThat(messages.isEmpty(), is(true));
	}
	
	@Test
	public void shouldReturnEmptyMessageWhenH1002ValueExistInH0800WithPrefix() {
		//Given
		List<String> assayCodeList = Arrays.asList("LS:AU-TL43", "LS:ME-ICP41");
		givenTestInstance(assayCodeList);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.doAssayCodeLookUpValidation(messages);
		//Then
		assertThat(messages.isEmpty(), is(true));
	}
	
	@Test
	public void shouldReturnErrorMessage() {
		//Given
		List<String> assayCodeList = Arrays.asList("AU-TL431", "ME-ICP41a");
		givenTestInstance(assayCodeList);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.doAssayCodeLookUpValidation(messages);
		//Then
		assertThat(messages.isEmpty(), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: AU-TL43 is NOT included in H0800")));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		List<String> assayCodeList = Arrays.asList("AU-TL43", "ME-ICP41");
		givenTestInstance(assayCodeList);
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}

	private void givenTestInstance(List<String> assayCodeList) {
		testInstance = new AssayCodeValidator("AU-TL43", assayCodeList);
	}
}

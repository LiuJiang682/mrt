package au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.powermock.reflect.Whitebox;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4.DataRecordNumberValidator;

public class DataRecordNumberValidatorTest {

	@Test
	public void shouldReturnEmptyMessageWhenBothListSizeIsSame() {
		// Given
		List<String> expectedList = Arrays.asList("3");
		List<String> actualList = Arrays.asList("3");
		DataRecordNumberValidator testInstance = new DataRecordNumberValidator(expectedList, actualList);
		List<String> messages = new ArrayList<>();
		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(true));
	}
	
	@Test
	public void shouldAssignedListsCorrectlyWhenConstructInstance() {
		//Given
		List<String> expectedList = Arrays.asList("3");
		List<String> actualList = Arrays.asList("2");
		//When
		DataRecordNumberValidator testInstance = new DataRecordNumberValidator(expectedList, actualList);
		//Then
		assertThat(testInstance, is(notNullValue()));
		List<String> retrievedExpectedList = Whitebox.getInternalState(testInstance, "expectedRecordsList");
		assertThat(retrievedExpectedList, is(equalTo(expectedList)));
		List<String> retrievedActualList = Whitebox.getInternalState(testInstance, "actualRecordsList");
		assertThat(retrievedActualList, is(equalTo(actualList)));
	}
	
	@Test
	public void shouldReturnIncorrectSizeMessageWhenBothListSizeIsNotEqual() {
		// Given
		List<String> expectedList = Arrays.asList("3");
		List<String> actualList = Arrays.asList("2");
		DataRecordNumberValidator testInstance = new DataRecordNumberValidator(expectedList, actualList);
		List<String> errorMessages = new ArrayList<>();
		// When
		testInstance.validate(errorMessages);
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0),
				is(equalTo("Number_of_data_records in the templateParamMap is 3 and Number_of_records_added is 2")));
	}

	@Test
	public void shouldReturnNoExpectedRecordMessageWhenNoExpectedRecordProvided() {
		// Given
		List<String> expectedList = null;
		List<String> actualList = Arrays.asList("2");
		DataRecordNumberValidator testInstance = new DataRecordNumberValidator(expectedList, actualList);
		List<String> errorMessages = new ArrayList<>();
		// When
		testInstance.validate(errorMessages);
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), is(equalTo("No Number_of_data_records in the templateParamMap")));
	}

	@Test
	public void shouldReturnNoActualRecordMessageWhenNoActualRecordProvided() {
		// Given
		List<String> expectedList = Arrays.asList("3");
		List<String> actualList = null;
		DataRecordNumberValidator testInstance = new DataRecordNumberValidator(expectedList, actualList);
		List<String> errorMessages = new ArrayList<>();
		// When
		testInstance.validate(errorMessages);
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), is(equalTo("No Number_of_records_added in the templateParamMap")));
	}

	
}

package au.gov.vic.ecodev.mrt.template.processor.updater;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class NumberOfRecordTemplateExtractorTest {

	private NumberOfRecordsTemplateExtractor testInstance;
	private Template mockTemplate;
	
	@Test
	public void shouldReturnNumOfRecords() {
		// Given
		givenTestInstance();
		when(mockTemplate.get(eq("H0203"))).thenReturn(TestFixture.getNumList());
		// When
		int numOfRecords = testInstance.extractNumOfRecordsFromTemplate(mockTemplate);
		// Then
		assertThat(numOfRecords, is(equalTo(3)));
	}

	@Test
	public void shouldReturnZeroAsNumOfRecordsWhenListIsNull() {
		// Given
		givenTestInstance();
		when(mockTemplate.get(eq("H0203"))).thenReturn(null);
		// When
		int numOfRecords = testInstance.extractNumOfRecordsFromTemplate(mockTemplate);
		// Then
		assertThat(numOfRecords, is(equalTo(0)));
	}

	@Test
	public void shouldReturnZeroAsNumOfRecordsWhenListContainsNotNumberValue() {
		// Given
		givenTestInstance();
		List<String> numList = new ArrayList<>();
		numList.add("Number_of_data_records");
		numList.add("abc");
		when(mockTemplate.get(eq("H0203"))).thenReturn(numList);
		// When
		int numOfRecords = testInstance.extractNumOfRecordsFromTemplate(mockTemplate);
		// Then
		assertThat(numOfRecords, is(equalTo(0)));
	}

	@Test
	public void shouldReturnZeroAsNumOfRecordsWhenListContainsOnlyOneValue() {
		// Given
		givenTestInstance();
		List<String> numList = new ArrayList<>();
		numList.add("6");
		when(mockTemplate.get(eq("H0203"))).thenReturn(numList);
		// When
		int numOfRecords = testInstance.extractNumOfRecordsFromTemplate(mockTemplate);
		// Then
		assertThat(numOfRecords, is(equalTo(0)));
	}
	
	private void givenTestInstance() {
		testInstance = new NumberOfRecordsTemplateExtractor();
		mockTemplate = Mockito.mock(Template.class);
	}
}

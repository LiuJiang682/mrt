package au.gov.vic.ecodev.mrt.template.processor.updater.helper;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class ProjectionZoneExtractionHelperTest {
	
	private ProjectionZoneExtractionHelper testInstance;
	private Template mockTemplate;

	@Test
	public void shouldReturnProjectZone() {
		// Given
		givenTestInstance();
		when(mockTemplate.get(eq("H0531"))).thenReturn(TestFixture.getProjectZoneList());
		// When
		BigDecimal amgZone = testInstance.extractAmgZoneFromTemplate();
		// Then
		assertThat(amgZone, is(equalTo(new BigDecimal("54"))));
	}
	
	@Test
	public void shouldReturnNullAsProjectZoneWhenListContainsNotNumberValue() {
		// Given
		givenTestInstance();
		List<String> projectZoneList = new ArrayList<>();
		projectZoneList.add("Prjection_zone");
		projectZoneList.add("abc");
		when(mockTemplate.get(eq("H0531"))).thenReturn(projectZoneList);
		// When
		BigDecimal projectZone = testInstance.extractAmgZoneFromTemplate();
		// Then
		assertThat(projectZone, is(nullValue()));
	}

	@Test
	public void shouldReturnNullAsProjectZoneWhenListIsNull() {
		// Given
		givenTestInstance();
		List<String> projectZoneList = null;
		when(mockTemplate.get(eq("H0531"))).thenReturn(projectZoneList);
		// When
		BigDecimal projectZone = testInstance.extractAmgZoneFromTemplate();
		// Then
		assertThat(projectZone, is(nullValue()));
	}

	@Test
	public void shouldReturnNullAsProjectzoneWhenListContainsOnlyOneValue() {
		// Given
		givenTestInstance();
		List<String> numList = new ArrayList<>();
		numList.add("6");
		when(mockTemplate.get(eq("H0531"))).thenReturn(numList);
		// When
		BigDecimal projectZone = testInstance.extractAmgZoneFromTemplate();
		// Then
		assertThat(projectZone, is(nullValue()));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		givenTestInstance();
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenNullTemplateProvided() {
		//Given
		Template template = null;
		//When
		new ProjectionZoneExtractionHelper(template);
		fail("Program reached unexpected point!");
	}
	
	private void givenTestInstance() {
		mockTemplate = Mockito.mock(Template.class);
		testInstance = new ProjectionZoneExtractionHelper(mockTemplate);
	}
}

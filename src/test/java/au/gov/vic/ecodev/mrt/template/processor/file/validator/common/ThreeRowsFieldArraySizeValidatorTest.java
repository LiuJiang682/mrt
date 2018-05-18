package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.fixture.TestFixture;

public class ThreeRowsFieldArraySizeValidatorTest {

	private ThreeRowsFieldArraySizeValidator testInstance;
	
	@Test
	public void shouldReturnMessageWhenListSizeIsInConsistent() {
		//Given
		givenTestInstance();
		String[] strs = {"H0602", "Description", "abc"};
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages, TestFixture.getHeadlessDrillingCode(), 
				TestFixture.getHeadlessDrillingCompanyList(), Arrays.asList(strs));
		//Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H0600, H0601 and H0602 have inconsistent list size: 2, 2 and 3")));
	}
	
	@Test
	public void shouldReturnMessageWhenListSizeIsZero() {
		//Given
		givenTestInstance();
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages, null, null, null);
		//Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H0600, H0601 and H0602 all have zero entry")));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		givenTestInstance();
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenRow1IsNull() {
		String row1 = null;
		String row2 = null;
		String row3 = null;
		//When
		new ThreeRowsFieldArraySizeValidator(row1,
				row2, row3);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenRow2IsNull() {
		String row1 = "H0600";
		String row2 = null;
		String row3 = null;
		//When
		new ThreeRowsFieldArraySizeValidator(row1,
				row2, row3);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenRow3IsNull() {
		String row1 = "H0600";
		String row2 = "H0601";
		String row3 = null;
		//When
		new ThreeRowsFieldArraySizeValidator(row1,
				row2, row3);
		fail("Program reached unexpected point!");
	}
	
	private void givenTestInstance() {
		String row1 = "H0600";
		String row2 = "H0601";
		String row3 = "H0602";
		
		testInstance = new ThreeRowsFieldArraySizeValidator(row1, row2, row3);
	}
}

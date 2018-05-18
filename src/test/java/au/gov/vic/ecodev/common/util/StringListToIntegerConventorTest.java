package au.gov.vic.ecodev.common.util;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class StringListToIntegerConventorTest {

	private StringListToIntegerConventor testInstance;
	
	@Test
	public void shouldReturnIntWhenListProvided() {
		//Given
		List<String> strings = Arrays.asList("6");
		testInstance = new StringListToIntegerConventor(strings);
		//When
		int number = testInstance.parse();
		//Then
		assertThat(number, is(equalTo(6)));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		List<String> strings = Arrays.asList("6");
		//When
		testInstance = new StringListToIntegerConventor(strings);
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenListIsNull() {
		//Given
		List<String> strings = null;
		//When
		new StringListToIntegerConventor(strings);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = NumberFormatException.class)
	public void shouldRaiseExceptionWhenListIsInvalid() {
		//Given
		List<String> strings = Arrays.asList("abc");
		//When
		new StringListToIntegerConventor(strings).parse();
		fail("Program reached unexpected point!");
	}
}

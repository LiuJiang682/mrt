package au.gov.vic.ecodev.common.util;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class StringToDateConventorTest {

	private StringToDateConventor testInstance;
	
	@Test
	public void shouldConvertStringToDate() throws ParseException {
		//Given
		givenTestInstance();
		String dateString = "12-Nov-12";
		//When
		Date date = testInstance.parse(dateString);
		//Then
		assertThat(date, is(notNullValue()));
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		assertThat(c.get(Calendar.YEAR), is(equalTo(2012)));
		assertThat(c.get(Calendar.MONTH), is(equalTo(Calendar.NOVEMBER)));
		assertThat(c.get(Calendar.DAY_OF_MONTH), is(equalTo(12)));
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
	public void shouldRaiseExceptionWhenPatternIsNull() {
		//Given
		String pattern = null;
		//When
		new StringToDateConventor(pattern);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = ParseException.class)
	public void shouldRaiseExceptionWhenDateStringIsRubbish() throws ParseException {
		//Given
		String pattern = "dd-MMM-yy";
		//When
		new StringToDateConventor(pattern).parse("abc");
		fail("Program reached unexpected point!");
	}
	
	@Test
	public void shouldRaiseExceptionWhenDateStringIsNull() throws ParseException {
		//Given
		String pattern = "dd-MMM-yy";
		//When
		Date date = new StringToDateConventor(pattern).parse(null);
		//Then
		assertThat(date, is(nullValue()));
	}
	
	private void givenTestInstance() {
		String pattern = "dd-MMM-yy";
		
		testInstance = new StringToDateConventor(pattern);
	}
}

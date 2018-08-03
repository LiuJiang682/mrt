package au.gov.vic.ecodev.mrt.template.processor.updater.helper;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class VariationHelperTest {

	private VariationHelper testInstance;
	
	@Test
	public void shouldReturnWordIndex() {
		//Given
		givenTestInstance("Azimuth_Mag");
		//When
		int index = testInstance.findIndex();
		//Then
		assertThat(index, is(equalTo(1)));
	}
	
	@Test
	public void shouldReturnSpaceWordIndex() {
		//Given
		givenTestInstance("Azimuth Mag");
		//When
		int index = testInstance.findIndex();
		//Then
		assertThat(index, is(equalTo(1)));
	}
	
	@Test
	public void shouldReturnSpaceWordIgnoreCaseIndex() {
		//Given
		givenTestInstance("azimuth mag");
		//When
		int index = testInstance.findIndex();
		//Then
		assertThat(index, is(equalTo(1)));
	}
	
	@Test
	public void shouldReturnHyphenWordIndex() {
		//Given
		givenTestInstance("Azimuth-Mag");
		//When
		int index = testInstance.findIndex();
		//Then
		assertThat(index, is(equalTo(1)));
	}
	
	@Test
	public void shouldReturnReversedWordIndex() {
		//Given
		givenTestInstance("Mag_Azimuth");
		//When
		int index = testInstance.findIndex();
		//Then
		assertThat(index, is(equalTo(1)));
	}
	
	@Test
	public void shouldReturnReversedSpaceWordIndex() {
		//Given
		givenTestInstance("Mag Azimuth");
		//When
		int index = testInstance.findIndex();
		//Then
		assertThat(index, is(equalTo(1)));
	}
	
	@Test
	public void shouldReturnReversedHyphenWordIndex() {
		//Given
		givenTestInstance("Mag-Azimuth");
		//When
		int index = testInstance.findIndex();
		//Then
		assertThat(index, is(equalTo(1)));
	}
	
	@Test
	public void shouldReturnReversedHyphenIgnoreCaseWordIndex() {
		//Given
		givenTestInstance("mag-azimuth");
		//When
		int index = testInstance.findIndex();
		//Then
		assertThat(index, is(equalTo(1)));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenDataListIsNull() {
		//Given
		List<String> data = null;
		String code = null;
		//When
		new VariationHelper(data, code);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenCodeIsNull() {
		//Given
		List<String> data = Arrays.asList("abc");
		String code = null;
		//When
		new VariationHelper(data, code);
		fail("Program reached unexpected point!");
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		givenTestInstance("Azimuth_Mag");
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}

	private void givenTestInstance(String code) {
		List<String> data = Arrays.asList("Hole_ID", code);
		testInstance = new VariationHelper(data, "Azimuth_Mag");
	}
}

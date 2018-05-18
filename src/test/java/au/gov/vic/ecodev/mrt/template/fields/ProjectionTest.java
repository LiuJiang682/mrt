package au.gov.vic.ecodev.mrt.template.fields;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

public class ProjectionTest {

	@Test
	public void shouldReturnCorrectProjections() {
		//Given
		//When
		//Then
		assertThat(Projection.fromString("Non-projected"), is(equalTo(Projection.NON_PROJECTED)));
		assertThat(Projection.fromString("Universal Transverse Mercator (UTM)"), is(equalTo(Projection.UTM)));
		assertThat(Projection.fromString("Lambert Conformable"), is(equalTo(Projection.LAMBERT_CONFORMABLE)));
		assertThat(Projection.fromString("Albers Equal Area"), is(equalTo(Projection.ALBERS_EQUAL_AREA)));
		assertThat(Projection.fromString("Map Grid of Australia (MGA)"), is(equalTo(Projection.MGA)));
		assertThat(Projection.fromString("Australian Map Grid (AGD)"), is(equalTo(Projection.AGD)));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenCodeIsNull() {
		//Given
		String code = null;
		//When
		Projection.fromString(code);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenCodeIsEmpty() {
		//Given
		String code = "";
		//When
		Projection.fromString(code);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenCodeIsRubbish() {
		//Given
		String code = "abc";
		//When
		Projection.fromString(code);
		fail("Program reached unexpected point!");
	}
}

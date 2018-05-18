package au.gov.vic.ecodev.mrt.template.fields;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

public class ProjectionZoneTest {

	@Test
	public void shouldReturnCorrectProjectionZoneValue() {
		//Given
		//When
		//Then
		assertThat(ProjectionZone.fromString("54"), is(equalTo(ProjectionZone.VIC_54)));
		assertThat(ProjectionZone.fromString("55"), is(equalTo(ProjectionZone.VIC_55)));
		assertThat(ProjectionZone.fromString("Non-projected"), is(equalTo(ProjectionZone.NON_PROJECTED)));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenCodeIsNull() {
		//Given
		String code = null;
		//When
		ProjectionZone.fromString(code);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenCodeIsEmpty() {
		//Given
		String code = "";
		//When
		ProjectionZone.fromString(code);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenCodeIsRubbish() {
		//Given
		String code = "abc";
		//When
		ProjectionZone.fromString(code);
		fail("Program reached unexpected point!");
	}
}

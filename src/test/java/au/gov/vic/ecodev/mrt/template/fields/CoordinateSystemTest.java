package au.gov.vic.ecodev.mrt.template.fields;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CoordinateSystemTest {

	@Test
	public void shouldReturnCorrectValue() {
		//Given
		//When
		//Then
		assertThat(CoordinateSystem.fromString("Projected"), is(equalTo(CoordinateSystem.PROJECTED)));
		assertThat(CoordinateSystem.fromString("Latitude/Longitude"), is(equalTo(CoordinateSystem.LATITUDE_LONGITUDE)));
	}
}

package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class GeodeticNumberValidatorTest {

	@Test
	public void shouldReturnTrueWhenIsValidGeodeticNumberCalledWith6DigitalDecimal() {
		// Given
		GeodeticNumberValidator geodeticNumberValidator = new GeodeticNumberValidator();
		// When
		// Then
		assertThat(geodeticNumberValidator.isValidGeodeticNumber("392200"), is(true));
		assertThat(geodeticNumberValidator.isValidGeodeticNumber("392200.1"), is(true));
		assertThat(geodeticNumberValidator.isValidGeodeticNumber("392200.12345"), is(true));
		assertThat(geodeticNumberValidator.isValidGeodeticNumber("392200556.12345"), is(true));
	}

	@Test
	public void shouldReturnFalseWhenIsValidGeodeticNumberCalledWithLt6DigitalDecimal() {
		// Given
		GeodeticNumberValidator geodeticNumberValidator = new GeodeticNumberValidator();
		// When
		// Then
		assertThat(geodeticNumberValidator.isValidGeodeticNumber("39220.1"), is(false));
		assertThat(geodeticNumberValidator.isValidGeodeticNumber("39220.12345"), is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenIsValidGeodeticNumberCalledWithInteger() {
		// Given
		GeodeticNumberValidator geodeticNumberValidator = new GeodeticNumberValidator();
		// When
		// Then
		assertThat(geodeticNumberValidator.isValidGeodeticNumber("100"), is(true));
		assertThat(geodeticNumberValidator.isValidGeodeticNumber("+100"), is(true));
		assertThat(geodeticNumberValidator.isValidGeodeticNumber("-100"), is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenIsValidGeodeticNumberCalledWithRubbish() {
		// Given
		GeodeticNumberValidator geodeticNumberValidator = new GeodeticNumberValidator();
		// When
		// Then
		assertThat(geodeticNumberValidator.isValidGeodeticNumber(""), is(false));
		assertThat(geodeticNumberValidator.isValidGeodeticNumber("-100a"), is(false));
	}
}

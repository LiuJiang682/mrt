package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.fields.SL4ColumnHeaders;

public class NorthingValidatorTest {

	@Test
	public void shouldReturnNoMessageWithCorrectNORTHING_MGA() {
		String[] datas = { "D", "KPDD001", "392200", "658960.1", "320", "210", "DD", "-90", "270" };
		List<String> headers = TestFixture.getColumnHeaderList();
		int lineNumber = 6;
		List<String> errorMessages = new ArrayList<>();
		// When
		new NorthingValidator(datas, lineNumber, headers, 
				SL4ColumnHeaders.NORTHING_MGA.getCode(), 
				SL4ColumnHeaders.NORTHING_AMG.getCode()).validate(errorMessages);
		// Then
		assertThat(errorMessages.size(), is(equalTo(0)));
	}
	
	@Test
	public void shouldReturnNoMessageWithCorrectNORTHING_AMG() {
		// Given
		String[] datas = { "D", "KPDD001", "392200", "658960.1", "320", "210", "DD", "-90", "270" };
		List<String> headers = TestFixture.getAMGColumnHeaderList();
		int lineNumber = 6;
		List<String> errorMessages = new ArrayList<>();
		// When
		new NorthingValidator(datas, lineNumber, headers, 
				SL4ColumnHeaders.NORTHING_MGA.getCode(), 
				SL4ColumnHeaders.NORTHING_AMG.getCode()).validate(errorMessages);
		// Then
		assertThat(errorMessages.size(), is(equalTo(0)));
	}
	
	@Test
	public void shouldReturnIncorrectNORTHING_MGAMessage() {
		String[] datas = { "D", "KPDD001", "392200", "65896.1", "320", "210", "DD", "-90", "270" };
		List<String> headers = TestFixture.getColumnHeaderList();
		int lineNumber = 6;
		List<String> errorMessages = new ArrayList<>();
		// When
		new NorthingValidator(datas, lineNumber, headers, 
				SL4ColumnHeaders.NORTHING_MGA.getCode(), 
				SL4ColumnHeaders.NORTHING_AMG.getCode()).validate(errorMessages);
		// Then
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), is(equalTo(
				"Line 6's Northing_MGA column must be either integer or has 6 digits before the decimal point! But got: 65896.1")));
	}
	
	@Test
	public void shouldReturnIncorrectNORTHING_AMGMessage() {
		// Given
		String[] datas = { "D", "KPDD001", "392200", "65896.1", "320", "210", "DD", "-90", "270" };
		List<String> headers = TestFixture.getAMGColumnHeaderList();
		int lineNumber = 6;
		List<String> errorMessages = new ArrayList<>();
		// When
		new NorthingValidator(datas, lineNumber, headers, 
				SL4ColumnHeaders.NORTHING_MGA.getCode(), 
				SL4ColumnHeaders.NORTHING_AMG.getCode()).validate(errorMessages);
		// Then
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), is(equalTo(
				"Line 6's Northing_AMG column must be either integer or has 6 digits before the decimal point! But got: 65896.1")));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenMGACodeIsNull() {
		// Given
		String[] datas = { "D", "KPDD001", "39220.1", "6589600", "320", "210", "DD", "-90", "270" };
		List<String> headers = TestFixture.getAMGColumnHeaderList();
		int lineNumber = 6;
		String mgaCode = null;
		String amgCode = null;
		// When
		new NorthingValidator(datas, lineNumber, headers, mgaCode, amgCode);
		fail("Program reached unexpected point!");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenAMGCodeIsNull() {
		// Given
		String[] datas = { "D", "KPDD001", "39220.1", "6589600", "320", "210", "DD", "-90", "270" };
		List<String> headers = TestFixture.getAMGColumnHeaderList();
		int lineNumber = 6;
		String mgaCode = SL4ColumnHeaders.EASTING_MGA.getCode();
		String amgCode = null;
		// When
		new NorthingValidator(datas, lineNumber, headers, mgaCode, amgCode);
		fail("Program reached unexpected point!");
	}

	@Test
	public void shouldReturnInstance() {
		// Given
		String[] datas = { "D", "KPDD001", "39220.1", "6589600", "320", "210", "DD", "-90", "270" };
		List<String> headers = TestFixture.getAMGColumnHeaderList();
		int lineNumber = 6;
		String mgaCode = SL4ColumnHeaders.EASTING_MGA.getCode();
		String amgCode = SL4ColumnHeaders.EASTING_AMG.getCode();
		// When
		NorthingValidator testInstance = new NorthingValidator(datas, lineNumber, headers, mgaCode, amgCode);
		// Then
		assertThat(testInstance, is(notNullValue()));
	}
}

package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.fields.SL4ColumnHeaders;

public class EastingValidatorTest {

	@Test
	public void shouldReturnNoMessageWithCorrectEASTING_MGA() {
		String[] datas = { "D", "KPDD001", "392200.1", "6589600", "320", "210", "DD", "-90", "270" };
		List<String> headers = TestFixture.getColumnHeaderList();
		int lineNumber = 6;
		List<String> errorMessages = new ArrayList<>();
		// When
		new EastingValidator(datas, lineNumber, headers, SL4ColumnHeaders.EASTING_MGA.getCode(),
				SL4ColumnHeaders.EASTING_AMG.getCode()).validate(errorMessages);
		// Then
		assertThat(errorMessages.size(), is(equalTo(Numeral.ZERO)));
	}

	@Test
	public void shouldReturnNoMessageWithCorrectEASTING_AMG() {
		String[] datas = { "D", "KPDD001", "392200.1", "6589600", "320", "210", "DD", "-90", "270" };
		List<String> headers = TestFixture.getAMGColumnHeaderList();
		int lineNumber = 6;
		List<String> errorMessages = new ArrayList<>();
		// When
		new EastingValidator(datas, lineNumber, headers, SL4ColumnHeaders.EASTING_MGA.getCode(),
				SL4ColumnHeaders.EASTING_AMG.getCode()).validate(errorMessages);
		// Then
		assertThat(errorMessages.size(), is(equalTo(0)));
	}

	@Test
	public void shouldReturnIncorrectEASTING_MGAMessage() {
		String[] datas = { "D", "KPDD001", "39220.1", "6589600", "320", "210", "DD", "-90", "270" };
		List<String> headers = TestFixture.getColumnHeaderList();
		int lineNumber = 6;
		List<String> errorMessages = new ArrayList<>();
		// When
		new EastingValidator(datas, lineNumber, headers, SL4ColumnHeaders.EASTING_MGA.getCode(),
				SL4ColumnHeaders.EASTING_AMG.getCode()).validate(errorMessages);
		// Then
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), is(equalTo(
				"Line 6's Easting_MGA column must be either integer or has 6 digits before the decimal point! But got: 39220.1")));
	}

	@Test
	public void shouldReturnIncorrectEASTING_AMGMessage() {
		String[] datas = { "D", "KPDD001", "39220.1", "6589600", "320", "210", "DD", "-90", "270" };
		List<String> headers = TestFixture.getAMGColumnHeaderList();
		int lineNumber = 6;
		List<String> errorMessages = new ArrayList<>();
		// When
		new EastingValidator(datas, lineNumber, headers, SL4ColumnHeaders.EASTING_MGA.getCode(),
				SL4ColumnHeaders.EASTING_AMG.getCode()).validate(errorMessages);
		// Then
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), is(equalTo(
				"Line 6's Easting_AMG column must be either integer or has 6 digits before the decimal point! But got: 39220.1")));
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
		new EastingValidator(datas, lineNumber, headers, mgaCode, amgCode);
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
		new EastingValidator(datas, lineNumber, headers, mgaCode, amgCode);
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
		EastingValidator testInstance = new EastingValidator(datas, lineNumber, headers, mgaCode, amgCode);
		// Then
		assertThat(testInstance, is(notNullValue()));
	}
}

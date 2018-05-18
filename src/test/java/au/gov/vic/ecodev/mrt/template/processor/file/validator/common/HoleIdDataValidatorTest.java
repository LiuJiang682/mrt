package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.fields.SL4ColumnHeaders;

public class HoleIdDataValidatorTest {

	@Test
	public void shouldReturnNoMessageWhenHoleIdProvided() {
		// Given
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "-90", "270" };
		int lineNumber = 6;
		MandatoryStringDataValidator testInstance = new MandatoryStringDataValidator(datas, 
				lineNumber, TestFixture.getColumnHeaderList(), 
				SL4ColumnHeaders.HOLE_ID.getCode(), Strings.TEMPLATE_NAME_SL4);
		List<String> messages = new ArrayList<>();
		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(true));
	}
	
	@Test
	public void shouldReturnMissHoleIdMessageWhenHoleIdIsNull() {
		// Given
		String[] datas = { "D", null, "392200", "6589600", "320", "210", "DD", "-90", "270" };
		int lineNumber = 6;
		MandatoryStringDataValidator testInstance = new MandatoryStringDataValidator(datas, 
				lineNumber, TestFixture.getColumnHeaderList(),  SL4ColumnHeaders.HOLE_ID.getCode(), 
				Strings.TEMPLATE_NAME_SL4);
		List<String> messages = new ArrayList<>();
		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line 6: Template SL4 column Hole_id cannot be null or empty")));
	}
	
	@Test
	public void shouldReturnMissingHoleIdMessageWhenHoleIdColumnHeaderIsNotProvided() {
		//Given
		MandatoryStringDataValidator testInstance = new MandatoryStringDataValidator(null, 0, 
				new ArrayList<String>(), SL4ColumnHeaders.HOLE_ID.getCode(), 
				Strings.TEMPLATE_NAME_SL4);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages, is(notNullValue()));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line 0: Template SL4 missing Hole_id column")));
	}
	
}

package au.gov.vic.ecodev.mrt.template.processor.file.validator.common.helper;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.model.sl4.Sl4Template;

public class H0100FieldHelperTest {

	@Test
	public void shouldPopulateTheTemplateParamMap() {
		//Given
		Template template = new Sl4Template();
		template.put(Strings.KEY_H0100, Arrays.asList("EL123", "EL456"));
		Map<String, List<String>> templateParamMap = new HashMap<>();
		//When
		new H0100FieldHelper().doTenementNoSplit(template, templateParamMap);
		//Then
		List<String> h0100Field = templateParamMap.get(Strings.KEY_H0100);
		assertThat(h0100Field, is(notNullValue()));
	}
	
	@Test
	public void shouldReturnTrueWhenHeaderIsTenement_no() {
		//Given
		String header = "Tenement_no";
		//When
		boolean flag = new H0100FieldHelper().isOneOfHeaders(header);
		//Then
		assertThat(flag, is(true));
	}
	
	@Test
	public void shouldReturnTrueWhenHeaderIsTenementSpaceNo() {
		//Given
		String header = "Tenement no";
		//When
		boolean flag = new H0100FieldHelper().isOneOfHeaders(header);
		//Then
		assertThat(flag, is(true));
	}
	
	@Test
	public void shouldReturnTrueWhenHeaderIsTenementHypenno() {
		//Given
		String header = "Tenement-no";
		//When
		boolean flag = new H0100FieldHelper().isOneOfHeaders(header);
		//Then
		assertThat(flag, is(true));
	}
	
	@Test
	public void shouldReturnTrueWhenHeaderIsTenement() {
		//Given
		String header = "Tenement";
		//When
		boolean flag = new H0100FieldHelper().isOneOfHeaders(header);
		//Then
		assertThat(flag, is(true));
	}
	
	@Test
	public void shouldReturnTrueWhenHeaderIsLicence() {
		//Given
		String header = "Licence";
		//When
		boolean flag = new H0100FieldHelper().isOneOfHeaders(header);
		//Then
		assertThat(flag, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenHeaderIsRubbish() {
		//Given
		String header = "Abc";
		//When
		boolean flag = new H0100FieldHelper().isOneOfHeaders(header);
		//Then
		assertThat(flag, is(false));
	}
	
	@Test
	public void shouldReturnIndexWhenHeaderContainsTenementNo() {
		//Given
		List<String> headers = new ArrayList<String>(TestFixture.getColumnHeaderList());
		headers.add("Tenement_no");
		//When
		int index = new H0100FieldHelper().getHeaderIndex(headers);
		//Then
		assertThat(index, is(equalTo(8)));
	}
	
	@Test
	public void shouldReturnNotFoundIndexWhenHeaderContainsNoTenementNo() {
		//Given
		List<String> headers = new ArrayList<String>(TestFixture.getColumnHeaderList());
		//When
		int index = new H0100FieldHelper().getHeaderIndex(headers);
		//Then
		assertThat(index, is(equalTo(-1)));
	}
}

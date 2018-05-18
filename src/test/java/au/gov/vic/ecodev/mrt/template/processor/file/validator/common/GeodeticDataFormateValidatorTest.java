package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

public class GeodeticDataFormateValidatorTest {

	@Test
	public void shouldReturnNoMessageWhenDataIsInteger() {
		//Given
		String[] datas = { "D", "KPDD001", "39220.1", "6589600", "320", "210", "DD", "-90", "270" };
		List<String> messages= new ArrayList<>();
		//When
		new GeodeticDataFormateValidator(datas, 6, "Easting", 2).validate(messages);
		//Then
		assertThat(CollectionUtils.isEmpty(messages), is(true));
	}
	
	@Test
	public void shouldReturnMessageWhenDataIsIncorrect() {
		//Given
		String[] datas = { "D", "KPDD001", "39220.1", "6589600", "320", "210", "DD", "-90", "270" };
		List<String> messages= new ArrayList<>();
		//When
		new GeodeticDataFormateValidator(datas, 6, "Easting", 1).validate(messages);
		//Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("Line 6's Easting column must be either integer or has 6 digits before the decimal point! But got: 39220.1")));
	}
}

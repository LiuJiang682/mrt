package au.gov.vic.ecodev.mrt.template.processor.updater.helper;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class LabelledColumnIndexListExtractorTest {

	@Test
	public void shouldReturnMapWhenGetDeplicatedColumnIndexReverseMapMethodCalled() {
		//Given
		Template mockTemplate = Mockito.mock(Template.class);
		List<String> headers = new ArrayList<>(TestFixture.getColumnHeaderListWithOptionalFields());
		headers.add("Au");
		when(mockTemplate.get(eq("H1000"))).thenReturn(headers);
		List<String> keys = Arrays.asList("H1000", "D1", "D2", "Duplicated_Au", "Duplicated_Zn");
		when(mockTemplate.getKeys()).thenReturn(keys);
		when(mockTemplate.get("Duplicated_Au")).thenReturn(Arrays.asList("5", "6"));
		when(mockTemplate.get("Duplicated_Zn")).thenReturn(Arrays.asList("11", "15", "16"));
		LabelledColumnIndexListExtractor testInstance = new LabelledColumnIndexListExtractor(mockTemplate);
		//When
		List<Integer> list = testInstance.getColumnIndexListByStartWith(Strings.KEY_PREFIX_DUPLICATED);
		//Then
		assertThat(list, is(notNullValue()));
		assertThat(list.size(), is(equalTo(5)));
		assertThat(list.get(0), is(equalTo(5)));
		assertThat(list.get(1), is(equalTo(6)));
		assertThat(list.get(2), is(equalTo(11)));
		assertThat(list.get(3), is(equalTo(15)));
		assertThat(list.get(4), is(equalTo(16)));
	}
}

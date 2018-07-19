package au.gov.vic.ecodev.common.util;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class StringListToFileListConvertorTest {

	@Test
	public void shouldReturnAListOfFileWhenListOfStringProvided() {
		// Given
		List<String> fileNames = Arrays.asList("abc");
		// When
		List<File> files = new StringListToFileListConvertor().convertToFile(fileNames);
		// Then
		assertThat(files, is(notNullValue()));
		assertThat(files.size(), is(equalTo(1)));
	}

	@Test
	public void shouldReturnEmptyListWhenListIsNull() {
		// Given
		List<String> fileNames = null;
		// When
		List<File> files = new StringListToFileListConvertor().convertToFile(fileNames);
		// Then
		assertThat(files, is(notNullValue()));
		assertThat(files.size(), is(equalTo(0)));
	}
}

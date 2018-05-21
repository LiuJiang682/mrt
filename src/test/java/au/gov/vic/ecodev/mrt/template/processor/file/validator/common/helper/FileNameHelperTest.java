package au.gov.vic.ecodev.mrt.template.processor.file.validator.common.helper;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

public class FileNameHelperTest {

	@Test
	public void shouldReturnTrueWhenFileNameContainsPartial() {
		//Given
		final String delim = System.getProperty("file.separator");
		String fileName = new StringBuilder("home")
				.append(delim)
				.append("user")
				.append(delim)
				.append("workspace")
				.append(delim)
				.append("mrt_eco123_partial.zip")
				.toString();
		
		//When
		boolean flag = new FileNameHelper(fileName).isPartailFileName();
		//Then
		assertThat(flag, is(true));
	}
	
	@Test
	public void shouldReturnTrueWhenSimpleFileNameContainsPartial() {
		//Given
		String fileName = "mrt_eco123_partial.zip";
		
		//When
		boolean flag = new FileNameHelper(fileName).isPartailFileName();
		//Then
		assertThat(flag, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenFileNameContainsPart() {
		//Given
		final String delim = System.getProperty("file.separator");
		String fileName = new StringBuilder("home")
				.append(delim)
				.append("user")
				.append(delim)
				.append("workspace")
				.append(delim)
				.append("mrt_eco123_part.zip")
				.toString();
		
		//When
		boolean flag = new FileNameHelper(fileName).isPartailFileName();
		//Then
		assertThat(flag, is(false));
	}
	
	@Test
	public void shouldReturnFalseWhenSimpleFileNameContainsPartial() {
		//Given
		String fileName = "mrt_eco123_part.zip";
		
		//When
		boolean flag = new FileNameHelper(fileName).isPartailFileName();
		//Then
		assertThat(flag, is(false));
	}
	
	@Test
	public void shouldReturnInstanceWhenFileNameIsProvided() {
		//Given
		String fileName = "abc";
		//When
		FileNameHelper testInstance = new FileNameHelper(fileName);
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenFileNameIsBlank() {
		//Given
		String fileName = null;
		//When
		new FileNameHelper(fileName);
		fail("Program reached unexpected point!");
	}
}

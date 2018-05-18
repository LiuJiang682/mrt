package au.gov.vic.ecodev.mrt.template.files;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

public class DirectoryFilesScannerTest {

	private DirectoryFilesScanner directoryFilesScanner;
	@Test
	public void shouldReturnInstance() {
		//Given
		givenTestInstance();
		//When
		//Then
		assertThat(directoryFilesScanner, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenDirectoryIsNull() {
		//Given
		String directory = null;
		//When
		new DirectoryFilesScanner(directory);
		fail("Program reached unexpected point!");
	}
	
	@Test
	public void shouldReturnListOfFiles() throws IOException {
		//Given
		givenTestInstance();
		//When
		List<File> files = directoryFilesScanner.scan();
		//Then
		assertThat(CollectionUtils.isEmpty(files), is(false));
	}
	
	private void givenTestInstance() {
		String directory = "src/test/resources";
		directoryFilesScanner = new DirectoryFilesScanner(directory);
	}
}

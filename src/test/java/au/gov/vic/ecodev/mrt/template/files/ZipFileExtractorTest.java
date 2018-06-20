package au.gov.vic.ecodev.mrt.template.files;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.assertj.core.util.Files;
import org.junit.Test;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;

public class ZipFileExtractorTest {

	private File file;
	private ZipFileExtractor zipFileExtractor;
	
	@Test
	public void shouldReturnInstance() throws IOException {
		//Given
		givenTestInstance();
		//When
		//Then
		assertThat(zipFileExtractor, is(notNullValue()));
		file.delete();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenFileIsNull() {
		//Given
		File file = null;
		//When
		new ZipFileExtractor(file);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenFileIsNotZipFile() {
		//Given
		File file = new File("my.txt");
		//When
		new ZipFileExtractor(file);
		fail("Program reached unexpected point!");
	}
	
	@Test
	public void shouldExtractFiles() throws IOException {
		//Given
		givenTestInstance();
		//When
		File extractedDir = zipFileExtractor.extract();
		//Then
		assertThat(extractedDir, is(notNullValue()));
		assertThat(extractedDir.isDirectory(), is(true));
		String[] files = extractedDir.list();
		assertThat(files.length, is(equalTo(1)));
		assertThat(files[0], is(equalTo("mytext.txt")));
		file.delete();
		Files.delete(extractedDir);
	}
	
	@Test
	public void shouldExtractFilesWithDirectory() throws IOException {
		//Given
		file = TestFixture.getDirectoryZipFile("src/test/resources/testData/my.zip");
		zipFileExtractor = new ZipFileExtractor(file);
		//When
		File extractedDir = zipFileExtractor.extract();
		//Then
		assertThat(extractedDir, is(notNullValue()));
		assertThat(extractedDir.isDirectory(), is(true));
		File[] files = extractedDir.listFiles();
		assertThat(files.length, is(equalTo(Numeral.ONE)));
		assertThat(files[Numeral.ZERO].isDirectory(), is(true));
		String[] subFiles = files[Numeral.ZERO].list();
		assertThat(subFiles.length, is(equalTo(Numeral.ONE)));
		assertThat(subFiles[0], is(equalTo("mytext.txt")));
		file.delete();
		Files.delete(extractedDir);
	}
	
	@Test
	public void shouldCreateDirectoryIfNotExist() {
		//Given
		file = new File("src/test/resources/testData/abc/my.zip");
		assertThat(file.exists(), is(false));
		assertThat(new File(file.getParent()).exists(), is(false));
		zipFileExtractor = new ZipFileExtractor(file);
		//When
		zipFileExtractor.createDirectoryIfNotExist(file.getParent());
		//Then
		assertThat(new File(file.getParent()).exists(), is(true));
		Files.delete(new File(file.getParent()));
	}
	
	private void givenTestInstance() throws IOException {
		file = TestFixture.getZipFile("src/test/resources/testData/my.zip");
		zipFileExtractor = new ZipFileExtractor(file);
	}
}

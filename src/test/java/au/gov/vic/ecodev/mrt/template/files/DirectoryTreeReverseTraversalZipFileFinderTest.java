package au.gov.vic.ecodev.mrt.template.files;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.File;

import org.assertj.core.util.Files;
import org.junit.Test;

import au.gov.vic.ecodev.mrt.fixture.TestFixture;

public class DirectoryTreeReverseTraversalZipFileFinderTest {

	private DirectoryTreeReverseTraversalZipFileFinder testInstance;

	@Test
	public void shouldFindZipFileFromParentDirectory() throws Exception {
		//Given
		File file = TestFixture.getDirectoryZipFile("src/test/resources/testData/my.zip");
		new ZipFileExtractor(file).extract();
		testInstance = new DirectoryTreeReverseTraversalZipFileFinder("src/test/resources/testData/my/abc");
		//When
		String zipFileName = testInstance.findZipFile();
		//Then
		File expectedFile = new File("src/test/resources/testData/my.zip");
		assertThat(zipFileName, is(equalTo(expectedFile.getAbsolutePath())));
		expectedFile.delete();
		Files.delete(new File("src/test/resources/testData/my"));
	}
	
	@Test
	public void shouldFindZipFileFromParentDirectoryWithoutDirectory() throws Exception {
		//Given
		File file = TestFixture.getZipFile("src/test/resources/testData/my.zip");
		new ZipFileExtractor(file).extract();
		testInstance = new DirectoryTreeReverseTraversalZipFileFinder("src/test/resources/testData/my");
		//When
		String zipFileName = testInstance.findZipFile();
		//Then
		File expectedFile = new File("src/test/resources/testData/my.zip");
		assertThat(zipFileName, is(equalTo(expectedFile.getAbsolutePath())));
		expectedFile.delete();
		Files.delete(new File("src/test/resources/testData/my"));
	}
	
	@Test
	public void shouldFindZipFileFromParentDirectoryWithOtherZipFiles() throws Exception {
		//Given
		File file = TestFixture.getDirectoryZipFile("src/test/resources/testData/xyz.zip");
		File myFile = TestFixture.getDirectoryZipFile("src/test/resources/testData/my.zip");
		new ZipFileExtractor(myFile).extract();
		testInstance = new DirectoryTreeReverseTraversalZipFileFinder("src/test/resources/testData/my/abc");
		//When
		String zipFileName = testInstance.findZipFile();
		//Then
		File expectedFile = new File("src/test/resources/testData/my.zip");
		assertThat(zipFileName, is(equalTo(expectedFile.getAbsolutePath())));
		expectedFile.delete();
		Files.delete(new File("src/test/resources/testData/my"));
		file.delete();
	}
	
	@Test
	public void shouldFindZipFileFromParentDirectoryWithoutDirectoryWithOtherZipFiles() throws Exception {
		//Given
		File file = TestFixture.getZipFile("src/test/resources/testData/xyz.zip");
		File myFile = TestFixture.getZipFile("src/test/resources/testData/my.zip");
		new ZipFileExtractor(myFile).extract();
		testInstance = new DirectoryTreeReverseTraversalZipFileFinder("src/test/resources/testData/my");
		//When
		String zipFileName = testInstance.findZipFile();
		//Then
		File expectedFile = new File("src/test/resources/testData/my.zip");
		assertThat(zipFileName, is(equalTo(expectedFile.getAbsolutePath())));
		expectedFile.delete();
		Files.delete(new File("src/test/resources/testData/my"));
		file.delete();
	}
	
	@Test
	public void shouldReturnInstance() {
		// Given
		String parentName = "src/test/resources";
		// When
		testInstance = new DirectoryTreeReverseTraversalZipFileFinder(parentName);
		// Then
		assertThat(testInstance, is(notNullValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenParentNameIsNull() {
		// Given
		String parentName = null;
		// When
		new DirectoryTreeReverseTraversalZipFileFinder(parentName);
		fail("Program reached unexpected point!");
	}
}

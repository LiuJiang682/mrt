package au.gov.vic.ecodev.mrt.template.files;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;

public class TemplateFileSelectorTest {

	private TemplateFileSelector testInstance;
	private String directory;
	
	@Test
	public void shouldReturnInstance() {
		//Given
		givenTestInstance();
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenDirectoryIsNull() {
		//Given
		String directory = null;
		//When
		testInstance = new TemplateFileSelector(directory);
		fail("Program reached unexpected point!");
	}
	
	@Test
	public void shouldReturnSL4TemplateFile() throws IOException {
		//Given
		givenTestInstance();
		//When
		Optional<List<String>> sl4TemplateFile = testInstance.getTemplateFileInDirectory(Arrays.asList(TemplateFileSelector.DATA_FORMAT_SL4));
		//Then
//		assertThat(sl4TemplateFile.get(), is(equalTo("SL4 EL5478_201702_01_Collar.txt")));
		List<String> sl4TemplateFileStrings = sl4TemplateFile.get();
		assertThat(sl4TemplateFileStrings.size(), is(equalTo(1)));
		String sl4TemplateFileString = sl4TemplateFileStrings.get(0);
		assertThat(sl4TemplateFileString, is(notNullValue()));
		String[] templateAndFile = sl4TemplateFileString.split(Strings.SPACE);
		assertThat(templateAndFile[Numeral.ZERO], is(equalTo("SL4")));
		File file = new File(directory + File.separator + "EL5478_201702_01_Collar.txt");
		assertThat(templateAndFile[Numeral.ONE], is(equalTo(file.getAbsolutePath())));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenTemplateListIsNull() throws IOException {
		//Given
		givenTestInstance();
		//When
		testInstance.getTemplateFileInDirectory(null);
		fail("Program reached unexpected point!");
	}
	
	
	@Test
	public void shouldReturnEmptyOptionWhenFileListIsEmpty() {
		//Given
		givenTestInstance();
		List<File> files = Collections.emptyList();
		String dataTemplate = "";
		//When
		Optional<List<String>> file = testInstance.findTemplateFileName(files , Arrays.asList(dataTemplate));
		//Then
		assertThat(file.isPresent(), is(false));
	}
	
	@Test
	public void shouldReturnEmptyOptionWhenDataTemplateIsNull() throws IOException {
		//Given
		givenTestInstance();
		List<File> files = TestFixture.getListOfFiles(directory);
		String dataTemplate = "";
		//When
		Optional<List<String>> file = testInstance.findTemplateFileName(files, Arrays.asList(dataTemplate));
		//Then
		assertThat(file.isPresent(), is(false));
	}
	
	@Test
	public void shouldReturnOptionWhenDataTemplateProvided() throws IOException {
		//Given
		givenTestInstance();
		List<File> files = TestFixture.getListOfFiles(directory);
		String dataTemplate = "SL4";
		//When
		Optional<List<String>> file = testInstance.findTemplateFileName(files, Arrays.asList(dataTemplate));
		//Then
		assertThat(file.isPresent(), is(true));
	}

	private void givenTestInstance() {
		directory = "src/test/resources/template";
		testInstance = new TemplateFileSelector(directory);
	}
 }

package au.gov.vic.ecodev.mrt.template.files;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.fixture.TestFixture;

public class FileMoverTest {

	private List<File> mockFiles;
	private FileMover fileMover;
	private File file;
	
	@Test
	public void shouldReturnInstance() {
		//Given
		givenTestInstance();
		//When
		//Then
		assertThat(fileMover, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseException() {
		//Given
		List<File> mockFiles = null;
		//When
		new FileMover(mockFiles);
		fail("Program reached unexpected point!");
	}
	
	@Test
	public void shouldReturnEmptyListWhenTheDestinationIsNullAndOverwrittenIsFalse() {
		//Given
		givenTestInstance();
		String destination = null;
		//When
		List<File> newFiles = fileMover.moveFile(destination, false);
		assertThat(newFiles.isEmpty(), is(true));
	}
	
	@Test
	public void shouldReturnEmptyListWhenTheDestinationIsNullAndOverwrittenIsTrue() {
		//Given
		givenTestInstance();
		String destination = null;
		//When
		List<File> newFiles = fileMover.moveFile(destination, true);
		assertThat(newFiles.isEmpty(), is(true));
	}
	
	@Test
	public void shouldReturnEmptyListWhenTheDestinationIsEmptyAndOverwrittenIsFalse() {
		//Given
		givenTestInstance();
		String destination = "";
		//When
		List<File> newFiles = fileMover.moveFile(destination, false);
		assertThat(newFiles.isEmpty(), is(true));
	}
	
	@Test
	public void shouldReturnEmptyListWhenTheDestinationIsEmptyAndOverwrittenIsTrue() {
		//Given
		givenTestInstance();
		String destination = "";
		//When
		List<File> newFiles = fileMover.moveFile(destination, true);
		assertThat(newFiles.isEmpty(), is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenTheDestinationIsNotWritableAndOverwrittenIsFalse() {
		//Given
		givenTestInstance();
		String destination = "/opt/abc/def";
		//When
		List<File> newFiles = fileMover.moveFile(destination, false);
		assertThat(newFiles.isEmpty(), is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenTheDestinationIsNotWritableAndOverwrittenIsTrue() {
		//Given
		givenTestInstance();
		String destination = "/opt/abc/def";
		//When
		List<File> newFiles = fileMover.moveFile(destination, true);
		assertThat(newFiles.isEmpty(), is(true));
	}
	
	@Test
	public void shouldReturnTrueWhenTheDestinationProvided() throws IOException, InterruptedException {
		//Given
		File movedFile = givenMovingFile();
		//When
		//Then
		movedFile.delete();
	}
	
	@Test
	public void shouldReturnFalseWhenTheFileExistedInDestinationAndOverwrittenIsFalse() throws IOException {
		//Given
		File movedFile = givenMovingFile();
		fileMover = new FileMover(givenFiles());
		String destination = "src/test/resources/testData/extract";
		//When
		List<File> newFiles = fileMover.moveFile(destination, false);
		//Then
		assertThat(newFiles.isEmpty(), is(true));
		movedFile.delete();
		if ((null != file) 
				&& (file.exists())) {
			file.delete();
		}
	}
	
	@Test
	public void shouldReturnFalseWhenTheFileExistedInDestinationAndOverwrittenIsTrue() throws IOException {
		//Given
		File movedFile = givenMovingFile();
		fileMover = new FileMover(givenFiles());
		String destination = "src/test/resources/testData/extract";
		//When
		List<File> newFiles = fileMover.moveFile(destination, true);
		//Then
		assertThat(newFiles.isEmpty(), is(false));
		movedFile.delete();
	}

	private File givenMovingFile() throws IOException {
		fileMover = new FileMover(givenFiles());
		String destination = "src/test/resources/testData/extract";
		
		List<File> newFiles = fileMover.moveFile(destination, false);
		assertThat(newFiles.isEmpty(), is(false));
		File movedFile = new File(destination + File.separator + "testFile.txt");
		assertThat(file.exists(), is(false));
		assertThat(movedFile.exists(), is(true));
		return movedFile;
	}
	
	private List<File> givenFiles() throws IOException {
		List<File> files = new ArrayList<>();
		file = TestFixture.getFile("src/test/resources/testData/testFile.txt");
		assertThat(file.exists(), is(true));
		files.add(file);
		return files;
	}

	@SuppressWarnings("unchecked")
	private void givenTestInstance() {
		mockFiles = Mockito.mock(List.class);
		//When
		fileMover = new FileMover(mockFiles);
	}
}

package au.gov.vic.ecodev.mrt.template.files;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
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
	public void shouldReturnEmptyListWhenTheDestinationIsNull() {
		//Given
		givenTestInstance();
		String destination = null;
		//When
		List<File> newFiles = fileMover.moveFile(destination);
		assertThat(newFiles.isEmpty(), is(true));
	}
	
	@Test
	public void shouldReturnEmptyListWhenTheDestinationIsEmpty() {
		//Given
		givenTestInstance();
		String destination = "";
		//When
		List<File> newFiles = fileMover.moveFile(destination);
		assertThat(newFiles.isEmpty(), is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenTheDestinationIsNotWritable() {
		//Given
		givenTestInstance();
		String destination = "/opt/abc/def";
		//When
		List<File> newFiles = fileMover.moveFile(destination);
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
	
	@Ignore
	@Test
	public void shouldReturnFalseWhenTheFileExistedInDestination() throws IOException {
		//Given
		File movedFile = givenMovingFile();
		fileMover = new FileMover(givenFiles());
		String destination = "src/test/resources/testData/extract";
		//When
		List<File> newFiles = fileMover.moveFile(destination);
		//Then
		assertThat(newFiles.isEmpty(), is(true));
		movedFile.delete();
		if ((null != file) 
				&& (file.exists())) {
			file.delete();
		}
	}

	private File givenMovingFile() throws IOException {
		fileMover = new FileMover(givenFiles());
		String destination = "src/test/resources/testData/extract";
		
		List<File> newFiles = fileMover.moveFile(destination);
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

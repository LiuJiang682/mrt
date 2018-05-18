package au.gov.vic.ecodev.mrt.template.loader.fsm.helpers;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.template.loader.fsm.model.DefaultMessage;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.Message;

public class ContextMessageHelperTest {

	private Message message;
	private ContextMessageHelper testInstance;

	@Test
	public void shouldAddedSuccessFileToMessage() {
		// Given
		givenTestInstance();
		String fileName = "my.zip";
		// When
		boolean flag = testInstance.addSuccessFiles(fileName);
		assertThat(flag, is(true));
		List<File> passedFiles = message.getPassedFiles();
		assertThat(CollectionUtils.isEmpty(passedFiles), is(false));
		assertThat(passedFiles.size(), is(equalTo(1)));
		assertThat(passedFiles.get(0).getName(), is(equalTo("my.zip")));
	}

	@Test
	public void shouldAddedFailedFileToMessage() {
		// Given
		givenTestInstance();
		String fileName = "my.zip";
		// When
		boolean flag = testInstance.addFailedFiles(fileName);
		// Then
		assertThat(flag, is(true));
		List<File> failedFiles = message.getFailedFiles();
		assertThat(CollectionUtils.isEmpty(failedFiles), is(false));
		assertThat(failedFiles.size(), is(equalTo(1)));
		assertThat(failedFiles.get(0).getName(), is(equalTo("my.zip")));
	}
	
	@Test
	public void shouldAddedFailedFileToMessageAndRemoveFromSuccessList() {
		// Given
		givenTestInstance();
		String fileName = "my.zip";
		boolean flag = testInstance.addSuccessFiles(fileName);
		assertThat(flag, is(true));
		List<File> passedFiles = message.getPassedFiles();
		assertThat(CollectionUtils.isEmpty(passedFiles), is(false));
		assertThat(passedFiles.size(), is(equalTo(1)));
		assertThat(passedFiles.get(0).getName(), is(equalTo("my.zip")));
		// When
		boolean failedFileFlag = testInstance.addFailedFiles(fileName);
		// Then
		assertThat(failedFileFlag, is(true));
		List<File> failedFiles = message.getFailedFiles();
		assertThat(CollectionUtils.isEmpty(failedFiles), is(false));
		assertThat(failedFiles.size(), is(equalTo(1)));
		assertThat(failedFiles.get(0).getName(), is(equalTo("my.zip")));
		List<File> emptyPassedFiles = message.getPassedFiles();
		assertThat(CollectionUtils.isEmpty(emptyPassedFiles), is(true));
	}
	
	@Test
	public void shouldIgnorePassedFileToMessageWhenFileAlreadyExistInFailedList() {
		// Given
		givenTestInstance();
		String fileName = "my.zip";
		boolean flag = testInstance.addFailedFiles(fileName);
		assertThat(flag, is(true));
		List<File> failedFiles = message.getFailedFiles();
		assertThat(CollectionUtils.isEmpty(failedFiles), is(false));
		assertThat(failedFiles.size(), is(equalTo(1)));
		assertThat(failedFiles.get(0).getName(), is(equalTo("my.zip")));
		// When
		boolean passedFileFlag = testInstance.addSuccessFiles(fileName);
		// Then
		assertThat(passedFileFlag, is(true));
		List<File> passedFiles = message.getPassedFiles();
		assertThat(CollectionUtils.isEmpty(passedFiles), is(true));
	}

	@Test
	public void shouldReturnFileWithZipFileExtensionWhenNotZipFileExtensionProvided() {
		// Given
		givenTestInstance();
		String fileName = "my";
		// When
		File file = testInstance.getFile(fileName);
		// Then
		assertThat(file, is(notNullValue()));
		assertThat(file.getName(), is(equalTo("my.zip")));
	}

	@Test
	public void shouldReturnFileWithZipFileExtensionWhenZipFileExtensionProvided() {
		// Given
		givenTestInstance();
		String fileName = "my.zip";
		// When
		File file = testInstance.getFile(fileName);
		// Then
		assertThat(file, is(notNullValue()));
		assertThat(file.getName(), is(equalTo("my.zip")));
	}

	@Test
	public void shouldReturnInstance() {
		// Given
		givenTestInstance();
		// When
		// Then
		assertThat(testInstance, is(notNullValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenMessageIsNull() {
		// Given
		Message message = null;
		// When
		new ContextMessageHelper(message);
		fail("Program reached unexpected point1");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenFileIsNullWhenGetFileCalled() {
		// Given
		givenTestInstance();
		String fileName = null;
		// When
		testInstance.getFile(fileName);
		fail("Program reached unexpected point1");
	}

	private void givenTestInstance() {
		message = new DefaultMessage();
		testInstance = new ContextMessageHelper(message);
	}
}

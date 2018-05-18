package au.gov.vic.ecodev.mrt.template.files;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.constants.LogSeverity;
import au.gov.vic.ecodev.mrt.template.processor.services.PersistentServices;

public class LogFileGeneratorTest {

	private LogFileGenerator testInstance;
	private PersistentServices mockPersistentServices;

	@Test
	public void shouldReturnIntstance() {
		// Given
		givenTestInstance();
		// When
		// Then
		assertThat(testInstance, is(notNullValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenBatchIdIsLessThenZero() {
		// Given
		int batchId = 0;
		PersistentServices persistentServices = Mockito.mock(PersistentServices.class);
		String logFileOutputDirectory = "src/test/resource/generated/log";
		// When
		new LogFileGenerator(batchId, persistentServices, logFileOutputDirectory);
		fail("Program reached unexpected point!");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenStatusLogDaoIsNull() {
		// Given
		int batchId = 1;
		PersistentServices persistentServices = null;
		String logFileOutputDirectory = "src/test/resource/generated/log";
		// When
		new LogFileGenerator(batchId, persistentServices, logFileOutputDirectory);
		fail("Program reached unexpected point!");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenOutputDirectoryIsEmpty() {
		// Given
		int batchId = 1;
		PersistentServices persistentServices = Mockito.mock(PersistentServices.class);
		String logFileOutputDirectory = null;
		// When
		new LogFileGenerator(batchId, persistentServices, logFileOutputDirectory);
		fail("Program reached unexpected point!");
	}

	@Test
	public void shouldGenerateLogFile() {
		// Given
		givenTestInstance();
		List<String> msgs = new ArrayList<>();
		msgs.add("Line number 6 : H0106 must be a number!");
		when(mockPersistentServices.getErrorMessageByBatchId(Matchers.anyInt(), 
				Matchers.any(LogSeverity.class))).thenReturn(msgs);
		// When
		String reason = testInstance.generateLogs();
		// Then
		assertThat(reason, is(nullValue()));
		File file = new File("src/test/resources/generated/log/Batch_1.log");
		file.delete();
	}

	@Test
	public void shouldNotCreateFileWhenDirectoryIsNotExist() {
		// Given
		int batchId = 1;
		mockPersistentServices = Mockito.mock(PersistentServices.class);
		String logFileOutputDirectory = "src/test/resource/generated/log";
		testInstance = new LogFileGenerator(batchId, mockPersistentServices, logFileOutputDirectory);
		// When
		String reason = testInstance.generateLogs();
		// Then
		assertThat(reason, is(equalTo("The system cannot find the path specified")));
		assertThat(new File("src/test/resource/generated/log/Batch_1.log").exists(), is(false));
		verify(mockPersistentServices, times(0)).getErrorMessageByBatchId(Matchers.anyInt(),
				Matchers.any(LogSeverity.class));
	}

	private void givenTestInstance() {
		int batchId = 1;
		mockPersistentServices = Mockito.mock(PersistentServices.class);
		String logFileOutputDirectory = "src/test/resources/generated/log";
		testInstance = new LogFileGenerator(batchId, mockPersistentServices, logFileOutputDirectory);
	}
}

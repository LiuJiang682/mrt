package au.gov.vic.ecodev.mrt.data.record.cleaner;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.config.MrtConfigProperties;
import au.gov.vic.ecodev.mrt.data.record.cleaner.persistent.model.Session;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;

public class FileSessionCleanerTest {

	private FileSessionCleaner testInstance;
	private MrtConfigProperties mockMrtConfigProperties;
	
	@Test
	public void shouldDeleteRelatedFileDirectory() throws IOException {
		File file = null;
		try {
			//Given
			file = new File("src/test/resources/failed/100");
			if (!file.exists()) {
				file.mkdirs();
			}
			givenTestInstance();
			when(mockMrtConfigProperties.getFailedFileDirectory()).thenReturn("src/test/resources/failed");
			//When
			testInstance.clean();
			//Then
			assertThat(file.exists(), is(false));
		}
		finally {
			if ((null != file) 
					&&(file.exists())) {
				file.delete();
			}
		}
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		givenTestInstance();
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenSessionIsNull() {
		//Given
		Session session = null;
		//When
		new FileSessionCleaner(session, mockMrtConfigProperties);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenMrtConfigPropertiesIsNull() {
		//Given
		Session session = TestFixture.getMrtSession();
		//When
		new FileSessionCleaner(session, mockMrtConfigProperties);
		fail("Program reached unexpected point!");
	}

	private void givenTestInstance() {
		mockMrtConfigProperties = Mockito.mock(MrtConfigProperties.class);
		testInstance = new FileSessionCleaner(TestFixture.getMrtSession(), 
				mockMrtConfigProperties);
	}
}

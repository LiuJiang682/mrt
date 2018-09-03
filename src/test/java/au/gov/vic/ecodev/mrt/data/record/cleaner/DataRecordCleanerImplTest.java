package au.gov.vic.ecodev.mrt.data.record.cleaner;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class DataRecordCleanerImplTest {

	@Autowired
	private DataRecordCleanerImpl testInstance;
	
	@Test
	public void shouldRunClear() {
		//Given
		//When
		testInstance.clean();
		//Then
		assertThat(true, is(true));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
}

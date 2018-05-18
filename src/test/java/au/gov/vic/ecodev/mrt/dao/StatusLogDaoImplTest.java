package au.gov.vic.ecodev.mrt.dao;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.LogSeverity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class StatusLogDaoImplTest {

	@Autowired
	private StatusLogDao statusLogDao;
	
	@Test
	public void shouldBeInstantiatedDao() {
		//Given
		//When
		//Then
		assertThat(statusLogDao, is(notNullValue()));
	}
	
	@Test
	public void shouldReturnListOfErrorMessages() {
		//Given
		long batchId = 1l;
		//When
		List<String> errorMsgs = statusLogDao.getErrorMessageByBatchId(batchId, LogSeverity.ERROR);
		//Then
		assertThat(CollectionUtils.isEmpty(errorMsgs), is(false));
		String errorMsg = errorMsgs.get(0);
		assertThat(errorMsg, is(equalTo("Line number 6 : H0106 must be a number!")));
	}
	
	@Test
	public void shouldSaveStatusLog() {
		//Given
		int batchId = 1;
		String message = "abc";
		List<String> errorMsgs = statusLogDao.getErrorMessageByBatchId(batchId, LogSeverity.ERROR);
		int count = errorMsgs.size();
		//When
		boolean saved = statusLogDao.saveStatusLog(batchId, LogSeverity.ERROR, message);
		//Then
		assertThat(saved, is(true));
		List<String> addedErrorMsgs = statusLogDao.getErrorMessageByBatchId(batchId, LogSeverity.ERROR);
		assertThat(addedErrorMsgs.size(), is(equalTo(count + 1)));
	}
	
}

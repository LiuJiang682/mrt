package au.gov.vic.ecodev.mrt.dao;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import java.sql.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import au.gov.vic.ecodev.common.util.IDGenerator;
import au.gov.vic.ecodev.mrt.model.SessionHeader;
import au.gov.vic.ecodev.mrt.model.fields.SessionStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SessionHeaderDaoImplTest {

	@Autowired
	private SessionHeaderDao sessionHeaderDao;
	
	@Test
	public void shouldBeInstantiatedDao() {
		//Given
		//When
		//Then
		assertThat(sessionHeaderDao, is(notNullValue()));
	}
	
	@Test
	public void shouldSaveAndRetrieveNewRecord() {
		//Given
		long sessionId = IDGenerator.getUID().longValue();
		SessionHeader sessionHeader = new SessionHeader(sessionId);
		sessionHeader.setTemplate("MRT");
		sessionHeader.setFileName("FILE_NAME");
		Date date = new Date(System.currentTimeMillis());
		sessionHeader.setProcessDate(date);
		sessionHeader.setTenement("TENEMENT");
		sessionHeader.setTenementHolder("TENEMENT_HOLDER");
		sessionHeader.setReportingDate(date);
		sessionHeader.setProjectName("PROJECT_NAME");
		sessionHeader.setStatus(SessionStatus.RUNNING);
		sessionHeader.setComments("COMMENTS");
		sessionHeader.setEmailSent("N");
		//When
		boolean flag = sessionHeaderDao.updateOrSave(sessionHeader);
		//Then
		assertThat(flag, is(true));
		
		SessionHeader retrievedSessionHeader = (SessionHeader) sessionHeaderDao.get(sessionId);
		//Then
		assertThat(retrievedSessionHeader, is(notNullValue()));
		assertThat(sessionHeader.getSessionId(), is(equalTo(sessionId)));
		assertThat(sessionHeader.getTemplate(), is(equalTo("MRT")));
		assertThat(sessionHeader.getFileName(), is(equalTo("FILE_NAME")));
		assertThat(sessionHeader.getProcessDate(), is(equalTo(date)));
		assertThat(sessionHeader.getTenement(),  is(equalTo("TENEMENT")));
		assertThat(sessionHeader.getTenementHolder(), is(equalTo("TENEMENT_HOLDER")));
		assertThat(sessionHeader.getReportingDate(), is(equalTo(date)));
		assertThat(sessionHeader.getProjectName(), is(equalTo("PROJECT_NAME")));
		assertThat(sessionHeader.getStatus().name(), is(equalTo("RUNNING")));
		assertThat(sessionHeader.getComments(), is(equalTo("COMMENTS")));
		assertThat(sessionHeader.getEmailSent(), is(equalTo("N")));
		
		sessionHeader.setStatus(SessionStatus.COMPLETED);
		boolean updateFlag = sessionHeaderDao.updateOrSave(sessionHeader);
		assertThat(updateFlag, is(true));
		SessionHeader completedSessionHeader = (SessionHeader) sessionHeaderDao.get(sessionId);
		assertThat(completedSessionHeader.getStatus(), is(equalTo(SessionStatus.COMPLETED)));
	}
	
	@Test
	public void shouldSaveAndRetrieveNewRecordWheSqlInjected() {
		//Given
		long sessionId = IDGenerator.getUID().longValue();
		SessionHeader sessionHeader = new SessionHeader(sessionId);
		sessionHeader.setTemplate("MRT");
		sessionHeader.setFileName("FILE_NAME");
		Date date = new Date(System.currentTimeMillis());
		sessionHeader.setProcessDate(date);
		sessionHeader.setTenement("TENEMENT");
		sessionHeader.setTenementHolder("TENEMENT_HOLDER");
		sessionHeader.setReportingDate(date);
		sessionHeader.setProjectName("PROJECT_NAME");
		sessionHeader.setStatus(SessionStatus.RUNNING);
		sessionHeader.setComments("COMMENTS");
		sessionHeader.setEmailSent("N");
		//When
		boolean flag = sessionHeaderDao.updateOrSave(sessionHeader);
		//Then
		assertThat(flag, is(true));
		
		SessionHeader retrievedSessionHeader = (SessionHeader) sessionHeaderDao.get(sessionId);
		retrievedSessionHeader.setComments("COMMENTS'; DROP TABLE SESSION_HEADER; -- '");
		flag = sessionHeaderDao.updateOrSave(retrievedSessionHeader);
		assertThat(flag, is(true));
		
		SessionHeader sqlInjected = (SessionHeader) sessionHeaderDao.get(sessionId);
		//Then
		assertThat(sqlInjected, is(notNullValue()));
		assertThat(sqlInjected.getSessionId(), is(equalTo(sessionId)));
		assertThat(sqlInjected.getTemplate(), is(equalTo("MRT")));
		assertThat(sqlInjected.getFileName(), is(equalTo("FILE_NAME")));
//		assertThat(sqlInjected.getProcessDate(), is(equalTo(date)));
		assertThat(sqlInjected.getTenement(),  is(equalTo("TENEMENT")));
		assertThat(sqlInjected.getTenementHolder(), is(equalTo("TENEMENT_HOLDER")));
//		assertThat(sqlInjected.getReportingDate(), is(equalTo(date)));
		assertThat(sqlInjected.getProjectName(), is(equalTo("PROJECT_NAME")));
		assertThat(sqlInjected.getStatus().name(), is(equalTo("RUNNING")));
		assertThat(sqlInjected.getComments(), is(equalTo("COMMENTS'; DROP TABLE SESSION_HEADER; -- '")));
		assertThat(sqlInjected.getEmailSent(), is(equalTo("N")));
	}
	
	@Test
	public void shouldReturnNullWhenIdIsRubbish() {
		//Given
		long sessionId = 10l;
		//When
		SessionHeader sessionHeader = (SessionHeader) sessionHeaderDao.get(sessionId);
		//Then
		assertThat(sessionHeader, is(nullValue()));
	}
}

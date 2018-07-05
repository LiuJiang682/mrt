package au.gov.vic.ecodev.mrt.template.processor.services;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import au.gov.vic.ecodev.mrt.constants.LogSeverity;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.dao.SessionHeaderDao;
import au.gov.vic.ecodev.mrt.dao.SessionHeaderDaoImpl;
import au.gov.vic.ecodev.mrt.dao.StatusLogDao;
import au.gov.vic.ecodev.mrt.dao.TemplateConfigDao;
import au.gov.vic.ecodev.mrt.dao.TemplateContextPropertiesDao;
import au.gov.vic.ecodev.mrt.dao.TemplateUpdaterConfigDao;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.model.SessionHeader;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.model.sl4.Sl4Template;
import au.gov.vic.ecodev.mrt.template.processor.persistent.Dao;
import au.gov.vic.ecodev.mrt.template.processor.update.TemplateUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.TemplateUpdaterFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TemplateUpdaterFactory.class)
public class DbPersistentServicesTest {

	private DbPersistentServices dbPersistentServices;
	private StatusLogDao mockStatusLogDao;
	private SessionHeaderDao mockSessionHeaderDao;
	private TemplateUpdaterConfigDao mockTemplateUpdaterConfigDao;
	private TemplateContextPropertiesDao mockTemplateContextPropertiesDao;
	private TemplateConfigDao mockTemplateConfigDao;
	
	@Test
	public void shouldReturnInstance() {
		//Given
		givenTestInstance();
		//When
		//Then
		assertThat(dbPersistentServices, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenTemplateConfigDaoIsNull() {
		//Given
		TemplateConfigDao templateConfigDao = null;
		StatusLogDao statusLogDao = Mockito.mock(StatusLogDao.class);
		SessionHeaderDao sessionHeaderDao = null;
		TemplateUpdaterConfigDao templateUpdaterConfigDao = null;
		TemplateContextPropertiesDao templateContextPropertiesDao = null;
		//When
		new DbPersistentServices(templateConfigDao, statusLogDao, 
				sessionHeaderDao, templateUpdaterConfigDao,
				templateContextPropertiesDao);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenStatusLogDaoIsNull() {
		//Given
		TemplateConfigDao templateConfigDao = Mockito.mock(TemplateConfigDao.class);
		StatusLogDao statusLogDao = null;
		SessionHeaderDao sessionHeaderDao = null;
		TemplateUpdaterConfigDao templateUpdaterConfigDao = null;
		TemplateContextPropertiesDao templateContextPropertiesDao = null;
		//When
		new DbPersistentServices(templateConfigDao, statusLogDao, 
				sessionHeaderDao, templateUpdaterConfigDao,
				templateContextPropertiesDao);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenBatchIdDaoIsNull() {
		//Given
		TemplateConfigDao templateConfigDao = Mockito.mock(TemplateConfigDao.class);
		StatusLogDao statusLogDao = Mockito.mock(StatusLogDao.class);
		SessionHeaderDao sessionHeaderDao = null;
		TemplateUpdaterConfigDao templateUpdaterConfigDao = null;
		TemplateContextPropertiesDao templateContextPropertiesDao = null;
		//When
		new DbPersistentServices(templateConfigDao, statusLogDao, 
				sessionHeaderDao, templateUpdaterConfigDao,
				templateContextPropertiesDao);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenTemplateUpdaterConfigDaoIsNull() {
		//Given
		TemplateConfigDao templateConfigDao = Mockito.mock(TemplateConfigDao.class);
		StatusLogDao statusLogDao = Mockito.mock(StatusLogDao.class);
		SessionHeaderDao sessionHeaderDao = Mockito.mock(SessionHeaderDao.class);
		TemplateUpdaterConfigDao templateUpdaterConfigDao = null;
		TemplateContextPropertiesDao templateContextPropertiesDao = null;
		//When
		new DbPersistentServices(templateConfigDao, statusLogDao, 
				sessionHeaderDao, templateUpdaterConfigDao,
				templateContextPropertiesDao);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenTemplateContextPropertiesDaoIsNull() {
		//Given
		TemplateConfigDao templateConfigDao = Mockito.mock(TemplateConfigDao.class);
		StatusLogDao statusLogDao = Mockito.mock(StatusLogDao.class);
		SessionHeaderDao sessionHeaderDao = Mockito.mock(SessionHeaderDao.class);
		TemplateUpdaterConfigDao templateUpdaterConfigDao = Mockito.mock(TemplateUpdaterConfigDao.class);
		TemplateContextPropertiesDao templateContextPropertiesDao = null;
		//When
		new DbPersistentServices(templateConfigDao, statusLogDao, 
				sessionHeaderDao, templateUpdaterConfigDao,
				templateContextPropertiesDao);
		fail("Program reached unexpected point!");
	}
	
	@Test
	public void shouldSaveStatusLog() {
		//Given
		givenTestInstance();
		int batchId = 1;
		String logMessage = "abvc";
		when(mockStatusLogDao.saveStatusLog(Matchers.anyInt(), Matchers.any(LogSeverity.class),
				Matchers.anyString())).thenReturn(true);
		//When
		boolean flag = dbPersistentServices.saveStatusLog(batchId, LogSeverity.ERROR, logMessage);
		//Then
		assertThat(flag, is(true));
	}
	
	@Test
	public void shouldNotSaveErrorLog() {
		// Given
		givenTestInstance();
		int batchId = 1;
		String logMessage = "abvc";
		when(mockStatusLogDao.saveStatusLog(Matchers.anyInt(), Matchers.any(LogSeverity.class),
				Matchers.anyString())).thenReturn(false);
		// When
		boolean flag = dbPersistentServices.saveStatusLog(batchId, LogSeverity.ERROR, logMessage);
		// Then
		assertThat(flag, is(false));
	}
	
	@Test
	public void shouldReturnNextBatchId() {
		// Given
		givenTestInstance();
		when(mockSessionHeaderDao.updateOrSave(Matchers.any(SessionHeader.class))).thenReturn(true);
		// When
		Long nextBatchId = dbPersistentServices.getNextBatchId(Arrays.asList("mrt"), Arrays.asList("MRT_EL123.zip"));
		// Then
		assertThat(Numeral.NEGATIVE_ONE == nextBatchId, is(false));
	}
	
	@Test
	public void shouldReturnSavingFailedAsNextBatchIdWhenSavingSessionHeaderFailed() {
		// Given
		givenTestInstance();
		when(mockSessionHeaderDao.updateOrSave(Matchers.any(SessionHeader.class))).thenReturn(false);
		// When
		Long nextBatchId = dbPersistentServices.getNextBatchId(Arrays.asList("mrt"), Arrays.asList("MRT_EL123.zip"));
		// Then
		assertThat(Numeral.NEGATIVE_ONE == nextBatchId, is(true));
	}
	
	@Test
	public void shouldSaveDataBean() throws Exception {
		// Given
		givenTestInstance();
		long batchId = 1L;
		Template template = new Sl4Template();
		template.put("abc", Arrays.asList(new String[] {"def"}));
		template.put("ghi", Arrays.asList(new String[] {"jkl"}));
		JdbcTemplate mockJdbcTemplate = Mockito.mock(JdbcTemplate.class);
		PowerMockito.mockStatic(TemplateUpdaterFactory.class);
		TemplateUpdater mockTemplateUpdater = Mockito.mock(TemplateUpdater.class);
		PowerMockito.doReturn(mockTemplateUpdater).when(TemplateUpdaterFactory.class, "getTemplateUpdater", eq(template), Matchers.any(PersistentServices.class));
		List<Class<? extends Dao>> daos = new ArrayList<>();
		daos.add(SessionHeaderDaoImpl.class);
		when(mockTemplateUpdater.getDaoClasses()).thenReturn(daos);
		// When
		boolean flag = dbPersistentServices.saveDataBean(mockJdbcTemplate, batchId, template);
		// Then
		assertThat(flag, is(true));
		verify(mockTemplateUpdater).getDaoClasses();
		verify(mockTemplateUpdater).setDaos(Matchers.anyListOf(Dao.class));
		verify(mockTemplateUpdater).update(Matchers.anyLong(), eq(template));
	}
	
	@Test
	public void shouldReturnTemplateOwnerEmail() {
		// Given
		givenTestInstance();
		String templateName = "mrt";
		Map<String, Object> emailProps = TestFixture.getEmailProperties();
		when(mockTemplateConfigDao.getOwnerEmailProperties(eq("mrt"))).thenReturn(emailProps);
		// When
		Map<String, Object> email = dbPersistentServices.getTemplateOwnerEmail(templateName);
		// Then
		assertThat(email, is(notNullValue()));
		assertThat(email.size(), is(equalTo(2)));
		assertThat(email.get("OWNER_EMAILS"), is(equalTo("jiang.liu@ecodev.vic.gov.au")));
		assertThat(email.get("EMAILS_BUILDER"), is(equalTo("au.gov.vic.ecodev.mrt.mail.MrtEmailBodyBuilder")));
		ArgumentCaptor<String> templateNameCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockTemplateConfigDao).getOwnerEmailProperties(templateNameCaptor.capture());
		assertThat(templateNameCaptor.getValue(), is(equalTo("mrt")));
	}

	private void givenTestInstance() {
		mockTemplateConfigDao = Mockito.mock(TemplateConfigDao.class);
		mockStatusLogDao = Mockito.mock(StatusLogDao.class);
		mockSessionHeaderDao = Mockito.mock(SessionHeaderDao.class);
		mockTemplateUpdaterConfigDao = Mockito.mock(TemplateUpdaterConfigDao.class);
		mockTemplateContextPropertiesDao = Mockito.mock(TemplateContextPropertiesDao.class);
		dbPersistentServices = new DbPersistentServices(mockTemplateConfigDao, mockStatusLogDao, 
				mockSessionHeaderDao, mockTemplateUpdaterConfigDao,
				mockTemplateContextPropertiesDao);
	}
}

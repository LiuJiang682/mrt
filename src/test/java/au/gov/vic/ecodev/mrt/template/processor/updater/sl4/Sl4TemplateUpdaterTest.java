package au.gov.vic.ecodev.mrt.template.processor.updater.sl4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import au.gov.vic.ecodev.mrt.dao.SessionHeaderDaoImpl;
import au.gov.vic.ecodev.mrt.dao.TemplateOptionalFieldDaoImpl;
import au.gov.vic.ecodev.mrt.dao.sl4.BoreHoleDaoImpl;
import au.gov.vic.ecodev.mrt.dao.sl4.DrillingDetailsDaoImpl;
import au.gov.vic.ecodev.mrt.dao.sl4.SiteDaoImpl;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.persistent.Dao;
import au.gov.vic.ecodev.mrt.template.processor.updater.sl4.Sl4DataRecordUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.sl4.Sl4TemplateUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.SessionHeaderUpdater;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.sl4.DrillingDetailsUpdater;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Sl4TemplateUpdater.class})
public class Sl4TemplateUpdaterTest {

	private Sl4TemplateUpdater testInstance;
	
	@Test
	public void shouldExecuteUpdateMethod() throws Exception {
		//Given
		List<Dao> daos = new ArrayList<>();
		long sessionId = 1l;
		Template mockTemplate = Mockito.mock(Template.class);
		SessionHeaderUpdater mockSessionHeaderUpdater = Mockito.mock(SessionHeaderUpdater.class);
		PowerMockito.whenNew(SessionHeaderUpdater.class).withArguments(eq(daos), 
				eq(sessionId), eq(mockTemplate)).thenReturn(mockSessionHeaderUpdater);
		DrillingDetailsUpdater mockDrillingDetialsUpdater = Mockito.mock(DrillingDetailsUpdater.class);
		PowerMockito.whenNew(DrillingDetailsUpdater.class).withArguments(eq(daos), 
				eq(mockTemplate), Matchers.any(Map.class))
			.thenReturn(mockDrillingDetialsUpdater);
		Sl4DataRecordUpdater mockSl4DataRecordUpdater = Mockito.mock(Sl4DataRecordUpdater.class);
		PowerMockito.whenNew(Sl4DataRecordUpdater.class)
			.withArguments(eq(daos), eq(sessionId), eq(mockTemplate), Matchers.any(Map.class))
			.thenReturn(mockSl4DataRecordUpdater);
		givenTestInstance();
		testInstance.setDaos(daos);
		//When
		testInstance.update(sessionId, mockTemplate);
		//Then
		verify(mockSessionHeaderUpdater).update();
		verify(mockDrillingDetialsUpdater).update();
		verify(mockSl4DataRecordUpdater).update();
		PowerMockito.verifyNew(SessionHeaderUpdater.class).withArguments(eq(daos), 
				eq(sessionId), eq(mockTemplate));
		PowerMockito.verifyNew(DrillingDetailsUpdater.class).withArguments(eq(daos), 
				eq(mockTemplate), Matchers.any(Map.class));
		PowerMockito.verifyNew(Sl4DataRecordUpdater.class).withArguments(eq(daos), 
				eq(sessionId), eq(mockTemplate), Matchers.any(Map.class));;
		PowerMockito.verifyNoMoreInteractions();
	}
	
	@Test
	public void shouldReturnListOfDaoClasses() {
		// Given
		givenTestInstance();
		// When
		List<Class<? extends Dao>> clsList = testInstance.getDaoClasses();
		// Then
		assertThat(clsList.size(), is(equalTo(5)));
		assertThat(clsList.get(0), is(equalTo(SessionHeaderDaoImpl.class)));
		assertThat(clsList.get(1), is(equalTo(DrillingDetailsDaoImpl.class)));
		assertThat(clsList.get(2), is(equalTo(SiteDaoImpl.class)));
		assertThat(clsList.get(3), is(equalTo(BoreHoleDaoImpl.class)));
		assertThat(clsList.get(4), is(equalTo(TemplateOptionalFieldDaoImpl.class)));
	}

	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionWhenDaoListIsNull() throws TemplateProcessorException {
		// Given
		givenTestInstance();
		// When
		testInstance.update(0l, null);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionWhenTemplateIsNull() throws TemplateProcessorException {
		// Given
		givenTestInstance();
		List<Dao> daos = new ArrayList<>();
		daos.add(Mockito.mock(Dao.class));
		testInstance.setDaos(daos);
		// When
		testInstance.update(0l, null);
		fail("Program reached unexpected point!");
	}

	private void givenTestInstance() {
		testInstance = new Sl4TemplateUpdater();
	}
}

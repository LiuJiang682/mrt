package au.gov.vic.ecodev.mrt.template.loader.fsm;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.jdbc.core.JdbcTemplate;

import au.gov.vic.ecodev.mrt.config.MrtConfigProperties;
import au.gov.vic.ecodev.mrt.map.services.VictoriaMapServices;
import au.gov.vic.ecodev.mrt.template.context.properties.SqlCriteria;
import au.gov.vic.ecodev.mrt.template.criteria.TemplateCriteria;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.DefaultMessage;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.Message;
import au.gov.vic.ecodev.mrt.template.processor.context.properties.ds4.DownHoleHoleIdNotInBoreHoleSearcher;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.services.PersistentServices;
import au.gov.vic.ecodev.mrt.template.properties.TemplateProperties;
import au.gov.vic.ecodev.template.processor.context.properties.StringTemplateProperties;

public class TemplateLoaderStateMachineContextTest {

	private TemplateLoaderStateMachineContext templateLoaderStateMachineContext;
	private PersistentServices mockPersistentServices;
	private MrtConfigProperties mockMrtConfigProperties;
	private JdbcTemplate mockJdbcTemplate;
	private VictoriaMapServices mockVictoriaMapServices;

	@Test
	public void shouldReturnInstance() {
		// Given
		givenTestInstance();
		// When
		// Then
		assertThat(templateLoaderStateMachineContext, is(notNullValue()));
		assertThat(templateLoaderStateMachineContext.getCurrentStep(), is(equalTo(LoadingStep.SCAN_DIR)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenPropertiesIsNull() {
		// Given
		MrtConfigProperties mrtConfigProperties = null;
		PersistentServices persistentServices = null;
		JdbcTemplate jdbcTemplate = null;
		VictoriaMapServices victoriaMapServices = null;
		// When
		new TemplateLoaderStateMachineContext(mrtConfigProperties, persistentServices, 
				jdbcTemplate, victoriaMapServices);
		fail("Program reached unexpected point!");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenPersistentServicesIsNull() {
		// Given
		MrtConfigProperties mrtConfigProperties = Mockito.mock(MrtConfigProperties.class);
		PersistentServices persistentServices = null;
		JdbcTemplate jdbcTemplate = null;
		VictoriaMapServices victoriaMapServices = null;
		// When
		new TemplateLoaderStateMachineContext(mrtConfigProperties, persistentServices, 
				jdbcTemplate, victoriaMapServices);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenEntityManagerIsNull() {
		// Given
		MrtConfigProperties mrtConfigProperties = Mockito.mock(MrtConfigProperties.class);
		PersistentServices persistentServices = Mockito.mock(PersistentServices.class);
		JdbcTemplate jdbcTemplate = null;
		VictoriaMapServices victoriaMapServices = null;
		// When
		new TemplateLoaderStateMachineContext(mrtConfigProperties, persistentServices, 
				jdbcTemplate, victoriaMapServices);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenVictoriaMapServicesIsNull() {
		// Given
		MrtConfigProperties mrtConfigProperties = Mockito.mock(MrtConfigProperties.class);
		PersistentServices persistentServices = Mockito.mock(PersistentServices.class);
		JdbcTemplate jdbcTemplate = Mockito.mock(JdbcTemplate.class);
		VictoriaMapServices victoriaMapServices = null;
		// When
		new TemplateLoaderStateMachineContext(mrtConfigProperties, persistentServices, 
				jdbcTemplate, victoriaMapServices);
		fail("Program reached unexpected point!");
	}

	@Test
	public void shouldReturnScanDirectoryState() throws Exception {
		// Given
		givenTestInstance();
		// When
		LoaderState loaderState = templateLoaderStateMachineContext.getNextState();
		// Then
		assertThat(loaderState, is(instanceOf(ScanDirectoryState.class)));
	}

	@Test
	public void shouldReturn8State() throws Exception {
		// Given
		givenTestInstance();
		// When
		LoaderState loaderState = templateLoaderStateMachineContext.getNextState();
		// Then
		assertThat(loaderState, is(instanceOf(ScanDirectoryState.class)));
		assertThat(templateLoaderStateMachineContext.getNextState(), is(instanceOf(ExtractTemplateNameState.class)));
		assertThat(templateLoaderStateMachineContext.getNextState(), is(instanceOf(RetrieveTemplateState.class)));
		assertThat(templateLoaderStateMachineContext.getNextState(), is(instanceOf(UnzipZipFileState.class)));
		assertThat(templateLoaderStateMachineContext.getNextState(), is(instanceOf(ProcessTemplateFileState.class)));
		assertThat(templateLoaderStateMachineContext.getNextState(), is(instanceOf(GenerateStatusLogState.class)));
		assertThat(templateLoaderStateMachineContext.getNextState(), is(instanceOf(MoveFileToNextStageState.class)));
		assertThat(templateLoaderStateMachineContext.getNextState(), is(instanceOf(NotifyUserState.class)));
		assertThat(templateLoaderStateMachineContext.getNextState(), is(nullValue()));
	}

	@Test
	public void shouldReturnNullState() throws Exception {
		// Given
		givenTestInstance();
		LoadingStep mockStep = LoadingStep.NOTIFY_USER;
		Whitebox.setInternalState(templateLoaderStateMachineContext, "currentStep", mockStep);
		templateLoaderStateMachineContext.getNextState();
		// When
		LoaderState loaderState = templateLoaderStateMachineContext.getNextState();
		// Then
		assertThat(loaderState, is(nullValue()));
	}

	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionWhenTemplateNameIsInvalid() throws TemplateProcessorException {
		// Given
		givenTestInstance();
		// When
		templateLoaderStateMachineContext.getTemplateContextProperty("abc");
		fail("Program reached unexpected poin!");
	}

	@Test
	public void shouldReturnEmptyListOfRequiredFieldsWhenTemplateNameIsValid() throws TemplateProcessorException {
		// Given
		givenTestInstance();
		when(mockPersistentServices.getTemplateContextProperty(eq("sl4"), eq("MandatoryValidationFields")))
				.thenReturn("H0002,H0005,H0202,H0203,H0400,H0401,H0402,H0501,H0502,H0503,H0530,H0532,D");
		// When
		TemplateProperties templateContextProperty = templateLoaderStateMachineContext.getTemplateContextProperty("sl4:MandatoryValidationFields");
		String requiredFields = ((StringTemplateProperties) templateContextProperty).getValue();
		// Then
		assertThat(requiredFields, is(notNullValue()));
		assertThat(requiredFields, is(equalTo("H0002,H0005,H0202,H0203,H0400,H0401,H0402,H0501,H0502,H0503,H0530,H0532,D")));
	}

	@Test
	public void shouldreturnTrueWhenAddFailedFileNameSuccessfully() {
		// Given
		givenTestInstance();
		Message message = new DefaultMessage();
		templateLoaderStateMachineContext.setMessage(message);
		assertThat(message.getFailedFiles().isEmpty(), is(true));
		// When
		boolean flag = templateLoaderStateMachineContext.addFailedFiles("abc");
		// Then
		assertThat(flag, is(true));
		assertThat(message.getFailedFiles().size(), is(equalTo(1)));
	}
	
	@Test
	public void shouldreturnTrueWhenAddFailedFileNameSuccessfullywithExistingFile() {
		// Given
		givenTestInstance();
		Message message = new DefaultMessage();
		templateLoaderStateMachineContext.setMessage(message);
		File file = new File("abc.zip");
		message.getFailedFiles().add(file);
		// When
		boolean flag = templateLoaderStateMachineContext.addFailedFiles("abc");
		// Then
		assertThat(flag, is(true));
		assertThat(message.getFailedFiles().size(), is(equalTo(1)));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldreturnFalseWhenAddFailedFileNameFailed() {
		// Given
		givenTestInstance();
		// When
		templateLoaderStateMachineContext.addFailedFiles("abc");
		fail("Program reached unexpected point!");
	}
	
	@Test
	public void shouldreturnTrueWhenAddPassedFileNameSuccessfully() {
		// Given
		givenTestInstance();
		Message message = new DefaultMessage();
		templateLoaderStateMachineContext.setMessage(message);
		assertThat(message.getPassedFiles().isEmpty(), is(true));
		// When
		boolean flag = templateLoaderStateMachineContext.addPassedFiles("abc");
		// Then
		assertThat(flag, is(true));
		assertThat(message.getPassedFiles().size(), is(equalTo(1)));
	}
	
	@Test
	public void shouldreturnTrueWhenAddPassedFileNameSuccessfullywithExistingFile() {
		// Given
		givenTestInstance();
		Message message = new DefaultMessage();
		templateLoaderStateMachineContext.setMessage(message);
		File file = new File("abc.zip");
		message.getPassedFiles().add(file);
		// When
		boolean flag = templateLoaderStateMachineContext.addPassedFiles("abc");
		// Then
		assertThat(flag, is(true));
		assertThat(message.getPassedFiles().size(), is(equalTo(1)));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenAddPassedFileNameFailed() {
		// Given
		givenTestInstance();
		// When
		templateLoaderStateMachineContext.addPassedFiles("abc");
		fail("Program reached unexpected point!");
	}
	
	@Test
	public void shouldReturnTemplatePropertiesWhenSearchCalledWitCriteria() throws TemplateProcessorException {
		// Given
		givenTestInstance();
		when(mockPersistentServices.getTemplateContextProperty(Matchers.anyString(), 
				Matchers.anyString())).thenReturn("jdbcTemplate,key");
		TemplateCriteria criteria = new SqlCriteria(DownHoleHoleIdNotInBoreHoleSearcher.class.getName(), 
				"DS4", 123l);
		// When
		TemplateProperties templateProperties = templateLoaderStateMachineContext.search(criteria);
		// Then
		assertThat(templateProperties, is(notNullValue()));
	}

	private void givenTestInstance() {
		mockMrtConfigProperties = Mockito.mock(MrtConfigProperties.class);
		mockPersistentServices = Mockito.mock(PersistentServices.class);
		mockJdbcTemplate = Mockito.mock(JdbcTemplate.class);
		mockVictoriaMapServices = Mockito.mock(VictoriaMapServices.class);
		templateLoaderStateMachineContext = new TemplateLoaderStateMachineContext(mockMrtConfigProperties,
				mockPersistentServices, mockJdbcTemplate, mockVictoriaMapServices);
	}
}

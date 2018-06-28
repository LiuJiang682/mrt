package au.gov.vic.ecodev.mrt.template.loader.fsm.helpers;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.template.processor.services.PersistentServices;

public class TemplateOwnerEmailHelperTest {

	private PersistentServices mockPersistentServices;
	private TemplateOwnerEmailHelper testInstance;
	
	@Test
	public void shouldExtractTemplateOwnerEmails() {
		//Given
		givenTestInstance();
		when(mockPersistentServices.getTemplateOwnerEmail(eq("mrt"))).thenReturn("jiang.liu@ecodev.vic.gov.au");
		//When
		String ownerEmails = testInstance.extractTemplateOwnerEmails(Arrays.asList("mrt"));
		//Then
		assertThat(ownerEmails, is(equalTo("jiang.liu@ecodev.vic.gov.au")));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		givenTestInstance();
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	private void givenTestInstance() {
		mockPersistentServices = Mockito.mock(PersistentServices.class);
		testInstance = new TemplateOwnerEmailHelper(mockPersistentServices);
	}
}

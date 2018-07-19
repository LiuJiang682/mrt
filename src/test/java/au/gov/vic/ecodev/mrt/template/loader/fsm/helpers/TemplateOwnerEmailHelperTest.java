package au.gov.vic.ecodev.mrt.template.loader.fsm.helpers;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.processor.services.PersistentServices;

public class TemplateOwnerEmailHelperTest {

	private PersistentServices mockPersistentServices;
	private TemplateOwnerEmailHelper testInstance;
	
	@Test
	public void shouldExtractTemplateOwnerEmails() {
		//Given
		givenTestInstance();
		Map<String, Object> email = new HashMap<>();
		email.put("OWNER_EMAILS", "jiang.liu@ecodev.vic.gov.au");
		email.put("EMAILS_BUILDER", "au.gov.vic.ecodev.mrt.mail.MrtEmailBodyBuilder");
		when(mockPersistentServices.getTemplateOwnerEmail(eq("mrt"))).thenReturn(email);
		//When
		List<Map<String, Object>> ownerEmails = testInstance.extractTemplateOwnerEmails(Arrays.asList("mrt"));
		//Then
		assertThat(ownerEmails, is(notNullValue()));
		assertThat(ownerEmails.size(), is(equalTo(1)));
		Map<String, Object> ownerEmail = ownerEmails.get(0);
		assertThat(ownerEmail.get("OWNER_EMAILS"), is(equalTo("jiang.liu@ecodev.vic.gov.au")));
		assertThat(ownerEmail.get("EMAILS_BUILDER"), is(equalTo("au.gov.vic.ecodev.mrt.mail.MrtEmailBodyBuilder")));
	}
	
	@Test
	public void shouldExtract2TemplateOwnerEmails() {
		//Given
		givenTestInstance();
		Map<String, Object> email = TestFixture.getEmailProperties();
		Map<String, Object> hydroEmail = new HashMap<>();
		hydroEmail.put("OWNER_EMAILS", "gavin.stilgoe@ecodev.vic.gov.au");
		hydroEmail.put("EMAILS_BUILDER", "au.gov.vic.ecodev.template.mail.custom.vgp.hydro.VgpHydroEmailBodyBuilder");
		when(mockPersistentServices.getTemplateOwnerEmail(eq("mrt"))).thenReturn(email);
		when(mockPersistentServices.getTemplateOwnerEmail(eq("hygo"))).thenReturn(hydroEmail);
		//When
		List<Map<String, Object>> ownerEmails = testInstance.extractTemplateOwnerEmails(Arrays.asList("mrt", "hygo"));
		//Then
		assertThat(ownerEmails, is(notNullValue()));
		assertThat(ownerEmails.size(), is(equalTo(2)));
		Map<String, Object> first = ownerEmails.get(0);
		Map<String, Object> second = ownerEmails.get(1);
		boolean foundFirstEmail = false;
		boolean foundSecondEmail = false;
		boolean foundFirstEmailBuilder = false;
		boolean foundSecondEmailBuilder = false;
		String firstEmailAddress = (String) first.get("OWNER_EMAILS");
		if ("jiang.liu@ecodev.vic.gov.au".equals(firstEmailAddress)) {
			foundFirstEmail = true;
		} else if ("gavin.stilgoe@ecodev.vic.gov.au".equals(firstEmailAddress)) {
			foundSecondEmail = true;
		}
		String secondEmailAddress = (String) second.get("OWNER_EMAILS");
		if ("jiang.liu@ecodev.vic.gov.au".equals(secondEmailAddress)) {
			foundFirstEmail = true;
		} else if ("gavin.stilgoe@ecodev.vic.gov.au".equals(secondEmailAddress)) {
			foundSecondEmail = true;
		}
		String firstEmailBuilder = (String) first.get("EMAILS_BUILDER");
		if ("au.gov.vic.ecodev.mrt.mail.MrtEmailBodyBuilder".equals(firstEmailBuilder)) {
			foundFirstEmailBuilder = true;
		} else if ("au.gov.vic.ecodev.template.mail.custom.vgp.hydro.VgpHydroEmailBodyBuilder".equals(firstEmailBuilder)) {
			foundSecondEmailBuilder = true;
		}
		String secondEmailBuilder = (String) second.get("EMAILS_BUILDER");
		if ("au.gov.vic.ecodev.mrt.mail.MrtEmailBodyBuilder".equals(secondEmailBuilder)) {
			foundFirstEmailBuilder = true;
		} else if ("au.gov.vic.ecodev.template.mail.custom.vgp.hydro.VgpHydroEmailBodyBuilder".equals(secondEmailBuilder)) {
			foundSecondEmailBuilder = true;
		}
		assertThat(foundFirstEmail, is(true));
		assertThat(foundSecondEmail, is(true));
		
		assertThat(foundFirstEmailBuilder, is(true));
		assertThat(foundSecondEmailBuilder, is(true));
	}
	
	@Test
	public void shouldNoExtractTemplateOwnerEmailsWhenNoData() {
		//Given
		givenTestInstance();
		when(mockPersistentServices.getTemplateOwnerEmail(eq("abc"))).thenReturn(null);
		//When
		List<Map<String, Object>> ownerEmails = testInstance.extractTemplateOwnerEmails(Arrays.asList("abc"));
		//Then
		assertThat(ownerEmails, is(notNullValue()));
		assertThat(ownerEmails.size(), is(equalTo(0)));
	}
	
//	@Test
//	public void shouldExtract3TemplateOwnerEmails() {
//		//Given
//		givenTestInstance();
//		when(mockPersistentServices.getTemplateOwnerEmail(eq("mrt"))).thenReturn("jiang.liu@ecodev.vic.gov.au");
//		when(mockPersistentServices.getTemplateOwnerEmail(eq("hygo"))).thenReturn("gavin.stilgoe@ecodev.vic.gov.au");
//		when(mockPersistentServices.getTemplateOwnerEmail(eq("gas"))).thenReturn("colin.marson@ecodev.vic.gov.au");
//		//When
//		List<Map<String, Object>> ownerEmails = testInstance.extractTemplateOwnerEmails(Arrays.asList("mrt", "hygo", "gas"));
//		//Then
//		assertThat(ownerEmails, is(notNullValue()));
//		assertThat(ownerEmails.size(), is(equalTo(2)));
//		assertThat(ownerEmails.get("OWNER_EMAILS"), is(equalTo("jiang.liu@ecodev.vic.gov.au")));
//		assertThat(ownerEmails.get("EMAILS_BUILDER"), is(equalTo("au.gov.vic.ecodev.mrt.mail.MrtEmailBodyBuilder")));
//	}
	
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

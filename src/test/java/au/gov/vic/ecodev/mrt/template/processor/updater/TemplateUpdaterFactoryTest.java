package au.gov.vic.ecodev.mrt.template.processor.updater;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.api.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.model.sl4.Sl4Template;
import au.gov.vic.ecodev.mrt.template.processor.services.PersistentServices;
import au.gov.vic.ecodev.mrt.template.processor.update.TemplateUpdater;

public class TemplateUpdaterFactoryTest {

	@Test
	public void shouldReturnTemplateUpdaterObjectWhenClassFound() {
		//Given
		Template template = new Sl4Template();
		PersistentServices mockPersistentService = Mockito.mock(PersistentServices.class);
		when(mockPersistentService.getTemplateUpdaterClass(eq("Sl4Template"))).thenReturn("au.gov.vic.ecodev.mrt.template.processor.updater.sl4.Sl4TemplateUpdater");
		//When
		TemplateUpdater  updater = TemplateUpdaterFactory.getTemplateUpdater(template, mockPersistentService);
		//Then
		assertThat(updater, is(notNullValue()));
	}
	
	@Test
	public void shouldReturnNullWhenClassNotFound() {
		//Given
		Template template = new Sl4Template();
		PersistentServices mockPersistentService = Mockito.mock(PersistentServices.class);
		when(mockPersistentService.getTemplateUpdaterClass(eq("Sl4Template"))).thenReturn(Strings.EMPTY);
		//When
		TemplateUpdater  updater = TemplateUpdaterFactory.getTemplateUpdater(template, mockPersistentService);
		//Then
		assertThat(updater, is(nullValue()));
	}
}

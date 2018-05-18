package au.gov.vic.ecodev.mrt.template.processor.context.properties.utils;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.template.context.properties.DefaultStringTemplateProperties;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;

public class MultiStringValueToListConventorTest {

	@Test
	public void shouldReturnListAsContextProperties() throws TemplateProcessorException {
		// Given
		TemplateProcessorContext mockTemplateProcessorContext = Mockito.mock(TemplateProcessorContext.class);
		when(mockTemplateProcessorContext.getTemplateContextProperty(eq("property")))
			.thenReturn(new DefaultStringTemplateProperties("abc,def"));
		MultiStringValueToListConventor testInstance = new MultiStringValueToListConventor(
				mockTemplateProcessorContext);
		String propertName = "property";
		// When
		List<String> myList = testInstance.getContextProperties(propertName, ",");
		assertThat(CollectionUtils.isEmpty(myList), is(false));
		assertThat(myList.size(), is(equalTo(2)));
		assertThat(myList.get(0), is(equalTo("abc")));
		assertThat(myList.get(1), is(equalTo("def")));
	}
	
	@Test
	public void shouldReturnInstance() {
		// Given
		TemplateProcessorContext mockTemplateProcessorContext = Mockito.mock(TemplateProcessorContext.class);
		// When
		MultiStringValueToListConventor testInstance = new MultiStringValueToListConventor(
				mockTemplateProcessorContext);
		// Then
		assertThat(testInstance, is(notNullValue()));
	}
}

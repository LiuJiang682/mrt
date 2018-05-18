package au.gov.vic.ecodev.mrt.template.processor.classloader;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.config.MrtConfigProperties;

public class MrtProcessorClassLoaderImplTest {

	private MrtConfigProperties mockMrtConfigProperties;
	private MrtProcessorClassLoaderImpl mrtProcessorClassLoaderImpl;
	
	@Test
	public void shouldReturnInstance() {
		// Given
		givenTestInstance();
		// When
		// Then
		assertThat(mrtProcessorClassLoaderImpl, is(notNullValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenPropertiesIsNull() {
		// Given
		MrtConfigProperties mockMrtConfigProperties = null;
		// When
		new MrtProcessorClassLoaderImpl(mockMrtConfigProperties);
		fail("Program reached unexpected point.");
	}

	@Test
	public void shouldExecuteLoad() throws Exception {
		// Given
		givenTestInstance();
		when(mockMrtConfigProperties.getTemplateApiJarFile()).thenReturn("../mrt_api/target/mrt-api-0.0.1-SNAPSHOT.jar");
		when(mockMrtConfigProperties.getCustomTemplateJarFileDir()).thenReturn("src/main/resources/template/custom");
		// When
		mrtProcessorClassLoaderImpl.load();
		// That
		// Load class without any issue!
		assertThat(true, is(true));
	}
	
	@Test
	public void shouldReturnListOfJarFiles() throws Exception {
		//Given
		givenTestInstance();
		when(mockMrtConfigProperties.getCustomTemplateJarFileDir()).thenReturn("src/main/resources/template/custom");
		//When
		List<File> jarFiles = mrtProcessorClassLoaderImpl.getCustomTemplateJarFiles();
		//Then
		assertThat(jarFiles, is(notNullValue()));
		assertThat(CollectionUtils.isEmpty(jarFiles), is(false));
	}

	private void givenTestInstance() {
		mockMrtConfigProperties = Mockito.mock(MrtConfigProperties.class);
		mrtProcessorClassLoaderImpl = new MrtProcessorClassLoaderImpl(
				mockMrtConfigProperties);
	}
}

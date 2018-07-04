package au.gov.vic.ecodev.mrt.template.loader.fsm.helpers;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ZipFileFilterTest {

	private ZipFileFilter testInstance;
	
	@Test
	public void shouldFiterOutNotZipFiles() {
		//Given
		List<String> fileNames = Arrays.asList("mrt_eco.zip", "mart_eco.txt", "My.java", 
				"mrt_abc_efc.zip", "mrt_abc_efg_hil.zip");
		givenTestInstance();
		//When
		List<String> zipFiles = testInstance.filterZipFile(fileNames);
		//Then
		assertThat(zipFiles, is(notNullValue()));
		assertThat(zipFiles.size(), is(equalTo(3)));
		assertThat(zipFiles.get(0), is(equalTo("mrt_eco.zip")));
		assertThat(zipFiles.get(1), is(equalTo("mrt_abc_efc.zip")));
		assertThat(zipFiles.get(2), is(equalTo("mrt_abc_efg_hil.zip")));
	}
	
	private void givenTestInstance() {
		testInstance = new ZipFileFilter();
	}
}
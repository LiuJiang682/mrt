package au.gov.vic.ecodev.mrt.data.record.cleaner.helper;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import au.gov.vic.ecodev.mrt.fixture.TestFixture;

public class TableNameHelperTest {
	
	private TableNameHelper testInstance;
	
	@Test
	public void shouldReturnListOfTable() {
		//Given
		givenTestInstance(TestFixture.MRT_DISPLAY_PROPERTIES_JSON);
		//When
		List<String> tableNames = testInstance.extractTableName();
		//Then
		assertThat(tableNames, is(notNullValue()));
		assertThat(tableNames.size(), is(equalTo(6)));
	}
	
	@Test
	public void shouldReturnListOfTableVgpHydro() {
		//Given
		givenTestInstance("{\"SAMP\":[{\"SAMP_METADATA\":\"SITE_ID,SAMPLE_ID,IGSN,FILE_NAME,ROW_NUMBER,CORE_ID,LAB_CODE,TYPE,PREP_CODE,SAMP_DATE,SAMP_TOP,SAMP_BOTTOM,STAND_WATER_LVL,PUMPING_DEPTH,REFERENCE,SAMP_AREA_DESC\"}],\"ANAL\":[{\"SAMP_ANALYSIS\":\"SAMPLE_ID,IGSN,FILE_NAME,ROW_NUMBER,LAB_SAMP_NO,ANAL_DATE,PARAM,UOM,RESULT,ANAL_METH,LOR\"}],\"OBS\":[{\"OBSERVATIONS\":\"SITE_ID,SAMPLE_ID,IGSN,FILE_NAME,ROW_NUMBER,OCCUR_TIME,PARAM,DEPTH_FROM,DEPTH_TO,RESULT,OBSERVER,TYPE\"}]}");
		//When
		List<String> tableNames = testInstance.extractTableName();
		//Then
		assertThat(tableNames, is(notNullValue()));
		assertThat(tableNames.size(), is(equalTo(3)));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		givenTestInstance(TestFixture.MRT_DISPLAY_PROPERTIES_JSON);
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenStringIsNull() {
		//Given
		String string = null;
		//When
		new TableNameHelper(string);
		fail("Program reached unexpected point!");
	}

	private void givenTestInstance(String string) {
			testInstance = new TableNameHelper(string);
	}
}

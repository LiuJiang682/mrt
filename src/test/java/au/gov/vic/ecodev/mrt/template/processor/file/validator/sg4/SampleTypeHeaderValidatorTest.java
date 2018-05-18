package au.gov.vic.ecodev.mrt.template.processor.file.validator.sg4;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.template.fields.Sg4ColumnHeaders;

public class SampleTypeHeaderValidatorTest {

	@Test
	public void shouldReturnNoMessageWithStandardHeader() {
		//Given
		String[] strs = { "H1000", "Sample ID", "aEasting_MGA", "Northing_MGA", "Sample_type", "Total Hole Depth", "Drill Code", "Dip", "Azimuth_MAG" };
		List<String> messages = new ArrayList<>();
		Map<String, List<String>> templateParamMap = new HashMap<>();
		//When
		new SampleTypeHeaderValidator().validate(messages, templateParamMap, Arrays.asList(strs));
		//Then
		assertThat(CollectionUtils.isEmpty(messages), is(true));
		List<String> sampleIdList = templateParamMap.get(Sg4ColumnHeaders.SAMPLE_TYPE.getCode());
		assertThat(sampleIdList, is(notNullValue()));
		assertThat(sampleIdList.size(), is(equalTo(1)));
		assertThat(sampleIdList.get(0), is(equalTo("Sample_type")));
	}
	
	@Test
	public void shouldReturnWarningMessageWithSampleSpaceTypeHeader() {
		//Given
		String[] strs = { "H1000", "Sample ID", "aEasting_MGA", "Northing_MGA", "Sample type", "Total Hole Depth", "Drill Code", "Dip", "Azimuth_MAG" };
		List<String> messages = new ArrayList<>();
		Map<String, List<String>> templateParamMap = new HashMap<>();
		//When
		new SampleTypeHeaderValidator().validate(messages, templateParamMap, Arrays.asList(strs));
		//Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("WARNING: H1000 requires the Sample_type header, found Sample type, use it as Sample_type")));
		List<String> sampleIdList = templateParamMap.get(Sg4ColumnHeaders.SAMPLE_TYPE.getCode());
		assertThat(sampleIdList, is(notNullValue()));
		assertThat(sampleIdList.size(), is(equalTo(1)));
		assertThat(sampleIdList.get(0), is(equalTo("Sample type")));
	}
	
	@Test
	public void shouldReturnWarningMessageWithSampleTypeHeader() {
		//Given
		String[] strs = { "H1000", "Sample ID", "aEasting_MGA", "Northing_MGA", "Sampletype", "Total Hole Depth", "Drill Code", "Dip", "Azimuth_MAG" };
		List<String> messages = new ArrayList<>();
		Map<String, List<String>> templateParamMap = new HashMap<>();
		//When
		new SampleTypeHeaderValidator().validate(messages, templateParamMap, Arrays.asList(strs));
		//Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("WARNING: H1000 requires the Sample_type header, found Sampletype, use it as Sample_type")));
		List<String> sampleIdList = templateParamMap.get(Sg4ColumnHeaders.SAMPLE_TYPE.getCode());
		assertThat(sampleIdList, is(notNullValue()));
		assertThat(sampleIdList.size(), is(equalTo(1)));
		assertThat(sampleIdList.get(0), is(equalTo("Sampletype")));
	}
	
	@Test
	public void shouldReturnWarningMessageWithSampleHyphenTypeHeader() {
		//Given
		String[] strs = { "H1000", "Sample ID", "aEasting_MGA", "Northing_MGA", "Sample-type", "Total Hole Depth", "Drill Code", "Dip", "Azimuth_MAG" };
		List<String> messages = new ArrayList<>();
		Map<String, List<String>> templateParamMap = new HashMap<>();
		//When
		new SampleTypeHeaderValidator().validate(messages, templateParamMap, Arrays.asList(strs));
		//Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("WARNING: H1000 requires the Sample_type header, found Sample-type, use it as Sample_type")));
		List<String> sampleIdList = templateParamMap.get(Sg4ColumnHeaders.SAMPLE_TYPE.getCode());
		assertThat(sampleIdList, is(notNullValue()));
		assertThat(sampleIdList.size(), is(equalTo(1)));
		assertThat(sampleIdList.get(0), is(equalTo("Sample-type")));
	}
	
	@Test
	public void shouldReturnErrorMessageWithNoSampleIDHeader() {
		//Given
		String[] strs = { "H1000", "Sample ID", "aEasting_MGA", "Northing_MGA", "Sample_type1", "Total Hole Depth", "Drill Code", "Dip", "Azimuth_MAG" };
		List<String> messages = new ArrayList<>();
		Map<String, List<String>> templateParamMap = new HashMap<>();
		//When
		new SampleTypeHeaderValidator().validate(messages, templateParamMap, Arrays.asList(strs));
		//Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: H1000 requires the Sample_type column")));
		List<String> sampleIdList = templateParamMap.get(Sg4ColumnHeaders.SAMPLE_TYPE.getCode());
		assertThat(sampleIdList, is(nullValue()));
	}
}

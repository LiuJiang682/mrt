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

public class SampleIdHeaderValidatorTest {

	@Test
	public void shouldReturnNoMessageWithStandardHeader() {
		//Given
		String[] strs = { "H1000", "Sample ID", "aEasting_MGA", "Northing_MGA", "Sample_type", "Total Hole Depth", "Drill Code", "Dip", "Azimuth_MAG" };
		List<String> messages = new ArrayList<>();
		Map<String, List<String>> templateParamMap = new HashMap<>();
		//When
		new SampleIdHeaderValidator().validate(messages, templateParamMap, Arrays.asList(strs));
		//Then
		assertThat(CollectionUtils.isEmpty(messages), is(true));
		List<String> sampleIdList = templateParamMap.get(Sg4ColumnHeaders.SAMPLE_ID.getCode());
		assertThat(sampleIdList, is(notNullValue()));
		assertThat(sampleIdList.size(), is(equalTo(1)));
		assertThat(sampleIdList.get(0), is(equalTo("Sample ID")));
	}
	
	@Test
	public void shouldReturnWarningMessageWithSampleIDHeader() {
		//Given
		String[] strs = { "H1000", "SampleID", "aEasting_MGA", "Northing_MGA", "Sample_type", "Total Hole Depth", "Drill Code", "Dip", "Azimuth_MAG" };
		List<String> messages = new ArrayList<>();
		Map<String, List<String>> templateParamMap = new HashMap<>();
		//When
		new SampleIdHeaderValidator().validate(messages, templateParamMap, Arrays.asList(strs));
		//Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("WARNING: H1000 requires the Sample ID header, found SampleID, use it as Sample ID")));
		List<String> sampleIdList = templateParamMap.get(Sg4ColumnHeaders.SAMPLE_ID.getCode());
		assertThat(sampleIdList, is(notNullValue()));
		assertThat(sampleIdList.size(), is(equalTo(1)));
		assertThat(sampleIdList.get(0), is(equalTo("SampleID")));
	}
	
	@Test
	public void shouldReturnWarningMessageWithSample_IDHeader() {
		//Given
		String[] strs = { "H1000", "Sample_ID", "aEasting_MGA", "Northing_MGA", "Sample_type", "Total Hole Depth", "Drill Code", "Dip", "Azimuth_MAG" };
		List<String> messages = new ArrayList<>();
		Map<String, List<String>> templateParamMap = new HashMap<>();
		//When
		new SampleIdHeaderValidator().validate(messages, templateParamMap, Arrays.asList(strs));
		//Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("WARNING: H1000 requires the Sample ID header, found Sample_ID, use it as Sample ID")));
		List<String> sampleIdList = templateParamMap.get(Sg4ColumnHeaders.SAMPLE_ID.getCode());
		assertThat(sampleIdList, is(notNullValue()));
		assertThat(sampleIdList.size(), is(equalTo(1)));
		assertThat(sampleIdList.get(0), is(equalTo("Sample_ID")));
	}
	
	@Test
	public void shouldReturnWarningMessageWithSampleHyphenIDHeader() {
		//Given
		String[] strs = { "H1000", "Sample-ID", "aEasting_MGA", "Northing_MGA", "Sample_type", "Total Hole Depth", "Drill Code", "Dip", "Azimuth_MAG" };
		List<String> messages = new ArrayList<>();
		Map<String, List<String>> templateParamMap = new HashMap<>();
		//When
		new SampleIdHeaderValidator().validate(messages, templateParamMap, Arrays.asList(strs));
		//Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("WARNING: H1000 requires the Sample ID header, found Sample-ID, use it as Sample ID")));
		List<String> sampleIdList = templateParamMap.get(Sg4ColumnHeaders.SAMPLE_ID.getCode());
		assertThat(sampleIdList, is(notNullValue()));
		assertThat(sampleIdList.size(), is(equalTo(1)));
		assertThat(sampleIdList.get(0), is(equalTo("Sample-ID")));
	}
	
	@Test
	public void shouldReturnErrorMessageWithNoSampleIDHeader() {
		//Given
		String[] strs = { "H1000", "Sample ID1", "aEasting_MGA", "Northing_MGA", "Sample_type", "Total Hole Depth", "Drill Code", "Dip", "Azimuth_MAG" };
		List<String> messages = new ArrayList<>();
		Map<String, List<String>> templateParamMap = new HashMap<>();
		//When
		new SampleIdHeaderValidator().validate(messages, templateParamMap, Arrays.asList(strs));
		//Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: H1000 requires the Sample ID column")));
		List<String> sampleIdList = templateParamMap.get(Sg4ColumnHeaders.SAMPLE_ID.getCode());
		assertThat(sampleIdList, is(nullValue()));
	}
}

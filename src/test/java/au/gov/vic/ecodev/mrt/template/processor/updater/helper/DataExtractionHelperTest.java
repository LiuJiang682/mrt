package au.gov.vic.ecodev.mrt.template.processor.updater.helper;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.fields.SL4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;

public class DataExtractionHelperTest {

	@Test
	public void shouldReturnDate() {
		//Given
		List<String> data = TestFixture.getDateList();
		DataExtractionHelper testInstance = new DataExtractionHelper(data);
		//When
		Date date = testInstance.extractDate(1, "dd-MMM-yy");
		//Then
		assertThat(date, is(notNullValue()));
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		assertThat(c.get(Calendar.YEAR), is(equalTo(2012)));
		assertThat(c.get(Calendar.MONTH), is(equalTo(Calendar.NOVEMBER)));
		assertThat(c.get(Calendar.DAY_OF_MONTH), is(equalTo(12)));
	}
	
	@Test
	public void shouldReturnNullDateWhenDataIsNull() {
		//Given
		List<String>  data = null;
		DataExtractionHelper testInstance = new DataExtractionHelper(data);
		//When
		Date date = testInstance.extractDate(1, "dd-MMM-yy");
		//Then
		assertThat(date, is(nullValue()));
	}
	
	@Test
	public void shouldReturnNullDateWhenIndexIsOutSideRange() {
		//Given
		List<String>  data = TestFixture.getDateList();
		DataExtractionHelper testInstance = new DataExtractionHelper(data);
		//When
		Date date = testInstance.extractDate(10, "dd-MMM-yy");
		//Then
		assertThat(date, is(nullValue()));
	}
	
	@Test
	public void shouldReturnNullDateWhenStringIsUnparsable() {
		//Given
		List<String>  data = new ArrayList<>();
		data.add("Start_date_of_data_acquisition");
		data.add("Start_date_of_data_acquisition");
		DataExtractionHelper testInstance = new DataExtractionHelper(data);
		//When
		Date date = testInstance.extractDate(10, "dd-MMM-yy");
		//Then
		assertThat(date, is(nullValue()));
	}
	
	@Test
	public void shouldReturnHeaderIndex() throws TemplateProcessorException {
		//Given
		DataExtractionHelper testInstance = new DataExtractionHelper(TestFixture.getColumnHeaderList());
		//When
		int index = testInstance.extractMandatoryFieldIndex(SL4ColumnHeaders.TOTAL_HOLE_DEPTH.getCode());
		//Then
		assertThat(index, is(equalTo(4)));
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionWhenCodeNotFound() throws TemplateProcessorException {
		//Given
		List<String> headers = TestFixture.getColumnHeaderList();
		headers.set(4, "");
		DataExtractionHelper testInstance = new DataExtractionHelper(headers);
		//When
		testInstance.extractMandatoryFieldIndex(SL4ColumnHeaders.TOTAL_HOLE_DEPTH.getCode());
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionWhenListIsNull() throws TemplateProcessorException {
		//Given
		List<String> headers = null;
		DataExtractionHelper testInstance = new DataExtractionHelper(headers);
		//When
		testInstance.extractMandatoryFieldIndex(SL4ColumnHeaders.TOTAL_HOLE_DEPTH.getCode());
	}
	
	@Test
	public void shouldReturnNumberWhenNumberStringIsCorrect() throws TemplateProcessorException {
		// Given
		List<String> d1 = new ArrayList<>(TestFixture.getDList());
		DataExtractionHelper testInstance = new DataExtractionHelper(d1);
		// When
		BigDecimal bigDecimal =testInstance.extractBigDecimal(1);
		//Then
		assertThat(bigDecimal, is(new BigDecimal("630500")));
	}
	
	@Test
	public void shouldReturnNullAsBigDecimalWhenNumberStringIsNull() throws TemplateProcessorException {
		// Given
		List<String> d1 = new ArrayList<>(TestFixture.getDList());
		d1.set(1, null);
		DataExtractionHelper testInstance = new DataExtractionHelper(d1);
		// When
		BigDecimal bigDecimal =testInstance.extractBigDecimal(1);
		//Then
		assertThat(bigDecimal, is(nullValue()));
	}
	
	@Test
	public void shouldReturnNullAsNumberWhenNumberStringIsRubbish() throws TemplateProcessorException {
		// Given
		List<String> d1 = new ArrayList<>(TestFixture.getDList());
		d1.set(1, "abcv");
		DataExtractionHelper testInstance = new DataExtractionHelper(d1);
		// When
		BigDecimal bigDecimal =testInstance.extractBigDecimal(1);
		//Then
		assertThat(bigDecimal, is(nullValue()));
	}
	
	@Test
	public void shouldReturnNullAsNumberWhenDataListIsNull() throws TemplateProcessorException {
		// Given
		List<String> d1 = null;
		DataExtractionHelper testInstance = new DataExtractionHelper(d1);
		// When
		BigDecimal bigDecimal =testInstance.extractBigDecimal(1);
		//Then
		assertThat(bigDecimal, is(nullValue()));
	}
}

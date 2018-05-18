package au.gov.vic.ecodev.mrt.template.processor.file.validator.dg4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.model.dg4.Dg4Template;

public class H1006ValidatorTest {

	@Test
	public void shouldReturnEmptyMessageWhenH1006Provided() {
		//Given
		String[] strs = {"H1006", null, null, null, null, null, "", "P", "", "", "", null, null, null, null, null, null, null};
		Map<String, List<String>> templateParamMap = new HashMap<>();
		List<String> headers = Arrays.asList("Hole_id",	"Sample_id", "From", "To", "Sample_type", "Au", "Au", "As", "Cu", "Pb", "Zn", "Drill code", "Prospect", "Date_Started", "Date_Completed", "Company", "Comments");
		templateParamMap.put(Strings.COLUMN_HEADERS, headers);
		H1006Validator testInstance= new H1006Validator();
		testInstance.init(strs);
		Template dataBean = new Dg4Template();
		//When
		Optional<List<String>> errorMessages = testInstance.validate(templateParamMap, dataBean);
		//Then
		assertThat(errorMessages.isPresent(), is(false));
		List<String> dataBeanIndexList = dataBean.get(Strings.KEY_PREFIX_DUPLICATED + "Au");
		assertThat(dataBeanIndexList, is(notNullValue()));
		assertThat(dataBeanIndexList.size(), is(equalTo(2)));
		assertThat(dataBeanIndexList.get(0), is(equalTo("5")));
		assertThat(dataBeanIndexList.get(1), is(equalTo("6")));
		List<String> h1006List = dataBean.get("H1006");
		assertThat(h1006List, is(notNullValue()));
	}
	
	@Test
	public void shouldReturnEmptyMessageWhenH1006ProvidedWithAuHeaderVariation() {
		//Given
		String[] strs = {"H1006", null, null, null, null, null, "", "P", "", "", "", null, null, null, null, null, null, null};
		Map<String, List<String>> templateParamMap = new HashMap<>();
		List<String> headers = Arrays.asList("Hole_id",	"Sample_id", "From", "To", "Sample_type", "Au", "Au", "Au variance", "Cu", "Pb", "Zn", "Drill code", "Prospect", "Date_Started", "Date_Completed", "Company", "Comments");
		templateParamMap.put(Strings.COLUMN_HEADERS, headers);
		H1006Validator testInstance= new H1006Validator();
		testInstance.init(strs);
		Template dataBean = new Dg4Template();
		//When
		Optional<List<String>> errorMessages = testInstance.validate(templateParamMap, dataBean);
		//Then
		assertThat(errorMessages.isPresent(), is(false));
		List<String> dataBeanIndexList = dataBean.get(Strings.KEY_PREFIX_DUPLICATED + "Au");
		assertThat(dataBeanIndexList, is(notNullValue()));
		assertThat(dataBeanIndexList.size(), is(equalTo(3)));
		assertThat(dataBeanIndexList.get(0), is(equalTo("5")));
		assertThat(dataBeanIndexList.get(1), is(equalTo("6")));
		assertThat(dataBeanIndexList.get(2), is(equalTo("7")));
		List<String> h1006List = dataBean.get("H1006");
		assertThat(h1006List, is(notNullValue()));
	}
	
	@Test
	public void shouldReturnEmptyMessageWhenH1006ProvidedWithOHeaderVariation() {
		//Given
		String[] strs = {"H1006", null, null, null, null, null, "", "P", "", "", "", null, null, null, null, null, null, null};
		Map<String, List<String>> templateParamMap = new HashMap<>();
		List<String> headers = Arrays.asList("Hole_id",	"Sample_id", "From", "To", "Sample_type", "O", "O1", "O variance", "Cu", "Pb", "Zn", "Drill code", "Prospect", "Date_Started", "Date_Completed", "Company", "Comments");
		templateParamMap.put(Strings.COLUMN_HEADERS, headers);
		H1006Validator testInstance= new H1006Validator();
		testInstance.init(strs);
		Template dataBean = new Dg4Template();
		//When
		Optional<List<String>> errorMessages = testInstance.validate(templateParamMap, dataBean);
		//Then
		assertThat(errorMessages.isPresent(), is(false));
		List<String> dataBeanIndexList = dataBean.get(Strings.KEY_PREFIX_DUPLICATED + "O");
		assertThat(dataBeanIndexList, is(notNullValue()));
		assertThat(dataBeanIndexList.size(), is(equalTo(3)));
		assertThat(dataBeanIndexList.get(0), is(equalTo("5")));
		assertThat(dataBeanIndexList.get(1), is(equalTo("6")));
		assertThat(dataBeanIndexList.get(2), is(equalTo("7")));
		List<String> h1006List = dataBean.get("H1006");
		assertThat(h1006List, is(notNullValue()));
	}
	
	@Test
	public void shouldReturnEmptyMessageWhenH1006ProvidedButNotDataBean() {
		//Given
		String[] strs = {"H1006", null, null, null, null, null, "", "P", "", "", "", null, null, null, null, null, null, null};
		Map<String, List<String>> templateParamMap = new HashMap<>();
		List<String> headers = Arrays.asList("Hole_id",	"Sample_id", "From", "To", "Sample_type", "Au", "Au", "As", "Cu", "Pb", "Zn", "Drill code", "Prospect", "Date_Started", "Date_Completed", "Company", "Comments");
		templateParamMap.put(Strings.COLUMN_HEADERS, headers);
		H1006Validator testInstance= new H1006Validator();
		testInstance.init(strs);
		Template dataBean = null;
		//When
		Optional<List<String>> errorMessages = testInstance.validate(templateParamMap, dataBean);
		//Then
		assertThat(errorMessages.isPresent(), is(false));
	}
	
	@Test
	public void shouldReturnEmptyMessageWhenH1006ProvidedWith6Columns() {
		//Given
		String[] strs = {"H1006", null, null, null, null, null, "P"};
		Map<String, List<String>> templateParamMap = new HashMap<>();
		List<String> headers = Arrays.asList("Hole_id",	"Sample_id", "From", "To", "Sample_type", "Au", "Au", "As", "Cu", "Pb", "Zn", "Drill code", "Prospect", "Date_Started", "Date_Completed", "Company", "Comments");
		templateParamMap.put(Strings.COLUMN_HEADERS, headers);
		H1006Validator testInstance= new H1006Validator();
		testInstance.init(strs);
		Template dataBean = new Dg4Template();
		//When
		Optional<List<String>> errorMessages = testInstance.validate(templateParamMap, dataBean);
		//Then
		assertThat(errorMessages.isPresent(), is(false));
		List<String> dataBeanIndexList = dataBean.get(Strings.KEY_PREFIX_DUPLICATED + "Au");
		assertThat(dataBeanIndexList, is(notNullValue()));
		assertThat(dataBeanIndexList.size(), is(equalTo(2)));
		assertThat(dataBeanIndexList.get(0), is(equalTo("5")));
		assertThat(dataBeanIndexList.get(1), is(equalTo("6")));
		List<String> h1006List = dataBean.get("H1006");
		assertThat(h1006List, is(notNullValue()));
	}
	
	@Test
	public void shouldReturnMissingPreferredResultMessageWhenH1006NotProvided() {
		//Given
		String[] strs = {"H1006", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null};
		Map<String, List<String>> templateParamMap = new HashMap<>();
		List<String> headers = Arrays.asList("Hole_id",	"Sample_id", "From", "To", "Sample_type", "Au", "Au", "As", "Cu", "Pb", "Zn", "Drill code", "Prospect", "Date_Started", "Date_Completed", "Company", "Comments");
		templateParamMap.put(Strings.COLUMN_HEADERS, headers);
		H1006Validator testInstance= new H1006Validator();
		testInstance.init(strs);
		Template dataBean = new Dg4Template();
		//When
		Optional<List<String>> errorMessages = testInstance.validate(templateParamMap, dataBean);
		//Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Au has NO preferred result provided")));
		List<String> h1006List = dataBean.get("H1006");
		assertThat(h1006List, is(nullValue()));
	}
	
	@Test
	public void shouldReturnMissingPreferredResultMessageWhenH1006NotProvidedWith6Columns() {
		//Given
		String[] strs = {"H1006", null, null, null, null, null, null};
		Map<String, List<String>> templateParamMap = new HashMap<>();
		List<String> headers = Arrays.asList("Hole_id",	"Sample_id", "From", "To", "Sample_type", "Au", "Au", "As", "Cu", "Pb", "Zn", "Drill code", "Prospect", "Date_Started", "Date_Completed", "Company", "Comments");
		templateParamMap.put(Strings.COLUMN_HEADERS, headers);
		H1006Validator testInstance= new H1006Validator();
		testInstance.init(strs);
		Template dataBean = new Dg4Template();
		//When
		Optional<List<String>> errorMessages = testInstance.validate(templateParamMap, dataBean);
		//Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Au has NO preferred result provided")));
		List<String> h1006List = dataBean.get("H1006");
		assertThat(h1006List, is(nullValue()));
	}
}

package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.fixture.TestFixture;

public class OptionalHeaderMandatoryFieldValidatorTest {

	private OptionalHeaderMandatoryFieldValidator testInstance;
	
	@Test
	public void shouldReturnEmptyMessageWhenH1001ValueProvided() {
		//Given
		givenTestInstance();
		List<String> messages = new ArrayList<>();
		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(true));
	}
	
	@Test
	public void shouldReturnMissingUomMessageWhenDataIsNotProvided() {
		//Given
		String[] strs = { "H1001", null, null, null, null, null, null, "ppm", "ppm", "ppm", "ppm" };
		List<String> headers = TestFixture.getDg4FullColumnHeaderList();
		List<String> mandatoryFields = TestFixture.getDg4H1001MadatoryFieldHeadersList();
		String header = "H1001";
		testInstance =
				new OptionalHeaderMandatoryFieldValidator(strs, headers,
						mandatoryFields, header);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Au requires value in H1001")));
	}
	
	@Test
	public void shouldReturnMissingUomMessageWhenDataIsProvidedWithVariationHeader() {
		//Given
		String[] strs = { "H1001", null, null, null, null, null, null, "ppm", "ppm", "ppm", "ppm" };
		List<String> headers = new ArrayList<>(TestFixture.getDg4ColumnHeaderList());
		headers.add("Sample_type");
		headers.add("Au variance");
		headers.add("Ag_variance");
		headers.add("Zn-variance");
		headers.add("Pb");
		headers.add("Cu");
		List<String> mandatoryFields = TestFixture.getDg4H1001MadatoryFieldHeadersList();
		String header = "H1001";
		testInstance =
				new OptionalHeaderMandatoryFieldValidator(strs, headers,
						mandatoryFields, header);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Au variance requires value in H1001")));
	}
	
	@Test
	public void shouldReturnMissingUomMessageWhenDataIsProvidedWithIndexVariationHeader() {
		//Given
		String[] strs = { "H1001", null, null, null, null, null, "ppm", "ppm", "ppm", null, null };
		List<String> headers = new ArrayList<>(TestFixture.getDg4ColumnHeaderList());
		headers.add("Sample_type");
		headers.add("Au variance");
		headers.add("Ag_variance");
		headers.add("Zn-variance");
		headers.add("O1");
		headers.add("O2");
		List<String> mandatoryFields = TestFixture.getDg4H1001MadatoryFieldHeadersList();
		String header = "H1001";
		testInstance =
				new OptionalHeaderMandatoryFieldValidator(strs, headers,
						mandatoryFields, header);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(2)));
		assertThat(messages.get(0), is(equalTo("ERROR: O1 requires value in H1001")));
		assertThat(messages.get(1), is(equalTo("ERROR: O2 requires value in H1001")));
	}
	
	@Test
	public void shouldReturnMissingUomMessageWhenDataIsProvidedWithCustomHeader() {
		//Given
		String[] strs = { "H1001", null, null, null, null, null, "ppm", "ppm", "ppm", "ppm", null };
		List<String> headers = new ArrayList<>(TestFixture.getDg4ColumnHeaderList());
		headers.add("Sample_type");
		headers.add("Au variance");
		headers.add("Ag_variance");
		headers.add("Zn-variance");
		headers.add("Pb");
		headers.add("My custom variance");
		List<String> mandatoryFields = new ArrayList<>(TestFixture
				.getDg4H1001MadatoryFieldHeadersList());
		mandatoryFields.add("My custom variance");
		String header = "H1001";
		testInstance =
				new OptionalHeaderMandatoryFieldValidator(strs, headers,
						mandatoryFields, header);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: My custom variance requires value in H1001")));
	}
	
	@Test
	public void shouldReturnCorrectIndex() {
		//Given
		givenTestInstance();
		//When
		//Then
		assertThat(testInstance.indexOf("Ag variance"), is(equalTo(2)));
		assertThat(testInstance.indexOf("Ag_variance"), is(equalTo(2)));
		assertThat(testInstance.indexOf("Ag-variance"), is(equalTo(2)));
		assertThat(testInstance.indexOf("Ag"), is(equalTo(-1)));
		assertThat(testInstance.indexOf("variance"), is(equalTo(-1)));
	}
	
	@Test
	public void shouldReturnInstance() {
		// Given
		givenTestInstance();
		// When
		// Then
		assertThat(testInstance, is(notNullValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenDataIsNull() {
		// Given
		String[] strs = null;
		List<String> headers = null;
		List<String> mandatoryFields = null;
		String header = null;
		// When
		new OptionalHeaderMandatoryFieldValidator(strs, headers,
				mandatoryFields, header);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenHeadersIsNull() {
		// Given
		String[] strs = TestFixture.getDg4H1001Data();
		List<String> headers = null;
		List<String> mandatoryFields = null;
		String header = null;
		// When
		new OptionalHeaderMandatoryFieldValidator(strs, headers,
				mandatoryFields, header);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenHeaderKeyIsNull() {
		// Given
		String[] strs = TestFixture.getDg4H1001Data();
		List<String> headers = TestFixture.getDg4FullColumnHeaderList();;
		List<String> mandatoryFields = TestFixture.getDg4H1001MadatoryFieldHeadersList();
		String header = null;
		// When
		new OptionalHeaderMandatoryFieldValidator(strs, headers,
				mandatoryFields, header);
		fail("Program reached unexpected point!");
	}
	
	private void givenTestInstance() {
		String[] strs = TestFixture.getDg4H1001Data();
		List<String> headers = TestFixture.getDg4FullColumnHeaderList();
		List<String> mandatoryFields = TestFixture.getDg4H1001MadatoryFieldHeadersList();
		String header = "H1001";
		testInstance =
				new OptionalHeaderMandatoryFieldValidator(strs, headers,
						mandatoryFields, header);
	}
}

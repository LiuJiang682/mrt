package au.gov.vic.ecodev.mrt.template.processor.validator.helper;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class IssueColumnIndexHelperTest {

	@Test
	public void shouldReturnIssueColumnIndex() {
		//Given
		IssueColumnIndexHelper testInstance = new IssueColumnIndexHelper();
		List<String> list = new ArrayList<>();
		list.add("0");
		//When
		int index = testInstance.getIssueColumnIndex(list);
		//Then
		assertThat(index, is(equalTo(0)));
	}
	

	
	@Test
	public void shouldReturnNotFoundIssueColumnIndexWhenNullListProvided() {
		//Given
		IssueColumnIndexHelper testInstance = new IssueColumnIndexHelper();
		List<String> list = null;
		//When
		int index = testInstance.getIssueColumnIndex(list);
		//Then
		assertThat(index, is(equalTo(-1)));
	}
	
	@Test
	public void shouldReturnNotFoundIssueColumnIndexWhenEmptyListProvided() {
		//Given
		IssueColumnIndexHelper testInstance = new IssueColumnIndexHelper();
		List<String> list = new ArrayList<>();
		//When
		int index = testInstance.getIssueColumnIndex(list);
		//Then
		assertThat(index, is(equalTo(-1)));
	}
	
	@Test
	public void shouldReturnNotFoundIssueColumnIndexWhenStringListProvided() {
		//Given
		IssueColumnIndexHelper testInstance = new IssueColumnIndexHelper();
		List<String> list = new ArrayList<>();
		list.add("abc");
		//When
		int index = testInstance.getIssueColumnIndex(list);
		//Then
		assertThat(index, is(equalTo(-1)));
	}
}

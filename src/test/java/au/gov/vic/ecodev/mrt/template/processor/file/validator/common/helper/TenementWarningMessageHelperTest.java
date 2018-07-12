package au.gov.vic.ecodev.mrt.template.processor.file.validator.common.helper;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TenementWarningMessageHelperTest {

	@Test
	public void shouldReturnNoTenementWarningMessage() {
		//Given
		List<String> messages = new ArrayList<>();
		//When
		new TenementWarningMessageHelper().contructNoTenementWarningMessage(messages);
		//Then
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("WARNING: No tenement number provided!")));
	}
}

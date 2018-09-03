package au.gov.vic.ecodev.mrt.data.record.cleaner.persistent.model;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class DefaultSessionTest {

	private DefaultSession testInstance;
	
	@Test
	public void shouldReturnInstance() {
		//Given
		long sessionId = 100l;
		List<String> templateList = Arrays.asList("MRT");
		//When
		testInstance = new DefaultSession(sessionId, templateList);
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenTemplateListIsNull() {
		//Given
		long sessionId = 100l;
		List<String> templateList = null;
		//When
		new DefaultSession(sessionId, templateList);
		fail("Program reached unexpected point!");
	}
}

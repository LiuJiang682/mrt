package au.gov.vic.ecodev.mrt.template.processor.updater.tables;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.dao.TemplateOptionalFieldDao;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.model.dg4.Dg4Template;

public class TemplateHeaderOptionalFieldUpdaterTest {

	private TemplateHeaderOptionalFieldUpdater testInstance;
	private TemplateOptionalFieldDao mockTemplateOptionalFieldDao;
	private Template template;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shouldUpdateDatabase() {
		//Given
		givenTestInstance();
		List<String> headers = TestFixture.getColumnHeaderListWithOptionalFields();
		template.put(Strings.KEY_H1000, headers);
		List<String> uomValues = Arrays.asList("", null, "", "ppb", "NA", "", "", null, null, "ppm");
		template.put("H1001", uomValues);
		List<String> assayCodes = Arrays.asList("", null, "", "BLEG", "", "", "", null, null, "ICP_OES");
		template.put("H1002", assayCodes);
		List<String> lowerDetectionLimits = Arrays.asList("", null, "", "0.01", "", "", "", null, null, "0.1");
		template.put("H1003", lowerDetectionLimits);
		//When
		testInstance.update();
		//Then
		ArgumentCaptor<List> entityCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockTemplateOptionalFieldDao).batchUpdate(entityCaptor.capture());
		List<List> capturedList = entityCaptor.getAllValues();
		assertThat(capturedList, is(notNullValue()));
		assertThat(capturedList.size(), is(equalTo(1)));
		assertThat(capturedList.get(0).size(), is(equalTo(7)));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		givenTestInstance();
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenTemplateIsNull() {
		//Given
		long sessionId = 100l;
		Template template = null;
		TemplateOptionalFieldDao templateOptionalFieldDao = null;
		List<String> keys = null;
		//When
		new TemplateHeaderOptionalFieldUpdater(sessionId, template, 
				templateOptionalFieldDao, keys);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenTemplateOptionalFieldDaoIsNull() {
		//Given
		long sessionId = 100l;
		Template template = new Dg4Template();
		TemplateOptionalFieldDao templateOptionalFieldDao = null;
		List<String> keys = null;
		//When
		new TemplateHeaderOptionalFieldUpdater(sessionId, template, 
				templateOptionalFieldDao, keys);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenKeysIsNull() {
		//Given
		long sessionId = 100l;
		Template template = new Dg4Template();
		TemplateOptionalFieldDao templateOptionalFieldDao = 
				Mockito.mock(TemplateOptionalFieldDao.class);;
		List<String> keys = null;
		//When
		new TemplateHeaderOptionalFieldUpdater(sessionId, template, 
				templateOptionalFieldDao, keys);
		fail("Program reached unexpected point!");
	}
	
	private void givenTestInstance() {
		long sessionId = 100l;
		template = new Dg4Template();
		mockTemplateOptionalFieldDao = 
				Mockito.mock(TemplateOptionalFieldDao.class);
		List<String> keys = new ArrayList<>();
		keys.add("H1001");
		keys.add("H1002");
		keys.add("H1003");
		
		testInstance = new TemplateHeaderOptionalFieldUpdater(sessionId, template, 
						mockTemplateOptionalFieldDao, keys);
	}
}

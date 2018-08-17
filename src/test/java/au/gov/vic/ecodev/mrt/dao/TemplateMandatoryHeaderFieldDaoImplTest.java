package au.gov.vic.ecodev.mrt.dao;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import au.gov.vic.ecodev.common.util.IDGenerator;
import au.gov.vic.ecodev.mrt.model.TemplateMandatoryHeaderField;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TemplateMandatoryHeaderFieldDaoImplTest {

	@Autowired
	private TemplateMandatoryHeaderFieldDaoImpl testInstance;
	
	private long id;
	
	@Test
	public void shouldInsertAndGetRecord() {
		//Given
		boolean flag = givenNewRecord();
		//When
		//Then
		assertThat(flag, is(true));
		TemplateMandatoryHeaderField retrievedTemplateMandatoryHeaderField = 
				(TemplateMandatoryHeaderField) testInstance.get(id);
		assertThat(retrievedTemplateMandatoryHeaderField, is(notNullValue()));
		assertThat(retrievedTemplateMandatoryHeaderField.getId(), is(equalTo(id)));
		assertThat(retrievedTemplateMandatoryHeaderField.getSessionId(), is(equalTo(100l)));
		assertThat(retrievedTemplateMandatoryHeaderField.getTemplateName(), is(equalTo("SL4.txt")));
		assertThat(retrievedTemplateMandatoryHeaderField.getColumnHeader(), is(equalTo("MGA_E")));
		assertThat(retrievedTemplateMandatoryHeaderField.getRowNumber(), is(equalTo("H1001")));
		assertThat(retrievedTemplateMandatoryHeaderField.getFieldValue(), is(equalTo("meters")));
	}
	
	private boolean givenNewRecord() {
		id = IDGenerator.getUIDAsAbsLongValue();
		TemplateMandatoryHeaderField entity = buildDTO();
		
		boolean flag = testInstance.updateOrSave(entity);
		return flag;
	}

	@Test
	public void shouldReturnInstance() {
		//Given
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	private TemplateMandatoryHeaderField buildDTO() {
		TemplateMandatoryHeaderField templateMandatoryHeaderField = 
				new TemplateMandatoryHeaderField();
		templateMandatoryHeaderField.setId(id);
		templateMandatoryHeaderField.setSessionId(100l);
		templateMandatoryHeaderField.setTemplateName("SL4");
		templateMandatoryHeaderField.setFileName("SL4.txt");
		templateMandatoryHeaderField.setRowNumber("H1001");
		templateMandatoryHeaderField.setColumnHeader("MGA_E");
		templateMandatoryHeaderField.setFieldValue("meters");
		return templateMandatoryHeaderField;
	}
}

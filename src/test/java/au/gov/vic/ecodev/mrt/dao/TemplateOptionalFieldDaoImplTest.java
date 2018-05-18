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
import au.gov.vic.ecodev.mrt.model.TemplateOptionalField;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TemplateOptionalFieldDaoImplTest {

	@Autowired
	private TemplateOptionalFieldDao templateOptionalFieldDao;
	
	private long id;

	@Test
	public void shouldInsertAndGetRecord() {
		//Given
		boolean flag = givenNewRecord();
		//When
		//Then
		assertThat(flag, is(true));
		TemplateOptionalField retrievedTemplateOptionalField = 
				(TemplateOptionalField) templateOptionalFieldDao.get(id);
		assertThat(retrievedTemplateOptionalField, is(notNullValue()));
		assertThat(retrievedTemplateOptionalField.getId(), is(equalTo(id)));
		assertThat(retrievedTemplateOptionalField.getSessionId(), is(equalTo(100l)));
		assertThat(retrievedTemplateOptionalField.getTemplateName(), is(equalTo("SL4")));
		assertThat(retrievedTemplateOptionalField.getTemplateHeader(), is(equalTo("Au")));
		assertThat(retrievedTemplateOptionalField.getRowNumber(), is(equalTo("1")));
		assertThat(retrievedTemplateOptionalField.getFieldValue(), is(equalTo("0.01")));
	}
	
	@Test
	public void shouldUpdateExistingRecord() {
		//Given
		boolean flag = givenNewRecord();
		assertThat(flag, is(true));
		TemplateOptionalField entity = new TemplateOptionalField();
		entity.setId(id);
		entity.setSessionId(100l);
		entity.setTemplateName("SL4");
		entity.setTemplateHeader("Au");
		entity.setRowNumber("1");
		entity.setFieldValue("0.02");
		//When
		boolean updateFlag = templateOptionalFieldDao.updateOrSave(entity);
		assertThat(updateFlag, is(true));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		//When
		//Then
		assertThat(templateOptionalFieldDao, is(notNullValue()));
	}
	
	private boolean givenNewRecord() {
		id = IDGenerator.getUID().longValue();
		long sessionId = 100l;
		String templateName = "SL4";
		String templateHeader = "Au";
		String rowNumber = "1";
		String fieldValue = "0.01";
		TemplateOptionalField entity = new TemplateOptionalField();
		entity.setId(id);
		entity.setSessionId(sessionId);
		entity.setTemplateName(templateName);
		entity.setTemplateHeader(templateHeader);
		entity.setRowNumber(rowNumber);
		entity.setFieldValue(fieldValue);
		
		boolean flag = templateOptionalFieldDao.updateOrSave(entity);
		return flag;
	}
}

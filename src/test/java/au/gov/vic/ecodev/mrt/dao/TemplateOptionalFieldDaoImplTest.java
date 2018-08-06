package au.gov.vic.ecodev.mrt.dao;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import au.gov.vic.ecodev.common.util.IDGenerator;
import au.gov.vic.ecodev.mrt.model.TemplateOptionalField;
import au.gov.vic.ecodev.mrt.template.processor.model.Entity;

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
	public void shouldInsertAListOfRecords() {
		//Given
		long id1 = IDGenerator.getUID().longValue();
		long id2 = IDGenerator.getUID().longValue();
		TemplateOptionalField entity1 = buildOptionalDTO();
		entity1.setId(id1);
		TemplateOptionalField entity2 = buildOptionalDTO();
		entity2.setId(id2);
		List<Entity> entities = new ArrayList<>();
		entities.add(entity1);
		entities.add(entity2);
		//When
		boolean flag = templateOptionalFieldDao.batchUpdate(entities);
		//Then
		assertThat(flag, is(true));
	}
	
	@Test
	public void shouldUpdateExistingRecord() {
		//Given
		boolean flag = givenNewRecord();
		assertThat(flag, is(true));
		TemplateOptionalField entity = new TemplateOptionalField();
		entity.setId(id);
		entity.setSessionId(100l);
		entity.setFileName("myTest.txt");
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
		TemplateOptionalField entity = buildOptionalDTO();
		
		boolean flag = templateOptionalFieldDao.updateOrSave(entity);
		return flag;
	}

	private TemplateOptionalField buildOptionalDTO() {
		long sessionId = 100l;
		String templateName = "SL4";
		String templateHeader = "Au";
		String rowNumber = "1";
		String fieldValue = "0.01";
		TemplateOptionalField entity = new TemplateOptionalField();
		entity.setId(id);
		entity.setSessionId(sessionId);
		entity.setFileName("myTest.txt");
		entity.setTemplateName(templateName);
		entity.setTemplateHeader(templateHeader);
		entity.setRowNumber(rowNumber);
		entity.setFieldValue(fieldValue);
		return entity;
	}
}

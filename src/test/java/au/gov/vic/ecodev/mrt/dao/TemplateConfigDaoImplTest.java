package au.gov.vic.ecodev.mrt.dao;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TemplateConfigDaoImplTest {

	@Autowired
	private TemplateConfigDao templateConfigDao;

	@Test
	public void shouldBeInstantiatedDao() {
		// Given
		// When
		// Then
		assertThat(templateConfigDao, is(notNullValue()));
	}

	@Test
	public void shouldReturnMrtTemplateClass() {
		// Given
		String template = "mrt";
		// When
		String classes = templateConfigDao.getTemplateClasses(template);
		// Then
		// assertThat(classes,
		// is(equalTo("SL4:au.gov.vic.ecodev.mrt.template.processor.sl4.Sl4TemplateProcessor,DS4,DL4,DG4")));
		assertThat(classes, is(equalTo("SL4:au.gov.vic.ecodev.mrt.template.processor.sl4.Sl4TemplateProcessor,DS4:au.gov.vic.ecodev.mrt.template.processor.ds4.Ds4TemplateProcessor,DL4:au.gov.vic.ecodev.mrt.template.processor.dl4.Dl4TemplateProcessor,DG4:au.gov.vic.ecodev.mrt.template.processor.dg4.Dg4TemplateProcessor,SG4:au.gov.vic.ecodev.mrt.template.processor.sg4.Sg4TemplateProcessor")));
	}
	
	@Test
	public void shouldReturnOwnerMails() {
		// Given
		String template = "mrt";
		// When
		String ownerEmails = templateConfigDao.getOwnerEmails(template);
		// Then
		assertThat(ownerEmails, is(equalTo("jiang.liu@ecodev.vic.gov.au")));
	}

	@Test
	public void shouldReturnEmptyTemplateClass() {
		// Given
		String template = "Dummy";
		// When
		String classes = templateConfigDao.getTemplateClasses(template);
		// Then
		assertThat(StringUtils.isBlank(classes), is(true));
	}
	
	@Test
	public void shouldReturnEmptyOwnerEmails() {
		// Given
		String template = "Dummy";
		// When
		String classes = templateConfigDao.getOwnerEmails(template);
		// Then
		assertThat(StringUtils.isBlank(classes), is(true));
	}

	@Test
	public void shouldReturnBlankTemplateClassWhenSqlInjection() {
		// Given
		String template = "  mrt' or '1'='1";
		// When
		String classes = templateConfigDao.getTemplateClasses(template);
		// Then
		assertThat(StringUtils.isBlank(classes), is(true));
	}
	
	@Test
	public void shouldReturnBlankOwnerEmailSqlInjection() {
		// Given
		String template = "  mrt' or '1'='1";
		// When
		String classes = templateConfigDao.getOwnerEmails(template);
		// Then
		assertThat(StringUtils.isBlank(classes), is(true));
	}
}

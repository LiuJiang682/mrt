package au.gov.vic.ecodev.mrt.dao;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Map;

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
		assertThat(classes, is(equalTo("SL4:au.gov.vic.ecodev.mrt.template.processor.sl4.Sl4TemplateProcessor:au.gov.vic.ecodev.mrt.template.files.H0202HeaderTemplateFileSelector:mandatory,DS4:au.gov.vic.ecodev.mrt.template.processor.ds4.Ds4TemplateProcessor:au.gov.vic.ecodev.mrt.template.files.H0202HeaderTemplateFileSelector,DL4:au.gov.vic.ecodev.mrt.template.processor.dl4.Dl4TemplateProcessor:au.gov.vic.ecodev.mrt.template.files.H0202HeaderTemplateFileSelector,DG4:au.gov.vic.ecodev.mrt.template.processor.dg4.Dg4TemplateProcessor:au.gov.vic.ecodev.mrt.template.files.H0202HeaderTemplateFileSelector,SG4:au.gov.vic.ecodev.mrt.template.processor.sg4.Sg4TemplateProcessor:au.gov.vic.ecodev.mrt.template.files.H0202HeaderTemplateFileSelector")));
	}
	
	@Test
	public void shouldReturnOwnerMails() {
		// Given
		String template = "mrt";
		// When
		Map<String, Object> ownerEmails = templateConfigDao.getOwnerEmailProperties(template);
		// Then
		assertThat(ownerEmails, is(notNullValue()));
		assertThat(ownerEmails.size(), is(equalTo(2)));
		assertThat(ownerEmails.get("OWNER_EMAILS"), is(equalTo("jiang.liu@ecodev.vic.gov.au")));
		assertThat(ownerEmails.get("EMAILS_BUILDER"), is(equalTo("au.gov.vic.ecodev.mrt.mail.MrtEmailBodyBuilder")));
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
		Map<String, Object> classes = templateConfigDao.getOwnerEmailProperties(template);
		// Then
		assertThat(classes, is(nullValue()));
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
		Map<String, Object> classes = templateConfigDao.getOwnerEmailProperties(template);
		// Then
		assertThat(classes, is(nullValue()));
	}
}

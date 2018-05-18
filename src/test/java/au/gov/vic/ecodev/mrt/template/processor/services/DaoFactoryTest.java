package au.gov.vic.ecodev.mrt.template.processor.services;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.dao.SessionHeaderDaoImpl;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.persistent.Dao;

public class DaoFactoryTest {

	private DaoFactory testInstance;
	
	@Test
	public void shouldReturnInstance() {
		//Given
		givenTestInstance();
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test
	public void shouldReturnListOfDaos() throws TemplateProcessorException {
		//Given
		givenTestInstance();
		//When
		List<Dao> daos = testInstance.getDaos();
		//Then
		assertThat(CollectionUtils.isEmpty(daos), is(false));
		assertThat(daos.size(), is(equalTo(1)));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenEntityManagerIsNull() {
		//Given
		JdbcTemplate mockJdbcTemplate = null;
		List<Class<? extends Dao>> daoClasses = null;
		//When
		new DaoFactory(mockJdbcTemplate, daoClasses);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenDaoClassListIsNull() {
		//Given
		JdbcTemplate mockJdbcTemplate = Mockito.mock(JdbcTemplate.class);
		List<Class<? extends Dao>> daoClasses = null;
		//When
		new DaoFactory(mockJdbcTemplate, daoClasses);
		fail("Program reached unexpected point!");
	}

	private void givenTestInstance() {
		JdbcTemplate mockJdbcTemplate = Mockito.mock(JdbcTemplate.class);
		List<Class<? extends Dao>> daoClasses = new ArrayList<>();
		daoClasses.add(SessionHeaderDaoImpl.class);

		testInstance = new DaoFactory(mockJdbcTemplate, daoClasses);
	}
}

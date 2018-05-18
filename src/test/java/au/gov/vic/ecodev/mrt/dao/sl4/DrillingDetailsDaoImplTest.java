package au.gov.vic.ecodev.mrt.dao.sl4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import au.gov.vic.ecodev.mrt.model.sl4.DrillingDetails;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class DrillingDetailsDaoImplTest {

	private long id;
	private String drillingType;
	private String drillingCompany;
	private String drillingDescription;

	@Autowired
	private DrillingDetailsDao drillingDetailsDao;

	@Test
	public void shouldBeInstantiatedDao() {
		// Given
		// When
		// Then
		assertThat(drillingDetailsDao, is(notNullValue()));
	}

	@Test
	public void shouldUpdateRecord() {
		// Given
		boolean flag = givenUpdate();
		// When
		// That
		assertThat(flag, is(true));
	}

	@Test
	public void ReturnDrillingDetailsRecordWhenIdProvided() {
		// Given
		boolean flag = givenUpdate();
		assertThat(flag, is(true));
		// When
		DrillingDetails retrievedGetDrillingDetails = (DrillingDetails) drillingDetailsDao.get(id);
		// Then
		assertThat(retrievedGetDrillingDetails, is(notNullValue()));
		assertThat(retrievedGetDrillingDetails.getId(), is(equalTo(1L)));
		assertThat(retrievedGetDrillingDetails.getDrillDescription(), is(equalTo("Diamond drilling")));
	}

	@Test
	public void shouldReturnDrillingDetailsRecordWhenDrillTypeAndDrillCompanyProvided() {
		// Given
		boolean flag = givenUpdate();
		assertThat(flag, is(true));
		// When
		DrillingDetails retrievedDrillingDetails = drillingDetailsDao.getByDrillingTypeAndCompany(drillingType,
				drillingCompany);
		
		// Then
		assertThat(retrievedDrillingDetails, is(notNullValue()));
		assertThat(retrievedDrillingDetails.getId(), is(equalTo(1L)));
		assertThat(retrievedDrillingDetails.getDrillDescription(), is(equalTo("Diamond drilling")));
	}

	@Test
	public void shouldReturnNullWhenDrillTypeOrDrillCompanyIsRubbish() {
		// Given
		String drillingCompany = "Drill Faster Pty Ltd";
		String drillingType = "DD1";
		// When
		DrillingDetails retrievedDrillingDetails = drillingDetailsDao.getByDrillingTypeAndCompany(drillingType,
				drillingCompany);
		// Then
		assertThat(retrievedDrillingDetails, is(nullValue()));
	}
	
	@Test
	public void shouldReturnNullWhenDrillTypeOrDrillCompanyIsSqlInjected() {
		// Given
		boolean flag = givenUpdate();
		assertThat(flag, is(true));
		String drillingCompany = "Drill Faster Pty Ltd'; DROP TABLE DRILLING_DETAILS; -- '";
		String drillingType = "DD";
		// When
		DrillingDetails retrievedDrillingDetails = drillingDetailsDao.getByDrillingTypeAndCompany(drillingType,
				drillingCompany);
		// Then
		assertThat(retrievedDrillingDetails, is(nullValue()));
		DrillingDetails existingDrillingDetails = (DrillingDetails) drillingDetailsDao.get(1l);
		assertThat(existingDrillingDetails, is(notNullValue()));
	}
	
	@Test
	public void shouldNotUpdateTheCommentColumnWhenSqlInjected() {
		//Given
		id = 100l;
		drillingType = "DD";
		drillingCompany = "Drill Faster Pty Ltd";
		drillingDescription = "Diamond drilling";

		DrillingDetails drillingDetails = new DrillingDetails();
		drillingDetails.setId(id);
		drillingDetails.setDrillType(drillingType);
		drillingDetails.setDrillCompany(drillingCompany);
		drillingDetails.setDrillDescription(drillingDescription);
		
		boolean flag = drillingDetailsDao.updateOrSave(drillingDetails);
		assertThat(flag, is(true));
		DrillingDetails existingDrillingDetails = (DrillingDetails) drillingDetailsDao.get(100l);
		assertThat(existingDrillingDetails, is(notNullValue()));
		assertThat(existingDrillingDetails.getDrillDescription(), is(equalTo(drillingDescription)));
		existingDrillingDetails.setDrillDescription("Diamond drilling'; DROP TABLE DRILLING_DETAILS; -- '");
		// When
		boolean newflag = drillingDetailsDao.updateOrSave(existingDrillingDetails);
		//Then
		assertThat(newflag, is(true));
		DrillingDetails sqlInjectedDrillingDetails = (DrillingDetails) drillingDetailsDao.get(100l);
		assertThat(sqlInjectedDrillingDetails, is(notNullValue()));
		assertThat(sqlInjectedDrillingDetails.getDrillDescription(), is(equalTo("Diamond drilling'; DROP TABLE DRILLING_DETAILS; -- '")));
	}

	@Test
	public void shouldReturnNullWhenIDIsRubbish() {
		// Given
		long id = 0;
		// When
		DrillingDetails retrievedDrillingDetails = (DrillingDetails) drillingDetailsDao.get(id);
		// Then
		assertThat(retrievedDrillingDetails, is(nullValue()));
	}

	private boolean givenUpdate() {
		id = 1l;
		drillingType = "DD";
		drillingCompany = "Drill Faster Pty Ltd";
		drillingDescription = "Diamond drilling";

		DrillingDetails drillingDetails = new DrillingDetails();
		drillingDetails.setId(id);
		drillingDetails.setDrillType(drillingType);
		drillingDetails.setDrillCompany(drillingCompany);
		drillingDetails.setDrillDescription(drillingDescription);
		// When
		return drillingDetailsDao.updateOrSave(drillingDetails);
	}
}

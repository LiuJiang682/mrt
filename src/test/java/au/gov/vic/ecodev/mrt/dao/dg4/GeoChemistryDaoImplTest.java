package au.gov.vic.ecodev.mrt.dao.dg4;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import au.gov.vic.ecodev.common.util.IDGenerator;
import au.gov.vic.ecodev.mrt.model.dg4.GeoChemistry;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class GeoChemistryDaoImplTest {

	@Autowired
	private GeoChemistryDao geoChemistryDao;
	
	private long id;
	
	@Test
	public void shouldUpdateExistRecord() {
		//Given
		boolean flag = givenNewRecord();
		assertThat(flag, is(true));
		GeoChemistry geoChemistry = new GeoChemistry();
		geoChemistry.setId(id);
		geoChemistry.setLoaderId(100l);
		geoChemistry.setHoleId("KPDD001");
		geoChemistry.setSampleId("kkk");
		geoChemistry.setFrom(BigDecimal.ZERO);
		geoChemistry.setTo(BigDecimal.TEN);
		geoChemistry.setDrillCode("BD");
		//When
		boolean updateFlag = geoChemistryDao.updateOrSave(geoChemistry);
		//Then
		assertThat(updateFlag, is(true));
	}
	
	@Test
	public void shouldInsertNewRecord() {
		//Given
		boolean flag = givenNewRecord();
		//When
		//Then
		assertThat(flag, is(true));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void shouldRaiseExceptionWhenCallGet() {
		// Given
		long id = 1l;
		// When
		geoChemistryDao.get(id);
		fail("Program reached unexpected point!");
	}
	
	private boolean givenNewRecord() {
		GeoChemistry geoChemistry = new GeoChemistry();
		id = IDGenerator.getUID().longValue();
		geoChemistry.setId(id);
		geoChemistry.setLoaderId(100l);
		geoChemistry.setHoleId("KPDD001");
		geoChemistry.setSampleId("kkk");
		geoChemistry.setFrom(BigDecimal.ZERO);
		geoChemistry.setTo(BigDecimal.TEN);
		geoChemistry.setDrillCode("DD");
		//When
		boolean flag = geoChemistryDao.updateOrSave(geoChemistry);
		return flag;
	}
}

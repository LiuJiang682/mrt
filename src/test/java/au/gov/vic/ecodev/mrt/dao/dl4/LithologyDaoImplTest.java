package au.gov.vic.ecodev.mrt.dao.dl4;

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
import au.gov.vic.ecodev.mrt.model.dl4.Lithology;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LithologyDaoImplTest {

	@Autowired
	private LithologyDao lithologyDao;
	
	private long id;
	
	@Test
	public void shouldUpdateExistingRecord() {
		//Given
		boolean flag = givenNewRecord();
		assertThat(flag, is(true));
		Lithology lithology = new Lithology();
		lithology.setId(id);
		lithology.setLoaderId(1l);
		lithology.setHoleId("KPDD001");
		lithology.setFileName("myTest.txt");
		lithology.setDepthFrom(new BigDecimal("3"));
		//When
		boolean updateFlag = lithologyDao.updateOrSave(lithology);
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
		lithologyDao.get(id);
		fail("Program reached unexpected point!");
	}
	
	private boolean givenNewRecord() {
		Lithology lithology = new Lithology();
		id = IDGenerator.getUID().longValue();
		lithology.setId(id);
		lithology.setLoaderId(1l);
		lithology.setHoleId("KPDD001");
		lithology.setFileName("myTest.txt");
		lithology.setDepthFrom(BigDecimal.TEN);
		//When
		boolean flag = lithologyDao.updateOrSave(lithology);
		return flag;
	}
}

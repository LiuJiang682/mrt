package au.gov.vic.ecodev.mrt.dao.ds4;

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
import au.gov.vic.ecodev.mrt.model.ds4.DownHole;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class DownHoleDaoImplTest {

	@Autowired
	private DownHoleDao downHoleDao;
	
	private long id;
	
	@Test
	public void shouldUpdateExistingRecord() {
		//Given
		boolean flag = givenNewRecord();
		assertThat(flag, is(true));
		DownHole downHole = new DownHole();
		downHole.setId(id);
		downHole.setLoaderId(2l);
		downHole.setHoleId("KPDD001");
		downHole.setFileName("myTest.txt");
		downHole.setRowNumber("1");
		downHole.setSurveyedDepth(BigDecimal.TEN);
		downHole.setAzimuthMag(new BigDecimal("60"));
		downHole.setDip(new BigDecimal("-62"));
		//When
		boolean newflag = downHoleDao.updateOrSave(downHole);
		assertThat(newflag, is(true));
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
		downHoleDao.get(id);
		fail("Program reached unexpected point!");
	}
	
	private boolean givenNewRecord() {
		DownHole downHole = new DownHole();
		id = IDGenerator.getUID().longValue();
		downHole.setId(id);
		downHole.setLoaderId(2l);
		downHole.setHoleId("KPDD001");
		downHole.setFileName("myTest.txt");
		downHole.setRowNumber("1");
		downHole.setSurveyedDepth(BigDecimal.TEN);
		downHole.setAzimuthMag(new BigDecimal("60"));
		downHole.setDip(new BigDecimal("-61"));
		
		boolean flag = downHoleDao.updateOrSave(downHole);
		return flag;
	}
}

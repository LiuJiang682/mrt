package au.gov.vic.ecodev.mrt.dao.sl4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import au.gov.vic.ecodev.mrt.model.sl4.BoreHole;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class BoreHoleDaoImplTest {

	@Autowired
	private BoreHoleDao boreHoleDao;
	
	private long loaderId;
	private String holeId;

	@Test
	public void shouldUpdateAExistingRecord() {
		// Given
		boolean flag = givenANewRecord();
		assertThat(flag, is(true));
		BoreHole boreHole = boreHoleDao.getBySessionIdAndHoleId(loaderId, holeId);
		assertThat(boreHole.getAzimuthMag(), is(nullValue()));
		boreHole.setAzimuthMag(new BigDecimal("535"));
		// When
		boolean updatedFlag = boreHoleDao.updateOrSave(boreHole);
		// Then
		assertThat(updatedFlag, is(true));
		BoreHole updatedBoreHole = boreHoleDao.getBySessionIdAndHoleId(loaderId, holeId);
		assertThat(updatedBoreHole.getAzimuthMag(), is(equalTo(new BigDecimal("535"))));
	}

	@Test
	public void shouldInsertANewRecord() {
		// Given
		boolean flag = givenANewRecord();
		// When
		// Then
		assertThat(flag, is(true));
		BoreHole retrieved = boreHoleDao.getBySessionIdAndHoleId(100l, "KPDD001");
		assertThat(retrieved, is(notNullValue()));
	}

	@Test
	public void shouldReturnABoreHoleRecord() {
		// Given
		long sessionId = 1l;
		String holeId = "KPDD001";
		// When
		BoreHole boreHole = boreHoleDao.getBySessionIdAndHoleId(sessionId, holeId);
		// Then
		assertThat(boreHole, is(notNullValue()));
		assertThat(boreHole.getLoaderId(), is(equalTo(1l)));
		assertThat(boreHole.getHoleId(), is(equalTo("KPDD001")));
		assertThat(boreHole.getBhAuthorityCd(), is(equalTo("U")));
		assertThat(boreHole.getBhRegulationCd(), is(equalTo("N/A")));
		Date startDate = boreHole.getDrillingStartDate();
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		assertDate(start);
		Date endDate = boreHole.getDrillingCompletionDate();
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		assertDate(end);
		assertThat(boreHole.getMdDepth(), is(equalTo(new BigDecimal("210"))));
		assertThat(boreHole.getElevationKb(), is(equalTo(new BigDecimal("320"))));
		assertThat(boreHole.getAzimuthMag(), is(nullValue()));
		assertThat(boreHole.getBhConfidentialFlag(), is(equalTo("Y")));
	}

	@Test
	public void shouldReturnNullBoreHoleRecordWhenKeysNotExist() {
		// Given
		long sessionId = 2l;
		String holeId = "KPDD001";
		// When
		BoreHole boreHole = boreHoleDao.getBySessionIdAndHoleId(sessionId, holeId);
		// Then
		assertThat(boreHole, is(nullValue()));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void shouldRaiseExceptionWhenCallGet() {
		// Given
		long id = 1l;
		// When
		boreHoleDao.get(id);
		fail("Program reached unexpected point!");
	}

	@Test
	public void shouldBeInstantiatedDao() {
		// Given
		// When
		// Then
		assertThat(boreHoleDao, is(notNullValue()));
	}

	private void assertDate(Calendar c) {
		Calendar now = Calendar.getInstance();
		assertThat(c.get(Calendar.YEAR), is(equalTo(now.get(Calendar.YEAR))));
		assertThat(c.get(Calendar.MONTH), is(equalTo(now.get(Calendar.MONTH))));
		assertThat(c.get(Calendar.DAY_OF_MONTH), is(equalTo(now.get(Calendar.DAY_OF_MONTH))));
	}

	private boolean givenANewRecord() {
		this.loaderId = 100l;
		this.holeId = "KPDD001";
		BoreHole boreHole = new BoreHole();
		boreHole.setLoaderId(loaderId);
		boreHole.setHoleId(holeId);
		boreHole.setBhAuthorityCd("U");
		boreHole.setBhRegulationCd("N/A");
		boreHole.setDillingDetailsId(1l);
		boreHole.setDrillingStartDate(new Date());
		boreHole.setDrillingCompletionDate(new Date());
		boreHole.setMdDepth(new BigDecimal("210"));
		boreHole.setElevationKb(new BigDecimal("320"));
		boreHole.setAzimuthMag(null);
		boreHole.setBhConfidentialFlag("Y");
		boreHole.setFileName("myTest.txt");
		
		boolean flag = boreHoleDao.updateOrSave(boreHole);
		return flag;
	}
}

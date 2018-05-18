package au.gov.vic.ecodev.mrt.dao.sg4;

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
import au.gov.vic.ecodev.mrt.model.sg4.SurfaceGeochemistry;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SurfaceGeochemistryDaoImplTest {

	@Autowired
	private SurfaceGeochemistryDao surfaceGeochemistryDao;
	
	private long id;
	
	@Test
	public void shouldUpdateExistingRecord() {
		//Given
		boolean flag = givenNewRecord();
		assertThat(flag, is(true));
		SurfaceGeochemistry surfaceGeochemistry = new SurfaceGeochemistry();
		surfaceGeochemistry.setId(id);
		surfaceGeochemistry.setLoaderId(100l);
		surfaceGeochemistry.setSampleId("abc123");
		surfaceGeochemistry.setEasting(BigDecimal.TEN);
		surfaceGeochemistry.setNorthing(BigDecimal.ONE);
		surfaceGeochemistry.setSampleType("ff");
		//When
		boolean updateFlag = surfaceGeochemistryDao.updateOrSave(surfaceGeochemistry);
		//Then
		assertThat(updateFlag, is(true));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void shouldRaiseExceptionWhenCallGet() {
		//Given
		long id = 1l;
		//When
		surfaceGeochemistryDao.get(id);
		fail("Program reached unexpected point!");
	}
	
	@Test
	public void shouldInsertRecord() {
		//Given
		boolean flag = givenNewRecord();
		//When
		//Then
		assertThat(flag, is(true));
	}

	private boolean givenNewRecord() {
		SurfaceGeochemistry surfaceGeochemistry = new SurfaceGeochemistry();
		id = IDGenerator.getUID().longValue();
		surfaceGeochemistry.setId(id);
		surfaceGeochemistry.setLoaderId(100l);
		surfaceGeochemistry.setSampleId("abc123");
		surfaceGeochemistry.setEasting(BigDecimal.TEN);
		surfaceGeochemistry.setNorthing(BigDecimal.ONE);
		surfaceGeochemistry.setSampleType("dd");
		return surfaceGeochemistryDao.updateOrSave(surfaceGeochemistry);
	}
}

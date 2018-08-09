package au.gov.vic.ecodev.mrt.dao.sl4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import au.gov.vic.ecodev.mrt.model.sl4.Site;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SiteDaoImplTest {

	@Autowired
	private SiteDao siteDao;

	private long sessionId;
	private String siteId;

	@Test
	public void shouldUpdateARecord() {
		// Given
		boolean flag = givenANewRecord(1001l, "abc123");
		assertThat(flag, is(true));
		Site site = siteDao.getSiteBySessionIdAndSiteId(sessionId, siteId);
		site.setProspect("new Prospect");
		// When
		boolean updateFlag = siteDao.updateOrSave(site);
		// Then
		assertThat(updateFlag, is(true));
		Site updatedSite = siteDao.getSiteBySessionIdAndSiteId(sessionId, siteId);
		assertThat(updatedSite, is(notNullValue()));
		assertThat(updatedSite.getProspect(), is(equalTo("new Prospect")));
	}

	@Test
	public void shouldInsertNewRecord() {
		// Given
		boolean flag = givenANewRecord(1000l, "abc123");
		// When
		// Then
		assertThat(flag, is(true));
		Site newSite = siteDao.getSiteBySessionIdAndSiteId(sessionId, siteId);
		assertThat(newSite, is(notNullValue()));
		assertThat(newSite.getNorthing(), is(equalTo(new BigDecimal("6589600"))));
	}

	@Test
	public void shouldReturnSiteRecord() {
		// Given
		long sessionId = 1l;
		String siteId = "KPDD001";
		// When
		Site site = siteDao.getSiteBySessionIdAndSiteId(sessionId, siteId);
		// Then
		assertThat(site, is(notNullValue()));
		assertThat(site.getLoaderId(), is(equalTo(1l)));
		assertThat(site.getSiteId(), is(equalTo("KPDD001")));
		assertThat(site.getGsvSiteId(), is(equalTo(0l)));
		assertThat(site.getParish(), is(equalTo("N/A")));
		assertThat(site.getProspect(), is(equalTo("Kryptonite")));
		assertThat(site.getAmgZone(), is(equalTo(new BigDecimal("55"))));
		assertThat(site.getEasting(), is(equalTo(new BigDecimal("392200"))));
		assertThat(site.getNorthing(), is(equalTo(new BigDecimal("6589600"))));
		assertThat(site.getLatitude(), is(equalTo(BigDecimal.ZERO)));
		assertThat(site.getLongitude(), is(equalTo(BigDecimal.ZERO)));
		assertThat(site.getLocnAcc(), is(equalTo(BigDecimal.ZERO)));
		assertThat(site.getLocnDatumCd(), is(equalTo("GDA94")));
		assertThat(site.getElevationGl(), is(equalTo(BigDecimal.ZERO)));
		assertThat(site.getElevAcc(), is(equalTo(BigDecimal.ZERO)));
		assertThat(site.getElevDatumCd(), is(equalTo("AHD")));
		assertThat(site.getFileName(), is(equalTo("myTest.txt")));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void shouldRaiseExceptionWhenCallGet() {
		// Given
		long id = 1l;
		// When
		siteDao.get(id);
		fail("Program reached unexpected point!");
	}

	@Test
	public void shouldBeInstantiatedDao() {
		// Given
		// When
		// Then
		assertThat(siteDao, is(notNullValue()));
	}

	private boolean givenANewRecord(long sessionId, String siteId) {
		this.sessionId = sessionId;
		this.siteId = siteId;
		Site site = new Site();
		site.setLoaderId(sessionId);
		site.setSiteId(siteId);
		site.setRowNumber("1");
		site.setParish("N/A");
		site.setProspect("Kryptonice");
		site.setAmgZone(new BigDecimal("54"));
		site.setEasting(new BigDecimal("392200"));
		site.setNorthing(new BigDecimal("6589600"));
		site.setLatitude(BigDecimal.ZERO);
		site.setLongitude(BigDecimal.ZERO);
		site.setLocnAcc(BigDecimal.ZERO);
		site.setLocnDatumCd("GDA94");
		site.setElevDatumCd("AHD");
		site.setNumberOfDataRecord(-1);
		site.setIssueColumnIndex(-1);
		site.setFileName("myTest.txt");
		// When
		return siteDao.updateOrSave(site);
	}
}

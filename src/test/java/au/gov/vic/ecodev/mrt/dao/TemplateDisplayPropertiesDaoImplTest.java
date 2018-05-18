package au.gov.vic.ecodev.mrt.dao;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TemplateDisplayPropertiesDaoImplTest {

	@Autowired
	private TemplateDisplayPropertiesDao templateDisplayPropertiesDao;
	
	@Test
	public void shouldReturnMrtDisplayProperties() {
		//Given
		String template = "MRT";
		//When
		String templateDisplayProperties = templateDisplayPropertiesDao
				.getDisplayProperties(template);
		//Then
		assertThat(templateDisplayProperties, is(notNullValue()));
		assertThat(templateDisplayProperties, is(equalTo("{\"SL4\":[{\"LOC_SITE\":\"SITE_ID,EASTING,NORTHING,LATITUDE,LONGITUDE\"},{\"DH_BOREHOLE\":\"DRILL_TYPE,DEPTH,ELEVATION_KB,AZIMUTH_MAG-SQL:SELECT b.DRILL_TYPE, a.DEPTH, a.ELEVATION_KB, a.AZIMUTH_MAG FROM DH_BOREHOLE a, DH_DRILLING_DETAILS b where a.DILLING_DETAILS_ID = b.ID AND a.LOADER_ID = ?\"}],\"DS4\":[{\"DH_DOWNHOLE\":\"SURVEYED_DEPTH,AZIMUTH_MAG,DIP\"}]}")));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		//When
		//Then
		assertThat(templateDisplayPropertiesDao, is(notNullValue()));
	}
}

package au.gov.vic.ecodev.mrt.map.services;

import static org.hamcrest.core.Is.is;
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

import au.gov.vic.ecodev.mrt.config.MrtConfig;
import au.gov.vic.ecodev.mrt.config.MrtConfigProperties;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class VictoriaMapServicesSdoImplTest {

	@Autowired
	private MrtConfig mrtConfig;
	
	private VictoriaMapServicesSdoImpl testInstance;
	
	@Test
	public void shouldReturnTrueWhenIsWithinMga54NorthEastCalledWithSwanHillCoorindate() {
		//Given
		givenTestInstance();
		//54H 732156.87610377 6086499.6270333
		BigDecimal easting = new BigDecimal("732156.87610377");
		BigDecimal northing = new BigDecimal("6086499.6270333");
		//When
		boolean flag = testInstance.isWithinMga54NorthEast(easting, northing);
		//Then
		assertThat(flag, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenIsWithinMga54NorthEastCalledWithWilcanniaCoorindate() {
		//Given
		givenTestInstance();
		//54J 725776.68192657 6506303.17651557
		BigDecimal easting = new BigDecimal("725776.68192657");
		BigDecimal northing = new BigDecimal("6506303.17651557");
		//When
		boolean flag = testInstance.isWithinMga54NorthEast(easting, northing);
		//Then
		assertThat(flag, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenIsWithinMga55NorthEastCalledWithLakeEntranceCoorindate() {
		//Given
		givenTestInstance();
		//55H 587607.98439159 5810236.29552759
		BigDecimal easting = new BigDecimal("587607.98439159");
		BigDecimal northing = new BigDecimal("5810236.29552759");
		//When
		boolean flag = testInstance.isWithinMga55NorthEast(easting, northing);
		//Then
		assertThat(flag, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenIsWithinMga55NorthEastCalledWithBombalaCoorindate() {
		//Given
		givenTestInstance();
		//55H 699758.47554411 5912594.31684648
		BigDecimal easting = new BigDecimal("699758.47554411");
		BigDecimal northing = new BigDecimal("5912594.31684648");
		//When
		boolean flag = testInstance.isWithinMga55NorthEast(easting, northing);
		//Then
		assertThat(flag, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenIsWithinMga54LatitudeLongitudeCalledWithSwanHillCoorindate() {
		//Given
		givenTestInstance();
		//-35.33963 143.55248
		BigDecimal latitude = new BigDecimal("-35.33963");
		BigDecimal longitude = new BigDecimal("143.55248");
		//When
		boolean flag = testInstance.isWithinMga54LatitudeLongitude(latitude, longitude);
		//Then
		assertThat(flag, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenIsWithinMga54LatitudeLongitudeCalledWithWilcanniaCoorindate() {
		//Given
		givenTestInstance();
		//-31.55618 143.37860
		BigDecimal latitude = new BigDecimal("-31.55618");
		BigDecimal longitude = new BigDecimal("143.37860");
		//When
		boolean flag = testInstance.isWithinMga54LatitudeLongitude(latitude, longitude);
		//Then
		assertThat(flag, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenIsWithinMga55LatitudeLongitudeCalledWithLakeEntranceCoorindate() {
		//Given
		givenTestInstance();
		//-37.85112 147.99582
		BigDecimal latitude = new BigDecimal("-37.85112");
		BigDecimal longitude = new BigDecimal("147.99582");
		//When
		boolean flag = testInstance.isWithinMga55LatitudeLongitude(latitude, longitude);
		//Then
		assertThat(flag, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenIsWithinMga55LatitudeLongitudeCalledWithBombalaCoorindate() {
		//Given
		givenTestInstance();
		//-36.91152 149.24232
		BigDecimal latitude = new BigDecimal("-36.91152");
		BigDecimal longitude = new BigDecimal("149.24232");
		//When
		boolean flag = testInstance.isWithinMga54LatitudeLongitude(latitude, longitude);
		//Then
		assertThat(flag, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenIsWithinAgd54NorthEastCalledWithSwanHillCoorindate() {
		//Given
		givenTestInstance();
		//54H 732156.87610377 6086499.6270333
		BigDecimal easting = new BigDecimal("732156.87610377");
		BigDecimal northing = new BigDecimal("6086499.6270333");
		//When
		boolean flag = testInstance.isWithinAgd54NorthEast(easting, northing);
		//Then
		assertThat(flag, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenIsWithinAgd54NorthEastCalledWithWilcanniaCoorindate() {
		//Given
		givenTestInstance();
		//54J 725776.68192657 6506303.17651557
		BigDecimal easting = new BigDecimal("725776.68192657");
		BigDecimal northing = new BigDecimal("6506303.17651557");
		//When
		boolean flag = testInstance.isWithinAgd54NorthEast(easting, northing);
		//Then
		assertThat(flag, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenIsWithinAgd55NorthEastCalledWithLakeEntranceCoorindate() {
		//Given
		givenTestInstance();
		//55H 587607.98439159 5810236.29552759
		BigDecimal easting = new BigDecimal("587607.98439159");
		BigDecimal northing = new BigDecimal("5810236.29552759");
		//When
		boolean flag = testInstance.isWithinAgd55NorthEast(easting, northing);
		//Then
		assertThat(flag, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenIsWithinAgd55NorthEastCalledWithBombalaCoorindate() {
		//Given
		givenTestInstance();
		//55H 699758.47554411 5912594.31684648
		BigDecimal easting = new BigDecimal("699758.47554411");
		BigDecimal northing = new BigDecimal("5912594.31684648");
		//When
		boolean flag = testInstance.isWithinAgd55NorthEast(easting, northing);
		//Then
		assertThat(flag, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenIsWithTenmentMga54LatitudeLongitudeCalledWithCorrectData() {
		//Given
		givenTestInstance();
		String tenement = "EL803";
		BigDecimal latitude = new BigDecimal("-35.33781");
		BigDecimal longitude = new BigDecimal("143.5544");
		//When
		boolean flag = testInstance.isWithinTenementMga54LatitudeLongitude(tenement, latitude, longitude);
		//Then
		assertThat(flag, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenIsWithTenmentMga54LatitudeLongitudeCalledWithInCorrectTenment() {
		//Given
		givenTestInstance();
		String tenement = "EL1803";
		BigDecimal latitude = new BigDecimal("-35.33781");
		BigDecimal longitude = new BigDecimal("143.5544");
		//When
		boolean flag = testInstance.isWithinTenementMga54LatitudeLongitude(tenement, latitude, longitude);
		//Then
		assertThat(flag, is(false));
	}
	
	@Test
	public void shouldReturnFalseWhenIsWithTenmentMga54LatitudeLongitudeCalledWithInCorrectLatitude() {
		//Given
		givenTestInstance();
		String tenement = "EL803";
		BigDecimal latitude = new BigDecimal("-45.33781");
		BigDecimal longitude = new BigDecimal("143.5544");
		//When
		boolean flag = testInstance.isWithinTenementMga54LatitudeLongitude(tenement, latitude, longitude);
		//Then
		assertThat(flag, is(false));
	}
	
	@Test
	public void shouldReturnFalseWhenIsWithTenmentMga54LatitudeLongitudeCalledWithInCorrectLongitude() {
		//Given
		givenTestInstance();
		String tenement = "EL803";
		BigDecimal latitude = new BigDecimal("-35.33781");
		BigDecimal longitude = new BigDecimal("153.5544");
		//When
		boolean flag = testInstance.isWithinTenementMga54LatitudeLongitude(tenement, latitude, longitude);
		//Then
		assertThat(flag, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenIsWithTenmentMga55LatitudeLongitudeCalledWithCorrectData() {
		//Given
		givenTestInstance();
		String tenement = "EL988";
		BigDecimal latitude = new BigDecimal("-37.6999972");
		BigDecimal longitude = new BigDecimal("148.4499982");
		//When
		boolean flag = testInstance.isWithinTenementMga54LatitudeLongitude(tenement, latitude, longitude);
		//Then
		assertThat(flag, is(true));
	}
	
	@Test
	public void shouldReturnTrueWhenIsWithinMga54TenmentNorthEastCalledWithSwanHillCoorindate() {
		//Given
		givenTestInstance();
		//54H 732156.87610377 6086499.6270333
		BigDecimal easting = new BigDecimal("732156.87610377");
		BigDecimal northing = new BigDecimal("6086499.6270333");
		String tno = "EL803";
		//When
		boolean flag = testInstance.isWithinTenementMga54NorthEast(tno, easting, northing);
		//Then
		assertThat(flag, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenIsWithinMga54TenmentNorthEastCalledWithIncorrectTenmentNo() {
		//Given
		givenTestInstance();
		//54H 732156.87610377 6086499.6270333
		BigDecimal easting = new BigDecimal("732156.87610377");
		BigDecimal northing = new BigDecimal("6086499.6270333");
		String tno = "EL863";
		//When
		boolean flag = testInstance.isWithinTenementMga54NorthEast(tno, easting, northing);
		//Then
		assertThat(flag, is(false));
	}
	
	@Test
	public void shouldReturnFalseWhenIsWithinMga54TenmentNorthEastCalledWithIncorrectCoordinate() {
		//Given
		givenTestInstance();
		BigDecimal easting = new BigDecimal("712156.87610377");
		BigDecimal northing = new BigDecimal("6086499.6270333");
		String tno = "EL863";
		//When
		boolean flag = testInstance.isWithinTenementMga54NorthEast(tno, easting, northing);
		//Then
		assertThat(flag, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenIsWithinTenmentMga55NorthEastCalledWithLakeEntranceCoorindate() {
		//Given
		givenTestInstance();
		//55H 587607.98439159 5810236.29552759
		BigDecimal easting = new BigDecimal("587607.98439159");
		BigDecimal northing = new BigDecimal("5810236.29552759");
		String tno = "EL4968";
		//When
		boolean flag = testInstance.isWithinTenementMga55NorthEast(tno, easting, northing);
		//Then
		assertThat(flag, is(true));
	}
	
	@Test
	public void shouldReturnTrueWhenIsWithinTenmentAgd54NorthEastCalledWithSwanHillCoorindate() {
		//Given
		givenTestInstance();
		//54H 732156.87610377 6086499.6270333
		BigDecimal easting = new BigDecimal("732156.87610377");
		BigDecimal northing = new BigDecimal("6086499.6270333");
		String tno = "EL803";
		//When
		boolean flag = testInstance.isWithinTenementAgd54NorthEast(tno, easting, northing);
		//Then
		assertThat(flag, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenIsWithinTenmentAgd54NorthEastCalledWithIncorrectTenment() {
		//Given
		givenTestInstance();
		//54H 732156.87610377 6086499.6270333
		BigDecimal easting = new BigDecimal("732156.87610377");
		BigDecimal northing = new BigDecimal("6086499.6270333");
		String tno = "EL813";
		//When
		boolean flag = testInstance.isWithinTenementAgd54NorthEast(tno, easting, northing);
		//Then
		assertThat(flag, is(false));
	}
	
	@Test
	public void shouldReturnFalseWhenIsWithinTenmentAgd54NorthEastCalledWithIncorrectCoordinate() {
		//Given
		givenTestInstance();
		BigDecimal easting = new BigDecimal("712156.87610377");
		BigDecimal northing = new BigDecimal("6086499.6270333");
		String tno = "EL803";
		//When
		boolean flag = testInstance.isWithinTenementAgd54NorthEast(tno, easting, northing);
		//Then
		assertThat(flag, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenIsWithinTenmentAgd55NorthEastCalledWithLakeEntranceCoorindate() {
		//Given
		givenTestInstance();
		//55H 587607.98439159 5810236.29552759
		BigDecimal easting = new BigDecimal("587607.98439159");
		BigDecimal northing = new BigDecimal("5810236.29552759");
		String tno = "EL4968";
		//When
		boolean flag = testInstance.isWithinTenementAgd55NorthEast(tno, easting, northing);
		//Then
		assertThat(flag, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenIsWithinTenmentAgd55NorthEastCalledWithIncorrectTenment() {
		//Given
		givenTestInstance();
		//55H 587607.98439159 5810236.29552759
		BigDecimal easting = new BigDecimal("587607.98439159");
		BigDecimal northing = new BigDecimal("5810236.29552759");
		String tno = "EL5968";
		//When
		boolean flag = testInstance.isWithinTenementAgd55NorthEast(tno, easting, northing);
		//Then
		assertThat(flag, is(false));
	}
	
	@Test
	public void shouldReturnFalseWhenIsWithinTenmentAgd55NorthEastCalledWithIncorrectCoordinate() {
		//Given
		givenTestInstance();
		BigDecimal easting = new BigDecimal("687607.98439159");
		BigDecimal northing = new BigDecimal("5810236.29552759");
		String tno = "EL5968";
		//When
		boolean flag = testInstance.isWithinTenementAgd55NorthEast(tno, easting, northing);
		//Then
		assertThat(flag, is(false));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		givenTestInstance();
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenPropertiesIsNull() {
		//Given
		MrtConfigProperties mrtConfigProperties = null;
		//When
		new VictoriaMapServicesSdoImpl(mrtConfigProperties);
		fail("Program reached unexpected point!");
	}
	
	private void givenTestInstance() {
		MrtConfigProperties mrtConfigProperties = mrtConfig.mrtConfigProperties();
		
		testInstance = new VictoriaMapServicesSdoImpl(mrtConfigProperties);
	}
}

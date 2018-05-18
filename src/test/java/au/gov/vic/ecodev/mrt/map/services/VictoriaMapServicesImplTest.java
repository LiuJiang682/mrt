package au.gov.vic.ecodev.mrt.map.services;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import au.gov.vic.ecodev.mrt.config.MrtConfigProperties;
import au.gov.vic.ecodev.mrt.map.services.helper.ExceptionEmailer;

@RunWith(PowerMockRunner.class)
@PrepareForTest(VictoriaMapServicesImpl.class)
public class VictoriaMapServicesImplTest {

	private VictoriaMapServicesImpl testInstance;
	@Test
	public void shouldReturnInstance() {
		//Given
		givenTestInstnace();
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
		assertThat(testInstance.isWithinMga54NorthEast(new BigDecimal("630694"), 
				new BigDecimal("5845228")), is(true));
		assertThat(testInstance.isWithinMga54NorthEast(new BigDecimal("392200"), 
				new BigDecimal("6589600")), is(false));
		//Lake Entrance is inside Victoria
		assertThat(testInstance.isWithinMga55NorthEast(new BigDecimal("586271.68119835"), 
				new BigDecimal("5806917.9683928")), is(true));
		//Sydney is not inside Victoria
		assertThat(testInstance.isWithinMga55NorthEast(new BigDecimal("334417.07"), 
				new BigDecimal("6251354.86")), is(false));
		//Swan Hill is inside Victoria
		assertThat(testInstance.isWithinMga54LatitudeLongitude(
				new BigDecimal("143.5525"), new BigDecimal("-35.3405225")),  is(true));
		//Sydney is not inside Victoria
		assertThat(testInstance.isWithinMga54LatitudeLongitude( 
				new BigDecimal("151.209900"), new BigDecimal("-33.865143")), is(false));
		//Lake Entrace is inside Victoria
		assertThat(testInstance.isWithinMga55LatitudeLongitude( 
				new BigDecimal("147.991605"), new BigDecimal("-37.879584")), is(true));
		//Sydney is not inside Victoria
		assertThat(testInstance.isWithinMga55LatitudeLongitude( 
				new BigDecimal("151.209900"), new BigDecimal("-33.865143")), is(false));
//		assertThat(testInstance.isWithingAgd54NorthEast(new BigDecimal("-37.879584"), 
//				new BigDecimal("147.991605")), is(true));
//		assertThat(testInstance.isWithingAgd55NorthEast(new BigDecimal("-37.879584"), 
//				new BigDecimal("147.991605")), is(true));
	}
	
	@Test
	public void shouldSendEmailAndShutDownWhenExceptionRaised() throws Exception {
		//Given
		MrtConfigProperties mrtConfigProperties = new MrtConfigProperties();
		VictoriaMapServicesImpl testInstance = PowerMockito.mock(VictoriaMapServicesImpl.class);
		Whitebox.setInternalState(testInstance, "maps", new HashMap<String, Geometry>());
		Whitebox.setInternalState(testInstance, "mrtConfigProperties", mrtConfigProperties);
		PowerMockito.doCallRealMethod().when(testInstance).init();
		PowerMockito.doCallRealMethod().when(testInstance).loadMap(Matchers.anyInt(), 
				Matchers.anyString());
		PowerMockito.doCallRealMethod().when(testInstance, "readFile", Matchers.anyString(),
				Matchers.any(GeometryFactory.class));
		PowerMockito.doNothing().when(testInstance, "shutDown");
		ExceptionEmailer mockExceptionEmailer = Mockito.mock(ExceptionEmailer.class);
		PowerMockito.whenNew(ExceptionEmailer.class).withArguments(mrtConfigProperties)
			.thenReturn(mockExceptionEmailer);
		//When
		testInstance.init();
		//Then
		PowerMockito.verifyPrivate(testInstance, times(6)).invoke("shutDown");
		PowerMockito.verifyNew(ExceptionEmailer.class, times(6));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenMrtConfigPropertiesIsNull() {
		//Given
		MrtConfigProperties mockMrtConfigProperties = null;
		//When
		new VictoriaMapServicesImpl(mockMrtConfigProperties);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void shouldRaiseExceptionWhenIsWithTenmentMga54LatitudeLongitudeCalled() {
		//Given
		givenTestInstnace();
		String tenmentNo = null;
		BigDecimal latitude = null;
		BigDecimal longitude = null;
		//When
		testInstance.isWithinTenementMga54LatitudeLongitude(tenmentNo, latitude, longitude);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void shouldRaiseExceptionWhenIsWithTenmentMga55LatitudeLongitudeCalled() {
		//Given
		givenTestInstnace();
		String tenmentNo = null;
		BigDecimal latitude = null;
		BigDecimal longitude = null;
		//When
		testInstance.isWithinTenementMga55LatitudeLongitude(tenmentNo, latitude, longitude);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void shouldRaiseExceptionWhenIsWithinTenmentMga54NorthEastCalled() {
		//Given
		givenTestInstnace();
		String tenmentNo = null;
		BigDecimal latitude = null;
		BigDecimal longitude = null;
		//When
		testInstance.isWithinTenementMga54NorthEast(tenmentNo, latitude, longitude);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void shouldRaiseExceptionWhenIsWithinTenmentMga55NorthEastCalled() {
		//Given
		givenTestInstnace();
		String tenmentNo = null;
		BigDecimal latitude = null;
		BigDecimal longitude = null;
		//When
		testInstance.isWithinTenementMga55NorthEast(tenmentNo, latitude, longitude);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void shouldRaiseExceptionWhenIsWithinTenmentAgd54NorthEastCalled() {
		//Given
		givenTestInstnace();
		String tenmentNo = null;
		BigDecimal latitude = null;
		BigDecimal longitude = null;
		//When
		testInstance.isWithinTenementAgd54NorthEast(tenmentNo, latitude, longitude);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void shouldRaiseExceptionWhenIsWithinTenmentAgd55NorthEastCalled() {
		//Given
		givenTestInstnace();
		String tenmentNo = null;
		BigDecimal latitude = null;
		BigDecimal longitude = null;
		//When
		testInstance.isWithinTenementAgd55NorthEast(tenmentNo, latitude, longitude);
		fail("Program reached unexpected point!");
	}
	
	private void givenTestInstnace() {
		MrtConfigProperties mockMrtConfigProperties = Mockito.mock(MrtConfigProperties.class);
		when(mockMrtConfigProperties.getSridGda94()).thenReturn(4283);
		when(mockMrtConfigProperties.getMga54NeFileName()).thenReturn("map/victoria_mga54_side.shp");
		when(mockMrtConfigProperties.getMga55NeFileName()).thenReturn("map/victoria_mga55_side.shp");
		when(mockMrtConfigProperties.getMga54LatFileName()).thenReturn("map/victoria_ggda94_54_side.shp");
		when(mockMrtConfigProperties.getMga55LatFileName()).thenReturn("map/victoria_ggda94_55_side.shp");
		when(mockMrtConfigProperties.getSridAgd84()).thenReturn(4203);
		when(mockMrtConfigProperties.getAgd8454NeFileName()).thenReturn("map/victoria_agd66_54_side.shp");
		when(mockMrtConfigProperties.getAgd8455NeFileName()).thenReturn("map/victoria_agd66_55_side.shp");
		//When
		testInstance = new VictoriaMapServicesImpl(mockMrtConfigProperties);
	}
}

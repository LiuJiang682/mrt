package au.gov.vic.ecodev.mrt.map.services;

import java.math.BigDecimal;

public interface VictoriaMapServices extends MapServices {

	boolean isWithinMga54NorthEast(final BigDecimal easting, final BigDecimal northing);
	boolean isWithinMga55NorthEast(final BigDecimal easting, final BigDecimal northing);
	
	boolean isWithinMga54LatitudeLongitude(final BigDecimal latitude, final BigDecimal longitude);
	boolean isWithinMga55LatitudeLongitude(final BigDecimal latitude, final BigDecimal longitude);
	
	boolean isWithinAgd54NorthEast(final BigDecimal easting, final BigDecimal northing);
	boolean isWithinAgd55NorthEast(final BigDecimal easting, final BigDecimal northing);
	
	boolean isWithinTenementMga54NorthEast(final String tenmentNo,
			final BigDecimal easting, final BigDecimal northing);
	boolean isWithinTenementMga55NorthEast(final String tenmentNo,
			final BigDecimal easting, final BigDecimal northing);
	boolean isWithinTenementMga54LatitudeLongitude(final String tenmentNo, 
			final BigDecimal latitude, final BigDecimal longitude);
	boolean isWithinTenementMga55LatitudeLongitude(final String tenmentNo, 
			final BigDecimal latitude, final BigDecimal longitude);
	boolean isWithinTenementAgd54NorthEast(final String tenmentNo,
			final BigDecimal easting, final BigDecimal northing);
	boolean isWithinTenementAgd55NorthEast(final String tenmentNo, 
			final BigDecimal easting, final BigDecimal northing);
}

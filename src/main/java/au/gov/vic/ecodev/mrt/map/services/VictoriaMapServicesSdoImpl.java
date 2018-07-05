package au.gov.vic.ecodev.mrt.map.services;

import java.math.BigDecimal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import au.gov.vic.ecodev.mrt.config.MrtConfigProperties;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;

public class VictoriaMapServicesSdoImpl implements VictoriaMapServices {

	private static final String GDA_94_TENMENT_LAT_LONG_SQL = "SELECT COUNT(*) FROM MINTEN m WHERE m.TNO = ? AND MDSYS.SDO_CONTAINS(m.SHAPE, MDSYS.SDO_GEOMETRY(2001, 4283, MDSYS.SDO_POINT_TYPE(?, ?, NULL), NULL, NULL)) = 'TRUE'";
	private static final String MGA_54_NE_SQL = "SELECT COUNT(*) FROM FR_FRAMEWORK_AREA_POLYGON f WHERE f.STATE = 'VIC' AND MDSYS.SDO_CONTAINS(f.SHAPE, MDSYS.SDO_GEOMETRY(2001, 28354, MDSYS.SDO_POINT_TYPE(?, ?, NULL), NULL, NULL)) = 'TRUE' ";
	private static final String MGA_55_NE_SQL = "SELECT COUNT(*) FROM FR_FRAMEWORK_AREA_POLYGON f WHERE f.STATE = 'VIC' AND MDSYS.SDO_CONTAINS(f.SHAPE, MDSYS.SDO_GEOMETRY(2001, 28355, MDSYS.SDO_POINT_TYPE(?, ?, NULL), NULL, NULL)) = 'TRUE' ";
	private static final String GDA_94_LAT_LONG_SQL = "SELECT COUNT(*) FROM FR_FRAMEWORK_AREA_POLYGON f WHERE MDSYS.SDO_CONTAINS(f.SHAPE, MDSYS.SDO_GEOMETRY(2001, 4283, MDSYS.SDO_POINT_TYPE(?, ?, NULL), NULL, NULL)) = 'TRUE' AND f.STATE = 'VIC'";
	private static final String AGD_54_NE_SQL = "SELECT COUNT(*) FROM FR_FRAMEWORK_AREA_POLYGON f WHERE MDSYS.SDO_CONTAINS(f.SHAPE, MDSYS.SDO_GEOMETRY(2001, 20354, MDSYS.SDO_POINT_TYPE(?, ?, NULL), NULL, NULL)) = 'TRUE' AND f.STATE = 'VIC'";
	private static final String AGD_55_NE_SQL = "SELECT COUNT(*) FROM FR_FRAMEWORK_AREA_POLYGON f WHERE MDSYS.SDO_CONTAINS(f.SHAPE, MDSYS.SDO_GEOMETRY(2001, 20355, MDSYS.SDO_POINT_TYPE(?, ?, NULL), NULL, NULL)) = 'TRUE' AND f.STATE = 'VIC'";
	private static final String MDA_54_TENMENT_NE_SQL = "SELECT COUNT(*) FROM MINTEN m WHERE m.TNO = ? AND MDSYS.SDO_CONTAINS(m.SHAPE, MDSYS.SDO_GEOMETRY(2001, 28354, MDSYS.SDO_POINT_TYPE(?, ?, NULL), NULL, NULL)) = 'TRUE'";
	private static final String MDA_55_TENMENT_NE_SQL = "SELECT COUNT(*) FROM MINTEN m WHERE m.TNO = ? AND MDSYS.SDO_CONTAINS(m.SHAPE, MDSYS.SDO_GEOMETRY(2001, 28355, MDSYS.SDO_POINT_TYPE(?, ?, NULL), NULL, NULL)) = 'TRUE'";
	private static final String AGD_54_TENMENT_NE_SQL = "SELECT COUNT(*) FROM MINTEN m WHERE m.TNO = ? AND MDSYS.SDO_CONTAINS(m.SHAPE, MDSYS.SDO_GEOMETRY(2001, 20354, MDSYS.SDO_POINT_TYPE(?, ?, NULL), NULL, NULL)) = 'TRUE'";
	private static final String AGD_55_TENMENT_NE_SQL = "SELECT COUNT(*) FROM MINTEN m WHERE m.TNO = ? AND MDSYS.SDO_CONTAINS(m.SHAPE, MDSYS.SDO_GEOMETRY(2001, 20355, MDSYS.SDO_POINT_TYPE(?, ?, NULL), NULL, NULL)) = 'TRUE'";
	
	private final JdbcTemplate jdbcTemplate;
	private final MrtConfigProperties mrtConfigProperties;
	
	public VictoriaMapServicesSdoImpl(final MrtConfigProperties mrtConfigProperties) {
		if (null == mrtConfigProperties) {
			throw new IllegalArgumentException("VictoriaMapServicesSdoImpl:mrtConfigProperties parameter cannot be null!");
		}
		this.mrtConfigProperties = mrtConfigProperties;
		this.jdbcTemplate = getJdbcTemplate();
	}
	
	private JdbcTemplate getJdbcTemplate() {
		DriverManagerDataSource datasource = new DriverManagerDataSource();
		datasource.setDriverClassName(mrtConfigProperties.getSdoDsDriverClassName());
		datasource.setUrl(mrtConfigProperties.getSdoDsUrl());
		datasource.setUsername(mrtConfigProperties.getSdoDsUserName());
		datasource.setPassword(mrtConfigProperties.getSdoDsPwd());
		return new JdbcTemplate(datasource);
	}

	@Override
	public boolean isWithinMga54NorthEast(BigDecimal easting, BigDecimal northing) {
		int count = jdbcTemplate.queryForObject(MGA_54_NE_SQL, Integer.class, 
				new Object[] {easting.doubleValue(), northing.doubleValue()});
		return Numeral.ZERO < count;
	}

	@Override
	public boolean isWithinMga55NorthEast(BigDecimal easting, BigDecimal northing) {
		int count = jdbcTemplate.queryForObject(MGA_55_NE_SQL, Integer.class, 
				new Object[] {easting.doubleValue(), northing.doubleValue()});
		return Numeral.ZERO < count;
	}

	@Override
	public boolean isWithinMga54LatitudeLongitude(BigDecimal latitude, BigDecimal longitude) {
		int count = jdbcTemplate.queryForObject(GDA_94_LAT_LONG_SQL, Integer.class, 
				new Object[] {longitude.doubleValue(), latitude.doubleValue()});
		return Numeral.ZERO < count;
	}

	@Override
	public boolean isWithinMga55LatitudeLongitude(BigDecimal latitude, BigDecimal longitude) {
		int count = jdbcTemplate.queryForObject(GDA_94_LAT_LONG_SQL, Integer.class, 
				new Object[] {longitude.doubleValue(), latitude.doubleValue()});
		return Numeral.ZERO < count;
	}

	@Override
	public boolean isWithinAgd54NorthEast(BigDecimal easting, BigDecimal northing) {
		int count = jdbcTemplate.queryForObject(AGD_54_NE_SQL, Integer.class, 
				new Object[] {easting.doubleValue(), northing.doubleValue()});
		return Numeral.ZERO < count;
	}

	@Override
	public boolean isWithinAgd55NorthEast(BigDecimal easting, BigDecimal northing) {
		int count = jdbcTemplate.queryForObject(AGD_55_NE_SQL, Integer.class, 
				new Object[] {easting.doubleValue(), northing.doubleValue()});
		return Numeral.ZERO < count;
	}

	@Override
	public boolean isWithinTenementMga54LatitudeLongitude(String tenmentNo, BigDecimal latitude, BigDecimal longitude) {
		int count = jdbcTemplate.queryForObject(GDA_94_TENMENT_LAT_LONG_SQL, Integer.class, 
				new Object[] {tenmentNo, longitude.doubleValue(), latitude.doubleValue()});
		return Numeral.ZERO < count;
	}

	@Override
	public boolean isWithinTenementMga55LatitudeLongitude(String tenmentNo, BigDecimal latitude, BigDecimal longitude) {
		int count = jdbcTemplate.queryForObject(GDA_94_TENMENT_LAT_LONG_SQL, Integer.class, 
				new Object[] {tenmentNo, longitude.doubleValue(), latitude.doubleValue()});
		return Numeral.ZERO < count;
	}

	@Override
	public boolean isWithinTenementMga54NorthEast(String tenmentNo, BigDecimal easting, 
			BigDecimal northing) {
		int count = jdbcTemplate.queryForObject(MDA_54_TENMENT_NE_SQL, Integer.class, 
				new Object[] {tenmentNo, easting.doubleValue(), northing.doubleValue()});
		return Numeral.ZERO < count;
	}

	@Override
	public boolean isWithinTenementMga55NorthEast(String tenmentNo, BigDecimal easting, 
			BigDecimal northing) {
		int count = jdbcTemplate.queryForObject(MDA_55_TENMENT_NE_SQL, Integer.class, 
				new Object[] {tenmentNo, easting.doubleValue(), northing.doubleValue()});
		return Numeral.ZERO < count;
	}

	@Override
	public boolean isWithinTenementAgd54NorthEast(String tenmentNo, BigDecimal easting, BigDecimal northing) {
		int count = jdbcTemplate.queryForObject(AGD_54_TENMENT_NE_SQL, Integer.class, 
				new Object[] {tenmentNo, easting.doubleValue(), northing.doubleValue()});
		return Numeral.ZERO < count;
	}

	@Override
	public boolean isWithinTenementAgd55NorthEast(String tenmentNo, BigDecimal easting, BigDecimal northing) {
		int count = jdbcTemplate.queryForObject(AGD_55_TENMENT_NE_SQL, Integer.class, 
				new Object[] {tenmentNo, easting.doubleValue(), northing.doubleValue()});
		return Numeral.ZERO < count;
	}

}

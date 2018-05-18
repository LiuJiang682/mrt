package au.gov.vic.ecodev.mrt.template.fields;

public enum SL4ColumnHeaders {

	HOLE_ID("Hole_id"),
	EASTING_MGA("Easting_MGA"),
	EASTING_AMG("Easting_AMG"),
	NORTHING_MGA("Northing_MGA"),
	NORTHING_AMG("Northing_AMG"),
	ELEVATION("Elevation"),
	TOTAL_HOLE_DEPTH("Total Hole Depth"),
	DRILL_CODE("Drill Code"),
	DIP("Dip"),
	AZIMUTH_MAG("Azimuth_MAG"), 
	LATITUDE("Latitude"), 
	LONGITUDE("Longitude");
	
	private final String code;
	
	SL4ColumnHeaders(final String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
}

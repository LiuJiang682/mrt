package au.gov.vic.ecodev.mrt.template.fields;

public enum Ds4ColumnHeaders {

	HOLE_ID("Hole_id"),
	SURVEYED_DEPTH("Surveyed_Depth"),
	AZIMUTH_MAG("Azimuth_MAG"), 
	DIP("Dip");
	
	private final String code;
	
	Ds4ColumnHeaders(final String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
}

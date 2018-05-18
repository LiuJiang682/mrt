package au.gov.vic.ecodev.mrt.template.fields;

public enum Sg4ColumnHeaders {
	SAMPLE_ID("Sample ID"), 
	EASTING_MGA("Easting_MGA"), 
	NORTHING_MGA("Northing_MGA"), 
	SAMPLE_TYPE("Sample_type"), 
	EASTING_AMG("Easting_AMG"),
	NORTHING_AMG("Northing_AMG");
	
	private String code;
	
	Sg4ColumnHeaders(final String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}

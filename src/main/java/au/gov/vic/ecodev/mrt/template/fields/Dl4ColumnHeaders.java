package au.gov.vic.ecodev.mrt.template.fields;

public enum Dl4ColumnHeaders {

	HOLE_ID("Hole_id"),
	DEPTH_FROM("Depth_from");
	
	private String code;
	
	Dl4ColumnHeaders(final String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
}

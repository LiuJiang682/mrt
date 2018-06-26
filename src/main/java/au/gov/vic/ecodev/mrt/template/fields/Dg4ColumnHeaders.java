package au.gov.vic.ecodev.mrt.template.fields;

public enum Dg4ColumnHeaders {

	HOLE_ID("Hole_id"),
	FROM("Depth From"), 
	TO("Depth To"), 
	SAMPLE_ID("Sample ID");
	
	private String code;
	
	Dg4ColumnHeaders(final String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
}

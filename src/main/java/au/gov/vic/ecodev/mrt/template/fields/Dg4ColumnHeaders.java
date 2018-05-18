package au.gov.vic.ecodev.mrt.template.fields;

public enum Dg4ColumnHeaders {

	HOLE_ID("Hole_id"),
	FROM("From"), 
	TO("To"), 
	SAMPLE_ID("Sample_id"), 
	DRILL_CODE("Drill_code");
	
	private String code;
	
	Dg4ColumnHeaders(final String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
}

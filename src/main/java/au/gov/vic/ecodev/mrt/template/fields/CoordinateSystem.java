package au.gov.vic.ecodev.mrt.template.fields;

public enum CoordinateSystem {

	PROJECTED("Projected"),
	LATITUDE_LONGITUDE("Latitude/Longitude");
	
	private final String code;
	
	CoordinateSystem(final String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}

	public static CoordinateSystem fromString(String string) {
		for (CoordinateSystem cs : CoordinateSystem.values()) {
			if (cs.getCode().equalsIgnoreCase(string)) {
				return cs;
			}
		}
		
		throw new IllegalArgumentException("Unknown string: " + string);
	}
	
	
}

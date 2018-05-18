package au.gov.vic.ecodev.mrt.template.fields;

public enum Projection {

	NON_PROJECTED("Non-projected"),
	UTM("Universal Transverse Mercator (UTM)"),
	LAMBERT_CONFORMABLE("Lambert Conformable"),
	ALBERS_EQUAL_AREA("Albers Equal Area"),
	MGA("Map Grid of Australia (MGA)"),
	AGD("Australian Map Grid (AGD)");
	
	private final String code;
	
	Projection(final String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
	public static Projection fromString(final String code) {
		for (Projection projection : Projection.values()) {
			if (projection.getCode().equalsIgnoreCase(code)) {
				return projection;
			}
		}
		
		throw new IllegalArgumentException("Unknown String: " + code);
	}
}

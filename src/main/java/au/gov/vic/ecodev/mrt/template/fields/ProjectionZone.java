package au.gov.vic.ecodev.mrt.template.fields;

public enum ProjectionZone {
	
	NON_PROJECTED("Non-projected"),
	VIC_54("54"),
	VIC_55("55");
	
	private final String projectionZone;
	
	ProjectionZone(final String projectionZone) {
		this.projectionZone = projectionZone;
	}
	
	public String getZone() {
		return projectionZone;
	}

	public static ProjectionZone fromString(String string) {
		for (ProjectionZone projectionZone : ProjectionZone.values()) {
			if (projectionZone.getZone().equals(string)) {
				return projectionZone;
			}
		}
		
		throw new IllegalArgumentException("Unknown string: " + string);
	}
}

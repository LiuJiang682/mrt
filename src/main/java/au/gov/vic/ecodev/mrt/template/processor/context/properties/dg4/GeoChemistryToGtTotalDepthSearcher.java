package au.gov.vic.ecodev.mrt.template.processor.context.properties.dg4;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import au.gov.vic.ecodev.mrt.template.context.properties.StringListTemplateProperties;
import au.gov.vic.ecodev.mrt.template.properties.TemplateProperties;
import au.gov.vic.ecodev.mrt.template.searcher.TemplateSearcher;

public class GeoChemistryToGtTotalDepthSearcher implements TemplateSearcher {

	private static final String SQL = "SELECT gc.HOLE_ID FROM DH_GEOCHEMISTRY gc, DH_BOREHOLE bh WHERE gc.HOLE_ID = bh.HOLE_ID AND gc.LOADER_ID = bh.LOADER_ID and gc.SAMPLE_TO > bh.depth and gc.LOADER_ID = ?";
	
	private JdbcTemplate jdbcTemplate;
	private long key;
	
	@Override
	public TemplateProperties search() {
		List<String> holeIds = jdbcTemplate.queryForList(SQL, String.class, new Object[] {key});
		return new StringListTemplateProperties(holeIds);
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setKey(long sessionId) {
		this.key = sessionId;
	}
}

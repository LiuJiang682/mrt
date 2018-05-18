package au.gov.vic.ecodev.mrt.template.processor.context.properties.dl4;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import au.gov.vic.ecodev.mrt.template.context.properties.StringListTemplateProperties;
import au.gov.vic.ecodev.mrt.template.properties.TemplateProperties;
import au.gov.vic.ecodev.mrt.template.searcher.TemplateSearcher;

public class LithologyHoleIdNotInBoreHoleSearcher implements TemplateSearcher {

	private static final String SQL = "SELECT DISTINCT HOLE_ID FROM DH_LITHOLOGY WHERE LOADER_ID = ? AND HOLE_ID NOT IN (SELECT HOLE_ID FROM DH_BOREHOLE WHERE LOADER_ID = ? AND HOLE_ID IN (SELECT DISTINCT HOLE_ID FROM DH_LITHOLOGY WHERE LOADER_ID = ?))";
	
	private JdbcTemplate jdbcTemplate;
	private long key;
	
	@Override
	public TemplateProperties search() {
		List<String> results = jdbcTemplate.queryForList(SQL, String.class, 
				new Object[] {key, key, key});
		return new StringListTemplateProperties(results);
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setKey(long sessionId) {
		this.key = sessionId;
	}
}

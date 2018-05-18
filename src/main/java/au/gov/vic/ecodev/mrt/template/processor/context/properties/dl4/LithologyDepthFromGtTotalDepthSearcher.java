package au.gov.vic.ecodev.mrt.template.processor.context.properties.dl4;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import au.gov.vic.ecodev.mrt.template.context.properties.StringListTemplateProperties;
import au.gov.vic.ecodev.mrt.template.properties.TemplateProperties;
import au.gov.vic.ecodev.mrt.template.searcher.TemplateSearcher;

public class LithologyDepthFromGtTotalDepthSearcher implements TemplateSearcher {

	private String SQL = "SELECT l.HOLE_ID FROM DH_LITHOLOGY l, DH_BOREHOLE bh WHERE l.HOLE_ID = bh.HOLE_ID AND l.LOADER_ID = bh.LOADER_ID AND l.DEPTH_FROM > bh.DEPTH AND l.LOADER_ID = ?";

	private JdbcTemplate jdbcTemplate;
	private long key;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setKey(long key) {
		this.key = key;
	}

	@Override
	public TemplateProperties search() {
		List<String> holeIds = jdbcTemplate.queryForList(SQL, String.class, new Object[] {key});
		return new StringListTemplateProperties(holeIds);
	}
}

package au.gov.vic.ecodev.mrt.template.processor.context.properties.ds4;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import au.gov.vic.ecodev.mrt.template.context.properties.StringListTemplateProperties;
import au.gov.vic.ecodev.mrt.template.properties.TemplateProperties;
import au.gov.vic.ecodev.mrt.template.searcher.TemplateSearcher;

public class DownHoleSurveyedDepthGtTotalDepthSearcher implements TemplateSearcher {

	private static final String SQL = "SELECT DH.HOLE_ID FROM DH_DOWNHOLE DH, DH_BOREHOLE BH WHERE DH.HOLE_ID = BH.HOLE_ID AND DH.LOADER_ID = BH.LOADER_ID AND DH.SURVEYED_DEPTH > BH.DEPTH AND DH.LOADER_ID = ?";
	
	private JdbcTemplate jdbcTemplate;
	private Long key;
	
	@Override
	public TemplateProperties search() {
		List<String> holeIds = jdbcTemplate.queryForList(SQL, String.class, new Object[] {key});
		return new StringListTemplateProperties(holeIds);
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setKey(Long key) {
		this.key = key;
	}
	
	

}

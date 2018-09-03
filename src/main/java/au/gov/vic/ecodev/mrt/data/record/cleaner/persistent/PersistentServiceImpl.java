package au.gov.vic.ecodev.mrt.data.record.cleaner.persistent;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import au.gov.vic.ecodev.mrt.config.MrtConfigProperties;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.utils.sql.helper.DbTableNameSqlInjectionFilter;

@Service("persistentService")
public class PersistentServiceImpl implements PersistentService {

	private final JdbcTemplate jdbcTemplate;
	private final MrtConfigProperties mrtConfigProperties;
	
	@Autowired
	public PersistentServiceImpl(final JdbcTemplate jdbcTemplate, 
			final MrtConfigProperties mrtConfigProperties) {
		if (null == jdbcTemplate) {
			throw new IllegalArgumentException("PersistentServiceImpl:jdbcTemplate parameter cannot be null!");
		}
		this.jdbcTemplate = jdbcTemplate;
		if (null == mrtConfigProperties) {
			throw new IllegalArgumentException("PersistentServiceImpl:mrtConfigProperties parameter cannot be null!");
		}
		this.mrtConfigProperties = mrtConfigProperties;
	}
	
	@Override
	public List<Map<String, Object>> getSessions() {
		List<Map<String, Object>> results = null;
		String selectSessionSql = mrtConfigProperties.getSelectCleanSessionSql();
		if (StringUtils.isEmpty(selectSessionSql)) {
			throw new RuntimeException("No select session sql! Please check your application.properties for select.clean.session.sql entry!");
		} else {
			if (DbTableNameSqlInjectionFilter.foundRestrictedSqlInjectedSqlTag(selectSessionSql)) {
				throw new RuntimeException("You have SQL injected SQL! It contains: " + selectSessionSql);
			} else {
				results = jdbcTemplate.queryForList(selectSessionSql, 
						new Object[] {Numeral.ONE});
				
			}
		}
		return results;
	}

}

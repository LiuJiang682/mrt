package au.gov.vic.ecodev.mrt.data.record.cleaner.persistent;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import au.gov.vic.ecodev.mrt.config.MrtConfigProperties;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.dao.TemplateDisplayPropertiesDao;
import au.gov.vic.ecodev.utils.sql.helper.DbTableNameSqlInjectionFilter;

@Service("persistentService")
public class PersistentServiceImpl implements PersistentService {

	private static final String SQL_DELETE_SUFFIX = " WHERE LOADER_ID = ?";
	private static final String SQL_DELETE_PREFIX = "DELETE FROM ";
	
	private final JdbcTemplate jdbcTemplate;
	private final MrtConfigProperties mrtConfigProperties;
	private final TemplateDisplayPropertiesDao templateDisplayPropertiesDao;
	
	@Autowired
	public PersistentServiceImpl(final JdbcTemplate jdbcTemplate, 
			final MrtConfigProperties mrtConfigProperties,
			final TemplateDisplayPropertiesDao templateDisplayPropertiesDao) {
		if (null == jdbcTemplate) {
			throw new IllegalArgumentException("PersistentServiceImpl:jdbcTemplate parameter cannot be null!");
		}
		this.jdbcTemplate = jdbcTemplate;
		if (null == mrtConfigProperties) {
			throw new IllegalArgumentException("PersistentServiceImpl:mrtConfigProperties parameter cannot be null!");
		}
		this.mrtConfigProperties = mrtConfigProperties;
		if (null == templateDisplayPropertiesDao) {
			throw new IllegalArgumentException("PersistentServiceImpl:templateDisplayPropertiesDao parameter cannot be null!");
		}
		this.templateDisplayPropertiesDao = templateDisplayPropertiesDao;
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

	@Override
	public String getDisplayProperties(String template) {
		return templateDisplayPropertiesDao.getDisplayProperties(template);
	}

	@Override
	public void deleteByTableNameAndSessionId(String table, long sessionId) {
		String sql = new StringBuilder(SQL_DELETE_PREFIX)
				.append(table)
				.append(SQL_DELETE_SUFFIX)
				.toString();
		jdbcTemplate.update(sql, new Object[] {sessionId});
	}

}

package au.gov.vic.ecodev.mrt.data.record.cleaner;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;

import au.gov.vic.ecodev.mrt.data.record.cleaner.helper.TableNameHelper;
import au.gov.vic.ecodev.mrt.data.record.cleaner.persistent.PersistentService;
import au.gov.vic.ecodev.mrt.data.record.cleaner.persistent.model.Session;
import au.gov.vic.ecodev.utils.sql.helper.DbTableNameSqlInjectionFilter;

public class DbSessionCleaner {

	private final Session session;
	private final PersistentService persistentService;
	private final Map<String, List<String>> templateClassListMap;
	
	public DbSessionCleaner(final Session session, final PersistentService persistenService, 
			final Map<String, List<String>> templateClassListMap) {
		if (null == session) {
			throw new IllegalArgumentException("DbSessionCleaner:session parameter cannot be null!");
		}
		this.session = session;
		if (null == persistenService) {
			throw new IllegalArgumentException("DbSessionCleaner:persistenService parameter cannot be null!");
		}
		this.persistentService = persistenService;
		if (null == templateClassListMap) {
			throw new IllegalArgumentException("DbSessionCleaner:templateClassListMap parameter cannot be null!");
		}
		this.templateClassListMap = templateClassListMap;
	}

	public void clean() {
		session.getTemplateList().stream()
			.forEach(template -> {
				List<String> templatTableList = templateClassListMap.get(template);
				if (CollectionUtils.isEmpty(templatTableList)) {
					String displayProperties = persistentService
						.getDisplayProperties(template);
					templatTableList = new TableNameHelper(displayProperties)
							.extractTableName();
					templateClassListMap.put(template, templatTableList);
				}
				templatTableList.stream()
					.forEach(table -> {
						if (DbTableNameSqlInjectionFilter.foundSqlInjectedTableName(table)) {
							throw new RuntimeException("SQL injection found at table name: " + table);
						} else {
							persistentService.deleteByTableNameAndSessionId(table, session.getSessionId());
						}
					});
			});
		
	}

}

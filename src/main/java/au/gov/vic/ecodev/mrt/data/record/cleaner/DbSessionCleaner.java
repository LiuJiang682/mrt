package au.gov.vic.ecodev.mrt.data.record.cleaner;

import java.util.List;

import au.gov.vic.ecodev.mrt.data.record.cleaner.helper.TableNameHelper;
import au.gov.vic.ecodev.mrt.data.record.cleaner.persistent.PersistentService;
import au.gov.vic.ecodev.mrt.data.record.cleaner.persistent.model.Session;
import au.gov.vic.ecodev.utils.sql.helper.DbTableNameSqlInjectionFilter;

public class DbSessionCleaner {

	private final Session session;
	private final PersistentService persistentService;
	
	public DbSessionCleaner(Session session, PersistentService persistenService) {
		if (null == session) {
			throw new IllegalArgumentException("DbSessionCleaner:session parameter cannot be null!");
		}
		this.session = session;
		if (null == persistenService) {
			throw new IllegalArgumentException("DbSessionCleaner:persistenService parameter cannot be null!");
		}
		this.persistentService = persistenService;
	}

	public void clean() {
		session.getTemplateList().stream()
			.forEach(template -> {
				String displayProperties = persistentService
						.getDisplayProperties(template);
				List<String> templatTableList = new TableNameHelper(displayProperties)
							.extractTableName();
				
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

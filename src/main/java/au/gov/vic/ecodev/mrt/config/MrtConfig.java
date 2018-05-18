package au.gov.vic.ecodev.mrt.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import au.gov.vic.ecodev.mrt.dao.SessionHeaderDao;
import au.gov.vic.ecodev.mrt.dao.StatusLogDao;
import au.gov.vic.ecodev.mrt.dao.TemplateConfigDao;
import au.gov.vic.ecodev.mrt.map.services.VictoriaMapServices;
import au.gov.vic.ecodev.mrt.map.services.VictoriaMapServicesSdoImpl;
import au.gov.vic.ecodev.mrt.template.loader.fsm.TemplateLoaderStateMachineContext;
import au.gov.vic.ecodev.mrt.template.processor.services.PersistentServices;

@Configuration
@EnableTransactionManagement
public class MrtConfig {

	private static final String DRIVER_CLASS_H2 = "org.h2.Driver";

	@Autowired
	private DataSource dataSource;

	@Autowired
	private TemplateConfigDao templateConfigDao;

	@Autowired
	private StatusLogDao statusLogDao;

	@Autowired
	private SessionHeaderDao sessionHeaderDao;
	
	@Autowired
	private PersistentServices persistentServices;

	@Bean
	public JdbcTemplate getJdbcTemplate() {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public MrtConfigProperties mrtConfigProperties() {
		return new MrtConfigProperties();
	}

	@Bean
	public TemplateLoaderStateMachineContext templateLoaderStateMachineContext() {
		return new TemplateLoaderStateMachineContext(mrtConfigProperties(), persistentServices, 
				getJdbcTemplate(), victoriaMapServices());
	}

	@Bean
	@Primary
	public DataSource dataSource() {
		MrtConfigProperties configProperties = mrtConfigProperties();
		DriverManagerDataSource datasource = new DriverManagerDataSource();
		datasource.setDriverClassName(configProperties.getDsDriverClassName());
		datasource.setUrl(configProperties.getDsUrl());
		datasource.setUsername(configProperties.getDsUserName());
		datasource.setPassword(configProperties.getDsPwd());
		if (DRIVER_CLASS_H2.equalsIgnoreCase(configProperties.getDsDriverClassName())) {
			DatabasePopulatorUtils.execute(createDatabasePopulator(), datasource);
		}
		return datasource;
	}

	private DatabasePopulator createDatabasePopulator() {
		Resource initSchema = new ClassPathResource("db/sql/create-db.sql");
		Resource initData = new ClassPathResource("db/sql/insert-data.sql");
		DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema, initData);
		return databasePopulator;
	}

	@Bean
	@Profile("test")
	public DataSource dataSource_test() {
		// no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2) // .H2 or .DERBY
				.addScript("db/sql/create-db.sql")
				.addScript("db/sql/insert-data.sql").build();
		return db;
	}

	@Bean
	public PersistentServices persistentServices() {
		return persistentServices;
	}

	@Bean
	public TemplateConfigDao templateConfigDao() {
		return templateConfigDao;
	}

	@Bean
	public StatusLogDao statusLogDao() {
		return statusLogDao;
	}
	
	@Bean
	public SessionHeaderDao sessionHeaderDao() {
		return sessionHeaderDao;
	}
	
	@Bean
	@Scope("singleton")
	public VictoriaMapServices victoriaMapServices() {
//		return new VictoriaMapServicesImpl(mrtConfigProperties());
		return new VictoriaMapServicesSdoImpl(mrtConfigProperties());
	}

}

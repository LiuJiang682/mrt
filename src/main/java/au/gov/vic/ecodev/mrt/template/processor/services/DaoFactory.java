package au.gov.vic.ecodev.mrt.template.processor.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.persistent.Dao;

public class DaoFactory {

	private final JdbcTemplate jdbcTemplate;
	private final List<Class<? extends Dao>> classList;

	public DaoFactory(final JdbcTemplate jdbcTemplate, final List<Class<? extends Dao>> classList) {
		if (null == jdbcTemplate) {
			throw new IllegalArgumentException("Parameter jdbcTemplate cannot be null!");
		}
		this.jdbcTemplate = jdbcTemplate;
		if (CollectionUtils.isEmpty(classList)) {
			throw new IllegalArgumentException("Parameter classList cannot be null!");
		}
		this.classList = classList;
	}

	public List<Dao> getDaos() throws TemplateProcessorException {
		List<Dao> daos = new ArrayList<>();
		try {
			for (Class<? extends Dao> cls : classList) {
				Dao dao = cls.newInstance();
				dao.setJdbcTemplate(jdbcTemplate);
				daos.add(dao);
			}
		} catch (Exception e) {
			throw new TemplateProcessorException(e.getMessage(), e);
		}
		return daos;
	}

}

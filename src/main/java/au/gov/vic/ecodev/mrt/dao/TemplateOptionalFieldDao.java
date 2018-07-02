package au.gov.vic.ecodev.mrt.dao;

import java.util.List;

import au.gov.vic.ecodev.mrt.template.processor.model.Entity;
import au.gov.vic.ecodev.mrt.template.processor.persistent.Dao;

public interface TemplateOptionalFieldDao extends Dao {

	boolean batchUpdate(final List<Entity> entities);
}

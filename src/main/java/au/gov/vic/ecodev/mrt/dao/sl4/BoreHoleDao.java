package au.gov.vic.ecodev.mrt.dao.sl4;

import au.gov.vic.ecodev.mrt.model.sl4.BoreHole;
import au.gov.vic.ecodev.mrt.template.processor.persistent.Dao;

public interface BoreHoleDao extends Dao {

	BoreHole getBySessionIdAndHoleId(final long sessionId, final String siteId);
}

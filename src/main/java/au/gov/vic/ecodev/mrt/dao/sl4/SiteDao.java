package au.gov.vic.ecodev.mrt.dao.sl4;

import au.gov.vic.ecodev.mrt.model.sl4.Site;
import au.gov.vic.ecodev.mrt.template.processor.persistent.Dao;

public interface SiteDao extends Dao {

	Site getSiteBySessionIdAndSiteId(final long sessionId, final String siteId);
}

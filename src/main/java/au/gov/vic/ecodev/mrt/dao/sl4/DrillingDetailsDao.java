package au.gov.vic.ecodev.mrt.dao.sl4;

import au.gov.vic.ecodev.mrt.model.sl4.DrillingDetails;
import au.gov.vic.ecodev.mrt.template.processor.persistent.Dao;

public interface DrillingDetailsDao extends Dao {

	DrillingDetails getByDrillingTypeAndCompany(final String drillingType, final String drillingCompany);

}

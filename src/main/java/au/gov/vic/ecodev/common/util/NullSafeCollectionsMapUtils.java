package au.gov.vic.ecodev.common.util;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class NullSafeCollectionsMapUtils {

	private static final Logger LOGGER = Logger.getLogger(NullSafeCollectionsMapUtils.class);
	
	private static final int ZERO = 0;
	
	private static final int NOT_FOUND = -1;
	
	public int parseInt(final Map<String, List<String>> templateParamMap, final String mapKey) {
		int lineNumber = NOT_FOUND;
		if (null != templateParamMap) {
			Object object = new NullSafeCollections(templateParamMap.get(mapKey)).get(ZERO);
			
			try {
				lineNumber = Integer.parseInt((String) object);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		
		return lineNumber;
	}
}

package au.gov.vic.ecodev.mrt.template.processor.file.validator.common.helper;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;

public class PartialFileNameFlagHelper {

	private final Map<String, List<String>> params;
	
	public PartialFileNameFlagHelper(Map<String, List<String>> params) {
		if (null == params) {
			throw new IllegalArgumentException("PartialFileNameFlagHelper -- params cannot be null!");
		}
		this.params = params;
	}

	public boolean getPartilaFileNameFlag() {
		boolean partialFileNameFlag = false;
		List<String> partialFileNameFlagList = params.get(Strings.PARTIAL_FILE_NAME_KEY);
		if (!CollectionUtils.isEmpty(partialFileNameFlagList)) {
			String partialFileNameFlagString = partialFileNameFlagList.get(Numeral.ZERO);
			if (StringUtils.isNoneBlank(partialFileNameFlagString)) {
				partialFileNameFlag = Boolean.parseBoolean(partialFileNameFlagString);
			}
		}
		return partialFileNameFlag;
	}

}

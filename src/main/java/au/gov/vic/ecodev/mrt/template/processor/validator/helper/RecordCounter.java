package au.gov.vic.ecodev.mrt.template.processor.validator.helper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;

public class RecordCounter {

	public String incrementAndGet(Map<String, List<String>> templateParamMap) {
		List<String> recordCount = templateParamMap.get(Strings.NUMBER_OF_DATA_RECORDS_ADDED);
		int count = Numeral.ONE;
		if (!CollectionUtils.isEmpty(recordCount)) {
			try {
				count = Integer.parseInt(recordCount.get(Numeral.ZERO));
				++count;
			} catch(NumberFormatException e) {
				//Ignore exception
			}
		}
		templateParamMap.put(Strings.NUMBER_OF_DATA_RECORDS_ADDED, Arrays.asList(String.valueOf(count)));
		return String.valueOf(count);
	}
}

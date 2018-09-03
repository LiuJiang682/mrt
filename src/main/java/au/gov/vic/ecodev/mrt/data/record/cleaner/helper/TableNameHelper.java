package au.gov.vic.ecodev.mrt.data.record.cleaner.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TableNameHelper {

	private static final Logger LOGGER = Logger.getLogger(TableNameHelper.class);
	
	private final String displayProperties;
	
	public TableNameHelper(String displayProperties) {
		if(StringUtils.isEmpty(displayProperties)) {
			throw new IllegalArgumentException("TableNameHelper:displayProperties parameter cannot be null or empty!");
		}
		this.displayProperties = displayProperties;
	}

	public List<String> extractTableName() {
		Set<String> tableNameSet = new HashSet<>();
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> response = new ObjectMapper().readValue(displayProperties, 
					HashMap.class);
			response.keySet().stream()
				.forEach(key -> {
					Object object = response.get(key);
					if (null != object) {
						if (object instanceof List) {
							@SuppressWarnings("unchecked")
							List<LinkedHashMap<String, String>> templateClassesList = 
								(List<LinkedHashMap<String, String>>) object;
							templateClassesList.stream()
								.forEach(templateClass -> {
									templateClass.entrySet().stream()
										.forEach(entry -> {
											String templateTable = entry.getKey();
											tableNameSet.add(templateTable);
										});
								});
						}
					}
				});
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return new ArrayList<>(tableNameSet);
	}

}

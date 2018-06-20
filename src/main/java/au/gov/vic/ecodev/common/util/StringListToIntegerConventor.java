package au.gov.vic.ecodev.common.util;

import java.util.List;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.api.constants.Constants.Numeral;

public class StringListToIntegerConventor {

	private final List<String> strings;
	
	public StringListToIntegerConventor(final List<String> strings) {
		if (CollectionUtils.isEmpty(strings)) {
			throw new IllegalArgumentException("Parameter strings cannot be empty!");
		}
		this.strings = strings;
	}

	public int parse() {
		return Integer.parseInt(strings.get(Numeral.ZERO));
	}
}

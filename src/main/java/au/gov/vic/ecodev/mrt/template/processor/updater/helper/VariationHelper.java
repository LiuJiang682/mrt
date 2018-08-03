package au.gov.vic.ecodev.mrt.template.processor.updater.helper;

import java.util.List;
import java.util.OptionalInt;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;

public class VariationHelper {

	private static final String REGEX_MULTI_DELIM_REGEX = "[ |_|-]{1}";
	private static final String REGEX_MULTI_DELIM = "[ |_|-]";
	private static final String REGEX_IGNORE_CASE = "(?i)";
	private static final String REGEX_MULTI_SPACE = " +";
	
	private final List<String> data;
	private final String code;
	
	public VariationHelper(List<String> data, String code) {
		if (CollectionUtils.isEmpty(data)) {
			throw new IllegalArgumentException("VariationHelper:data parameter cannot be null or empty!");
		}
		this.data = data;
		if (StringUtils.isBlank(code)) {
			throw new IllegalArgumentException("VariationHelper:code parameter cannot be null or empty!");
		}
		this.code = code;
	}

	public int findIndex() {
		final String patternString = code.trim().replaceAll(REGEX_MULTI_SPACE, Strings.SPACE);
		Pattern reversedPattern = getReversedPattern(patternString);
		final String patternStringRegex = patternString.replaceAll(REGEX_MULTI_DELIM, 
				REGEX_MULTI_DELIM_REGEX);
		Pattern pattern = Pattern.compile(REGEX_IGNORE_CASE + patternStringRegex);
		
		OptionalInt indexOpt = IntStream.range(Numeral.ZERO, data.size())
				.filter(i -> {
					String header = data.get(i);
					return pattern.matcher(header).matches() || reversedPattern.matcher(header).matches();
					})
				.findFirst();
		int index = Numeral.NOT_FOUND;
		if (indexOpt.isPresent()) {
			index = indexOpt.getAsInt();
		}
		return index;
	}

	private Pattern getReversedPattern(final String patternString) {
		String[] strings = patternString.split(REGEX_MULTI_DELIM);
		ArrayUtils.reverse(strings);
		String reversedString = String.join(REGEX_MULTI_DELIM_REGEX, strings);
		return Pattern.compile(REGEX_IGNORE_CASE + reversedString);
	}

}

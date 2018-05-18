package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.RegexPattern;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;

public class OptionalHeaderMandatoryFieldValidator {

	private static final Pattern DELIM_PATTERN = Pattern.compile("[ |_|-]");
	
	private final String[] strs;
	private final List<String> headers;
	private final List<String> mandatoryFieldList;
	private final String headerKey;
	
	public OptionalHeaderMandatoryFieldValidator(final String[] strs, final List<String> headers,
			final List<String> mandatoryFieldList, final String headerKey) {
		if (null == strs) {
			throw new IllegalArgumentException("OptionalHeaderMandatoryFieldValidator:strs parameter cannot be null!");
		}
		this.strs = strs;
		if (CollectionUtils.isEmpty(headers)) {
			throw new IllegalArgumentException("OptionalHeaderMandatoryFieldValidator:headers parameter cannot be null or empty!");
		}
		this.headers = headers;
		this.mandatoryFieldList = mandatoryFieldList;
		if (StringUtils.isEmpty(headerKey)) {
			throw new IllegalArgumentException("OptionalHeaderMandatoryFieldValidator:headerKey parameter cannot be null or empty!");
		}
		this.headerKey = headerKey;
	}
	
	public void validate(List<String> messages) {
		if (null != mandatoryFieldList) {
			for (int index = Numeral.ONE; index < strs.length; index++) {
				String fieldValue = strs[index];
				if (StringUtils.isEmpty(fieldValue)) {
					int position = index;
					--position;
					String rawheader = headers.get(position);
					String header = rawheader.trim();
					if (mandatoryFieldList.contains(header)) {
						//Have to do the exact matching first to prevent
						//breakdown the custom field.
						messages.add(constructMissingHeaderMessage(rawheader));
					} else {
						//Attempt to breakdown the header to find the element
						//in the periodic table.
						if (RegexPattern.PERIODIC_TABLE_ELEMENT_NAME_PATTERN.matcher(header)
								.find()) {
							String first = header.substring(Numeral.ZERO, Numeral.ONE);
							String second = header.substring(Numeral.ONE, Numeral.TWO);
							if ((!NumberUtils.isParsable(second)) 
									&& (!Strings.SPACE.equals(second))) {
								//The second char is NOT a number, append it
								//with the first one to make up the element symbol.
								first += second;
							}
							if (mandatoryFieldList.contains(first)) {
								messages.add(constructMissingHeaderMessage(rawheader));
							}
						}
					}
				}
			}
		}
	}

	protected final int indexOf(final String string) {
		Matcher matcher = DELIM_PATTERN.matcher(string);
		return matcher.find() ? matcher.start() : Numeral.NOT_FOUND;
	}
	
	private String constructMissingHeaderMessage(String header) {
		String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
				.append(header)
				.append(" requires value in ")
				.append(headerKey)
				.toString();
		return message;
	}
}

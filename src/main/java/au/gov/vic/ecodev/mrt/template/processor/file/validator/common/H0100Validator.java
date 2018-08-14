package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ValidatorHelper;
import au.gov.vic.ecodev.utils.validator.helper.ErrorMessageChecker;

public class H0100Validator implements Validator {

	private static final String REGEX_ALPHA = "\\p{Alpha}+";

	private static final String REGEX_DIGIT = "[0-9]+";

	private static final String REGEX_PUNCT_OR_BLANK = "\\p{Punct}|\\p{Blank}";

	private static final Pattern PATTERN_TENEMENT = 
			Pattern.compile("^\\p{Alpha}+\\p{Digit}+$");
	
	private static final Pattern PATTERN_CHAR_NUM_GROUP = 
			Pattern.compile("(\\p{Alpha}+\\p{Digit}+)");
	
	private String[] strs;
	
	@Override
	public void init(String[] strs) {
		this.strs = strs;
	}

	@Override
	public Optional<List<String>> validate(Map<String, List<String>> templateParamMap, Template dataBean) {
		List<String> messages = new ArrayList<>();
		if (null == strs) {
			String message = "H0100 requires minimum 3 columns, only got 0";
			messages.add(message);
		}  else if (strs.length < Numeral.THREE) {
			String message = "H0100 requires minimum 3 columns, only got " + strs.length;
			messages.add(message);
		} else {
			String[] tenements = strs[Numeral.TWO].split(REGEX_PUNCT_OR_BLANK);
			String previous = null;
			List<String> correctTenements = new ArrayList<>();
			for(String tenement : tenements) {
				if (StringUtils.isNotBlank(tenement)) {
					if (PATTERN_TENEMENT.matcher(tenement).matches()) {
						correctTenements.add(tenement);
					} else {
						if (tenement.matches(REGEX_DIGIT)) {
							if (null == previous) {
								buildErrorMessage(messages);
								break;
							} else {
								previous += tenement;
								if (PATTERN_TENEMENT.matcher(previous).matches()) {
									correctTenements.add(previous);
								} else {
									buildErrorMessage(messages);
									break;	
								} 
								previous = null;
							} 
						} else if (tenement.matches(REGEX_ALPHA)){
							previous = tenement;
						} else {
							List<String> tenementList = new ArrayList<>();
							Matcher matcher = PATTERN_CHAR_NUM_GROUP.matcher(tenement);
							while(matcher.find()) {
								tenementList.add(matcher.group(Numeral.ONE));
							}
							if (Numeral.ZERO == tenementList.size()) {
								buildErrorMessage(messages);
								break;	
							} else {
								String newString = String.join(Strings.EMPTY, tenementList);
								if (tenement.equals(newString)) {
									correctTenements.addAll(tenementList);
								} else {
									buildErrorMessage(messages);
									break;	
								}
							}
						}
					}
				}
			}
			if (Numeral.ZERO ==  messages.size()) {
				strs[Numeral.TWO] = String.join(Strings.COMMA, correctTenements);
			}
		}
		
		boolean hasErrorMessage = new ErrorMessageChecker(messages).isContainsErrorMessages();
		return new ValidatorHelper(messages, hasErrorMessage)
				.updateDataBeanOrCreateErrorOptional(strs, dataBean);
	}

	private void buildErrorMessage(List<String> messages) {
		String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
				.append("Tenement must be in the format of abc123, but got: ")
				.append(strs[Numeral.TWO])
				.toString();
		messages.add(message);
	}

}

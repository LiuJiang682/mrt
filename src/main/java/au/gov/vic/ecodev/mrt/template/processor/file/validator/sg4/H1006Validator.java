package au.gov.vic.ecodev.mrt.template.processor.file.validator.sg4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ErrorMessageChecker;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ValidatorHelper;

public class H1006Validator implements Validator {

	private static final String LABLE_PREFERRED = "P";
	private String[] strs;
	
	@Override
	public void init(String[] strs) {
		this.strs = strs;
	}

	@Override
	public Optional<List<String>> validate(Map<String, List<String>> templateParamMap, Template dataBean) {
		List<String> messages = new ArrayList<>();
		List<String> headers = templateParamMap.get(Strings.COLUMN_HEADERS);
		Map<String, List<Integer>> elementPositionMap = new HashMap<>();
		Map<String, List<Integer>> duplicateHeaderMap = new HashMap<>();
		int len = headers.size();
		for (int index = Numeral.ZERO; index < len; index++) {
			String header = headers.get(index);
			List<Integer> positions = elementPositionMap.get(header);
			if (CollectionUtils.isEmpty(positions)) {
				positions = new ArrayList<>();
			}
			positions.add(index);
			elementPositionMap.put(header, positions);
		}
		Iterator<String> iterator = elementPositionMap.keySet().iterator();
		while (iterator.hasNext()) {
			String header = iterator.next();
			List<Integer> positions = elementPositionMap.get(header);
			if (Numeral.ONE < positions.size()) {
				List<String> values = new ArrayList<>();
				for (int index = Numeral.ZERO; index < positions.size(); index++) {
					int position = positions.get(index);
					++position;
					String content = (position < strs.length) ? strs[position] : Strings.EMPTY;
					values.add(content);
				}
				
				Optional<String> preferredOptional = values.stream()
					.filter(value -> LABLE_PREFERRED.equalsIgnoreCase(value))
					.findFirst();
				if (preferredOptional.isPresent()) {
					duplicateHeaderMap.put(header, positions);
				} else {
					String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
							.append(header)
							.append(" has NO preferred result provided")
							.toString();
					messages.add(message);
				}
			}
		}
		
		boolean hasErrorMessage = new ErrorMessageChecker(messages).isContainsErrorMessages();
		if (!hasErrorMessage) {
			Iterator<String> duplicateIterator = duplicateHeaderMap.keySet().iterator();
			while(duplicateIterator.hasNext()) {
				String header = duplicateIterator.next();
				List<Integer> indexList = duplicateHeaderMap.get(header);
				List<String> indexStringList = indexList.stream()
						.map(Object::toString)
						.collect(Collectors.toList());
				dataBean.put(Strings.KEY_PREFIX_DUPLICATED +  header, indexStringList);
			}
		}
		return new ValidatorHelper(messages, hasErrorMessage)
				.updateDataBeanOrCreateErrorOptional(strs, dataBean);
	}

}

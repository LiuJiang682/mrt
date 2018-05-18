package au.gov.vic.ecodev.mrt.template.processor.validator.helper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.ArrayUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class ValidatorHelper {

	private final List<String> messages;
	private final boolean hasErrorMessage;
	
	public ValidatorHelper(List<String> messages, final boolean hasErrorMessage) {
		if (null == messages) {
			throw new IllegalArgumentException("Parameter messages cannot be null!");
		}
		this.messages = messages;
		this.hasErrorMessage = hasErrorMessage;
	}

	public Optional<List<String>> updateDataBeanOrCreateErrorOptional(String[] datas,
			Template dataBean) {
		if (hasErrorMessage) {
			return Optional.of(messages);
		} else {
			if ((null != dataBean) 
					&& (ArrayUtils.isNotEmpty(datas)) 
					&& (Numeral.TWO <= datas.length)) {
				String[] newDatas = Arrays.copyOfRange(datas, Numeral.ONE, datas.length);
				List<String> values = Arrays.asList(newDatas);
				dataBean.put(datas[Numeral.ZERO], values);
			}
			return messages.isEmpty() ? Optional.empty() : Optional.of(messages);
		} 
	}

	
}

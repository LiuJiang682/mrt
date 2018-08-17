package au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ValidatorHelper;

public class Sl4DefaultValidator implements Validator {

	private String[] strs;
	
	@Override
	public Optional<List<String>> validate(Map<String, List<String>> templateParamMap,
			Template dataBean) {
		List<String> messages = new ArrayList<>();
		
		return new ValidatorHelper(messages, false)
				.updateDataBeanOrCreateErrorOptional(strs, dataBean);
	}

	@Override
	public void init(String[] strs) {
		this.strs = strs;
	}

}

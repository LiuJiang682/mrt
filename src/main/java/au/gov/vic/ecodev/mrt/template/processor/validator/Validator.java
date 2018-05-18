package au.gov.vic.ecodev.mrt.template.processor.validator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public interface Validator {

	void init(String[] strs);
	Optional<List<String>> validate(Map<String, List<String>> templateParamMap, 
			Template dataBean);

}

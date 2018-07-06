package au.gov.vic.ecodev.mrt.template.processor.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;

public class MrtTemplateBase implements Template {

	private Map<String, MrtTemplateValue> datas = new HashMap<>();
	
	@Override
	public void put(String key, List<String> values) {
		MrtTemplateValue value = new MrtTemplateValue(values, Numeral.NOT_FOUND);
		datas.put(key, value);
	}

	@Override
	public void put(String key, TemplateValue value) {
		datas.put(key, (MrtTemplateValue)value);
	}

	@Override
	public List<String> get(String key) {
		MrtTemplateValue value = datas.get(key);
		return (null == value) ? null : value.getDatas() ;
	}

	@Override
	public TemplateValue getTemplateValue(String key) {
		return datas.get(key);
	}

	@Override
	public List<String> getKeys() {
		return new ArrayList<String>(datas.keySet());
	}

}

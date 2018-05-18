package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.template.fields.CoordinateSystem;
import au.gov.vic.ecodev.mrt.template.fields.Projection;
import au.gov.vic.ecodev.mrt.template.fields.ProjectionZone;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ErrorMessageChecker;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ValidatorHelper;

public class H0530Validator implements Validator {

	private static final String COORDINATE_SYSTEM_TITLE = "Coordinate_system";
	private String[] strs;
	
	@Override
	public void init(String[] strs) {
		this.strs = strs;
	}

	@Override
	public Optional<List<String>> validate(Map<String, List<String>> templateParamMap, Template dataBean) {
		List<String> messages = new ArrayList<>();
		if (null == strs) {
			String message = "H0530 requires minimum 3 columns, only got 0";
			messages.add(message);
		} else if (strs.length < Numeral.THREE) {
			String message = "H0530 requires minimum 3 columns, only got " + strs.length;
			messages.add(message);
		} else {
			if (!COORDINATE_SYSTEM_TITLE.equalsIgnoreCase(strs[Numeral.ONE])) {
				String message = "H0530 title must be Coordinate_system, but got " + strs[Numeral.ONE];
				messages.add(message);
			} 
			if (!isValidCoordinateSystemValue(strs[Numeral.TWO])) {
				String message = "H0530 must be either Projected or Latitude/Longitude, but got " + strs[Numeral.TWO];
				messages.add(message);
			} else {
				doLatitudeLongitudeCrossCheck(templateParamMap, messages);
			}
		}
		
		boolean hasErrorMessage = new ErrorMessageChecker(messages).isContainsErrorMessages();
		if (!hasErrorMessage) {
			List<String> params = Arrays.asList(new String[] {strs[Numeral.TWO]});
			templateParamMap.put(COORDINATE_SYSTEM_TITLE, params);
		} 
		return new ValidatorHelper(messages, hasErrorMessage)
				.updateDataBeanOrCreateErrorOptional(strs, dataBean);
	}

	protected final void doLatitudeLongitudeCrossCheck(Map<String, List<String>> templateParamMap, List<String> messages) {
		CoordinateSystem coordinateSystem = CoordinateSystem.fromString(strs[Numeral.TWO]);
		if (CoordinateSystem.LATITUDE_LONGITUDE.equals(coordinateSystem)) {
			List<String> h0503List = templateParamMap.get(H0503Validator.PROJECTION_TITLE);
			doProjectionCrossCheck(messages, h0503List);
			List<String> h0531List = templateParamMap.get(H0531Validator.PROJECTION_ZONE_TITLE);
			doProjectionZoneCrossCheck(messages, h0531List);
		}
	}

	protected final void doProjectionZoneCrossCheck(List<String> messages, List<String> h0531List) {
		if (!CollectionUtils.isEmpty(h0531List)) {
			String h0531 = h0531List.get(Numeral.ZERO);
			if (!ProjectionZone.NON_PROJECTED.getZone().equalsIgnoreCase(h0531)) {
				String message = "H0530 is Latitude/Longitude, H0531 must be empty or Non-projected, but got: " + h0531;
				messages.add(message);
			}
		}
	}

	protected final void doProjectionCrossCheck(List<String> messages, List<String> h0503List) {
		if (!CollectionUtils.isEmpty(h0503List)) {
			String h0503 = h0503List.get(Numeral.ZERO);
			if (!Projection.NON_PROJECTED.getCode().equalsIgnoreCase(h0503)) {
				String message = "H0530 is Latitude/Longitude, H0503 must be empty or Non-projected, but got: " + h0503;
				messages.add(message);
			}
		}
	}

	protected final boolean isValidCoordinateSystemValue(String string) {
		boolean isValid = true;
		
		try {
			CoordinateSystem.fromString(string);
		} catch (IllegalArgumentException e) {
			isValid = false;
		}
		return isValid;
	}

}

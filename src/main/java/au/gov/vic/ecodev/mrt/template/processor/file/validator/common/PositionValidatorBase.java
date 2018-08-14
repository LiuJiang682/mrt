package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.common.util.StringNumberFormatValidator;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.map.services.MapServices;
import au.gov.vic.ecodev.mrt.map.services.VictoriaMapServices;
import au.gov.vic.ecodev.mrt.template.fields.ProjectionZone;
import au.gov.vic.ecodev.mrt.template.fields.SL4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.fields.Sg4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.helper.MessageHelper;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.helper.PartialFileNameFlagHelper;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.helper.TenementWarningMessageHelper;

public abstract class PositionValidatorBase {

	private final String[] strs;
	private final long lineNumber;
	private final Map<String, List<String>> templateParamMap;
	private final TemplateProcessorContext context;
	private final boolean isPartialFileName;
	
	public PositionValidatorBase(String[] strs, long lineNumber, Map<String, List<String>> templateParamMap, 
			TemplateProcessorContext context) {
		if (null == strs) {
			throw new IllegalArgumentException("PositionValidatorBase:strs cannot be null!");
		}
		this.strs = strs;
		this.lineNumber = lineNumber;
		if (null == templateParamMap) {
			throw new IllegalArgumentException("PositionValidatorBase:templateParamMap cannot be null!");
		}
		this.templateParamMap = templateParamMap;
		if (null == context) {
			throw new IllegalArgumentException("PositionValidatorBase:context cannot be null!");
		}
		this.context = context;
		this.isPartialFileName = new PartialFileNameFlagHelper(templateParamMap).getPartilaFileNameFlag();
	}
	
	public void validate(List<String> messages) {
		List<String> columnHeaders = templateParamMap.get(Strings.COLUMN_HEADERS);
		if (CollectionUtils.isEmpty(columnHeaders)) {
			// Remove this due to user request.
			// String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
			//		.append("No column header has been passing down")
			//		.toString();
			// messages.add(message);
		} else {
			List<String> projectionZoneList = templateParamMap
					.get(Strings.TITLE_PREFIX + H0531Validator.PROJECTION_ZONE_TITLE);
			if (CollectionUtils.isEmpty(projectionZoneList)) {
				String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
						.append("No projection_zone passing down")
						.toString();
				messages.add(message);
			} else {
				String projectionZone = projectionZoneList.get(Numeral.ZERO);
				int eastingIndex = columnHeaders.indexOf(Sg4ColumnHeaders.EASTING_MGA.getCode());
				boolean useAmg = false;
				if (Numeral.NOT_FOUND == eastingIndex) {
					useAmg = true;
					eastingIndex = columnHeaders.indexOf(Sg4ColumnHeaders.EASTING_AMG.getCode());
				}
				BigDecimal easting = null;
				if (Numeral.NOT_FOUND != eastingIndex) {
					String dataString = strs[++eastingIndex];
					if (new StringNumberFormatValidator(dataString).isValid()) {
						easting = new BigDecimal(dataString);
					}
				}
				int northingIndex = columnHeaders.indexOf(Sg4ColumnHeaders.NORTHING_MGA.getCode());
				if (Numeral.NOT_FOUND == northingIndex) {
					northingIndex = columnHeaders.indexOf(Sg4ColumnHeaders.NORTHING_AMG.getCode());
				}
				BigDecimal northing = null;
				if (Numeral.NOT_FOUND != northingIndex) {
					String dataString = strs[++northingIndex];
					if (new StringNumberFormatValidator(dataString).isValid()) {
						northing = new BigDecimal(dataString);
					}
				}
				String id = getId(messages, columnHeaders, getIdCode());
				MapServices mapServices = context.getMapServices();
				VictoriaMapServices  victoriaMapServices = (VictoriaMapServices) mapServices;
//				List<String> tenmentNoList = new StringListHelper()
//						.trimOffTheTitle(templateParamMap.get(Strings.KEY_H0100));
				List<String> tenmentNoList = templateParamMap.get(Strings.KEY_H0100);
				if ((null == easting) 
						&& (null == northing)) {
					doLatitudeLongitudeCheck(messages, id, projectionZone, columnHeaders,
							victoriaMapServices, tenmentNoList);
				} else {
					if (ProjectionZone.VIC_54.getZone().equals(projectionZone)) {
						doZone54Check(messages, id, useAmg, easting, northing, 
								victoriaMapServices, tenmentNoList);
					} else if (ProjectionZone.VIC_55.getZone().equals(projectionZone)) {
						doZone55Check(messages, id, useAmg, easting, northing, 
								victoriaMapServices, tenmentNoList);
					}
				}
			}
		}
	}
	
	protected final String[] getStrs() {
		return strs;
	}
	
	protected final Map<String, List<String>>getTemplateParamMap() {
		return templateParamMap;
	}
	
	protected final TemplateProcessorContext getTemplateProcessorContext() {
		return context;
	}
	
	protected final void doZone55Check(List<String> messages, String sampleId, boolean useAmg, 
			BigDecimal easting, BigDecimal northing,
			VictoriaMapServices victoriaMapServices, List<String> tenementNoList) {
		if (useAmg) {
			if (victoriaMapServices.isWithinAgd55NorthEast(easting, northing)) {
				if (CollectionUtils.isEmpty(tenementNoList)) {
					new TenementWarningMessageHelper().contructNoTenementWarningMessage(messages);
				} else {
					if (!isPartialFileName) {
						boolean foundTenement = false;
						for (String tenenmentNo : tenementNoList) {
							if (victoriaMapServices.isWithinTenementAgd55NorthEast(tenenmentNo, 
								easting, northing)) {
								foundTenement = true;
								break;
							}
						}
						if (!foundTenement) {
							handleNoTenementFound(messages, lineNumber, sampleId, context, easting, northing, 
								tenementNoList);
						}
					}
				}
			} else {
				contructErrorMessage(messages, lineNumber, easting, northing);
			}
		} else {
			if (victoriaMapServices.isWithinMga55NorthEast(easting, northing)) {
				if (CollectionUtils.isEmpty(tenementNoList)) {
					new TenementWarningMessageHelper().contructNoTenementWarningMessage(messages);
				} else {
					if (!isPartialFileName) {
						boolean foundTenement = false;
						for (String tenenmentNo : tenementNoList) {
							if (victoriaMapServices.isWithinTenementMga55NorthEast(tenenmentNo, 
								easting, northing)) {
								foundTenement = true;
								break;
							}
						}
						if (!foundTenement) {
							handleNoTenementFound(messages, lineNumber, sampleId, context, easting, northing, 
								tenementNoList);
						}
					}
				}
			} else {
				contructErrorMessage(messages, lineNumber, easting, northing);
			}
		}
	}
	
	protected final void doZone54Check(List<String> messages, String sampleId, boolean useAmg, 
			BigDecimal easting, BigDecimal northing,
			VictoriaMapServices victoriaMapServices, List<String> tenementNoList) {
		if (useAmg) {
			if (victoriaMapServices.isWithinAgd54NorthEast(easting, northing)) {
				if (CollectionUtils.isEmpty(tenementNoList)) {
					new TenementWarningMessageHelper().contructNoTenementWarningMessage(messages);
				} else {
					if (!isPartialFileName) {
						boolean foundTenement = false;
						for (String tenenmentNo : tenementNoList) {
							if (victoriaMapServices.isWithinTenementAgd54NorthEast(tenenmentNo, 
									easting, northing)) {
								foundTenement = true;
								break;
							}
						}
						if (!foundTenement) {
							handleNoTenementFound(messages, lineNumber, sampleId, context, easting, northing, 
									tenementNoList);
						}
					}
				}
			} else {
				contructErrorMessage(messages, lineNumber, easting, northing);
			}
		} else {
			if (victoriaMapServices.isWithinMga54NorthEast(easting, northing)) {
				if (CollectionUtils.isEmpty(tenementNoList)) {
					new TenementWarningMessageHelper().contructNoTenementWarningMessage(messages);
				} else {
					if (!isPartialFileName) {
						boolean foundTenement = false;
						for (String tenenmentNo : tenementNoList) {
							if (victoriaMapServices.isWithinTenementMga54NorthEast(tenenmentNo, 
								easting, northing)) {
								foundTenement = true;
								break;
							}
						}
						if (!foundTenement) {
							handleNoTenementFound(messages, lineNumber, sampleId, context, easting, northing, 
								tenementNoList);
						}
					}
				}
			} else {
				contructErrorMessage(messages, lineNumber, easting, northing);
			}
		}
	}
	
	public final void doLatitudeLongitudeCheck(List<String> messages, String sampleId,
			String projectionZone, List<String> columnHeaders, 
			VictoriaMapServices victoriaMapServices, List<String> tenmentNoList) {

		BigDecimal latitude = getCoordinateData(messages, columnHeaders, 
				SL4ColumnHeaders.LATITUDE.getCode());
		BigDecimal longitude = getCoordinateData(messages, columnHeaders, 
				SL4ColumnHeaders.LONGITUDE.getCode());
		if ((null != latitude)
				&& (null != longitude)) {
			if (ProjectionZone.VIC_54.getZone().equals(projectionZone)) {
				doZone54LatitudeLongitudeCheck(messages, sampleId, latitude, longitude, 
						victoriaMapServices, tenmentNoList);
			} else if (ProjectionZone.VIC_55.getZone().equals(projectionZone)) {
				doZone55LatitudeLongitudeCheck(messages, sampleId, latitude, longitude, 
						victoriaMapServices, tenmentNoList);
			}
		}
	}
	
	protected final void doZone54LatitudeLongitudeCheck(List<String> messages, String sampleId,
			BigDecimal latitude, BigDecimal longitude, VictoriaMapServices victoriaMapServices, 
			List<String> tenementNoList) {
		if (victoriaMapServices.isWithinMga54LatitudeLongitude(latitude, longitude)) {
			if (CollectionUtils.isEmpty(tenementNoList)) {
				new TenementWarningMessageHelper().contructNoTenementWarningMessage(messages);
			} else {
				if (!isPartialFileName) {
					boolean foundTenement = false;
					for (String tenenmentNo : tenementNoList) {
						if (victoriaMapServices.isWithinTenementMga54LatitudeLongitude(tenenmentNo, 
							latitude, longitude)) {
							foundTenement = true;
							break;
						}
					}
					if (!foundTenement) {
						handleNoTenementFound(messages, lineNumber, sampleId, context, latitude, longitude, 
							tenementNoList);
					}
				}
			}
		} else {
			contructErrorMessage(messages, lineNumber, latitude, longitude);
		}
	}
	
	protected final void doZone55LatitudeLongitudeCheck(List<String> messages, String sampleId,
			BigDecimal latitude, BigDecimal longitude,
			VictoriaMapServices victoriaMapServices, List<String> tenementNoList) {
		if (victoriaMapServices.isWithinMga55LatitudeLongitude(latitude, longitude)) {
			if (CollectionUtils.isEmpty(tenementNoList)) {
				new TenementWarningMessageHelper().contructNoTenementWarningMessage(messages);
			} else {
				if (!isPartialFileName) {
					boolean foundTenement = false;
					for (String tenenmentNo : tenementNoList) {
						if (victoriaMapServices.isWithinTenementMga55LatitudeLongitude(tenenmentNo, 
							latitude, longitude)) {
							foundTenement = true;
							break;
						}
					}
					if (!foundTenement) {
						handleNoTenementFound(messages, lineNumber, sampleId, context, latitude, longitude, 
							tenementNoList);
					}
				}
			}
		} else {
			contructErrorMessage(messages, lineNumber, latitude, longitude);
		}
	}
	
	protected final String getId(final List<String> messages, 
			final List<String> columnHeaders, String code) {
		int columnIndex = columnHeaders.indexOf(code);
		String holeId = null;
		if (Numeral.NOT_FOUND == columnIndex) {
			new MessageHelper(lineNumber, code).constructMissingHeaderMessages(messages);
		} else {
			holeId = strs[++columnIndex];
		}
		return holeId;
	}
	
	public final BigDecimal getCoordinateData(final List<String> messages, 
			final List<String> columnHeaders, String code) {
		int columnIndex = columnHeaders.indexOf(code);
		BigDecimal data = null;
		if (Numeral.NOT_FOUND == columnIndex) {
			new MessageHelper(lineNumber, code).constructMissingHeaderMessages(messages);
		} else {
			String dataString = strs[++columnIndex];
			if (new StringNumberFormatValidator(dataString).isValid()) {
				data = new BigDecimal(dataString);
			} else {
				new MessageHelper(lineNumber, code).constructInvalidNumberDataMessage(messages, dataString);
			}
		}
		return data;
	}
	
	protected abstract void handleNoTenementFound(List<String> messages, long lineNumber, 
			String sample, TemplateProcessorContext context,
			BigDecimal easting, BigDecimal northing, List<String> tenementNoList);
	protected abstract void contructErrorMessage(List<String> messages, long lineNumber, 
			BigDecimal easting, BigDecimal northing);
	protected abstract String getIdCode();
}

package au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4;

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
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0531Validator;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.helper.PartialFileNameFlagHelper;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.helper.StringListHelper;

public class BoreHolePositionValidator {

	private final String[] strs;
	private final long lineNumber;
	private final Map<String, List<String>> templateParamMap;
	private final TemplateProcessorContext context;
	private final boolean isPartialFileName;
	
	public BoreHolePositionValidator(String[] strs, long lineNumber, Map<String, List<String>> templateParamMap, 
			TemplateProcessorContext context) {
		if (null == strs) {
			throw new IllegalArgumentException("BoreHolePositionValidator:strs cannot be null!");
		}
		this.strs = strs;
		this.lineNumber = lineNumber;
		if (null == templateParamMap) {
			throw new IllegalArgumentException("BoreHolePositionValidator:templateParamMap cannot be null!");
		}
		this.templateParamMap = templateParamMap;
		if (null == context) {
			throw new IllegalArgumentException("BoreHolePositionValidator:context cannot be null!");
		}
		this.context = context;
		this.isPartialFileName = new PartialFileNameFlagHelper(templateParamMap).getPartilaFileNameFlag();
	}

	public void validate(List<String> messages) {
		List<String> columnHeaders = templateParamMap.get(Strings.COLUMN_HEADERS);
		if (CollectionUtils.isEmpty(columnHeaders)) {
			String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
					.append("No column header has been passing down")
					.toString();
			messages.add(message);
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
				int eastingIndex = columnHeaders.indexOf(SL4ColumnHeaders.EASTING_MGA.getCode());
				boolean useAmg = false;
				if (Numeral.NOT_FOUND == eastingIndex) {
					useAmg = true;
					eastingIndex = columnHeaders.indexOf(SL4ColumnHeaders.EASTING_AMG.getCode());
				}
				BigDecimal easting = null;
				if (Numeral.NOT_FOUND != eastingIndex) {
					String dataString = strs[++eastingIndex];
					if (new StringNumberFormatValidator(dataString).isValid()) {
						easting = new BigDecimal(dataString);
					}
				}
				int northingIndex = columnHeaders.indexOf(SL4ColumnHeaders.NORTHING_MGA.getCode());
				if (Numeral.NOT_FOUND == northingIndex) {
					northingIndex = columnHeaders.indexOf(SL4ColumnHeaders.NORTHING_AMG.getCode());
				}
				BigDecimal northing = null;
				if (Numeral.NOT_FOUND != northingIndex) {
					String dataString = strs[++northingIndex];
					if (new StringNumberFormatValidator(dataString).isValid()) {
						northing = new BigDecimal(dataString);
					}
				}
				String boreHoleId = getHoleId(messages, columnHeaders, SL4ColumnHeaders.HOLE_ID.getCode());
				MapServices mapServices = context.getMapServices();
				VictoriaMapServices  victoriaMapServices = (VictoriaMapServices) mapServices;
				List<String> tenmentNoList = new StringListHelper()
						.trimOffTheTitle(templateParamMap.get(Strings.KEY_H0100));
				if ((null == easting) 
						&& (null == northing)) {
					doLatitudeLongitudeCheck(messages, boreHoleId, projectionZone, columnHeaders,
							victoriaMapServices, tenmentNoList);
				} else {
					if (ProjectionZone.VIC_54.getZone().equals(projectionZone)) {
						doZone54Check(messages, boreHoleId, useAmg, easting, northing, 
								victoriaMapServices, tenmentNoList);
					} else if (ProjectionZone.VIC_55.getZone().equals(projectionZone)) {
						doZone55Check(messages, boreHoleId, useAmg, easting, northing, 
								victoriaMapServices, tenmentNoList);
					}
				}
			}
		}
	}

	protected final void doLatitudeLongitudeCheck(List<String> messages, String boreHoleId,
			String projectionZone, List<String> columnHeaders, 
			VictoriaMapServices victoriaMapServices, List<String> tenmentNoList) {

		BigDecimal latitude = getCoordinateData(messages, columnHeaders, 
				SL4ColumnHeaders.LATITUDE.getCode());
		BigDecimal longitude = getCoordinateData(messages, columnHeaders, 
				SL4ColumnHeaders.LONGITUDE.getCode());
		if ((null != latitude)
				&& (null != longitude)) {
			if (ProjectionZone.VIC_54.getZone().equals(projectionZone)) {
				doZone54LatitudeLongitudeCheck(messages, boreHoleId, latitude, longitude, 
						victoriaMapServices, tenmentNoList);
			} else if (ProjectionZone.VIC_55.getZone().equals(projectionZone)) {
				doZone55LatitudeLongitudeCheck(messages, boreHoleId, latitude, longitude, 
						victoriaMapServices, tenmentNoList);
			}
		}
	}

	protected final void doZone55LatitudeLongitudeCheck(List<String> messages, String boreHoleId,
			BigDecimal latitude, BigDecimal longitude,
			VictoriaMapServices victoriaMapServices, List<String> tenementNoList) {
		if (victoriaMapServices.isWithinMga55LatitudeLongitude(latitude, longitude)) {
			if (CollectionUtils.isEmpty(tenementNoList)) {
				contructNoTenementWarningMessage(messages);
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
						handleNoTenementFound(messages, boreHoleId, latitude, longitude, 
							tenementNoList);
					}
				}
			}
		} else {
			contructErrorMessage(messages, latitude, longitude);
		}
	}

	protected final String getHoleId(final List<String> messages, 
			final List<String> columnHeaders, String code) {
		int columnIndex = columnHeaders.indexOf(code);
		String holeId = null;
		if (Numeral.NOT_FOUND == columnIndex) {
			constructMissingHeaderMessages(messages, code);
		} else {
			holeId = strs[++columnIndex];
		}
		return holeId;
	}
	
	protected final BigDecimal getCoordinateData(final List<String> messages, 
			final List<String> columnHeaders, String code) {
		int columnIndex = columnHeaders.indexOf(code);
		BigDecimal data = null;
		if (Numeral.NOT_FOUND == columnIndex) {
			constructMissingHeaderMessages(messages, code);
		} else {
			String dataString = strs[++columnIndex];
			if (new StringNumberFormatValidator(dataString).isValid()) {
				data = new BigDecimal(dataString);
			} else {
				constructInvalidNumberDataMessage(messages, code, dataString);
			}
		}
		return data;
	}

	protected final void doZone54LatitudeLongitudeCheck(List<String> messages, String boreHoleId,
			BigDecimal latitude, BigDecimal longitude, VictoriaMapServices victoriaMapServices, 
			List<String> tenementNoList) {
		if (victoriaMapServices.isWithinMga54LatitudeLongitude(latitude, longitude)) {
			if (CollectionUtils.isEmpty(tenementNoList)) {
				contructNoTenementWarningMessage(messages);
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
						handleNoTenementFound(messages, boreHoleId, latitude, longitude, 
							tenementNoList);
					}
				}
			}
		} else {
			contructErrorMessage(messages, latitude, longitude);
		}
	}

	protected final void doZone55Check(List<String> messages, String boreHoleId, boolean useAmg, 
			BigDecimal easting, BigDecimal northing,
			VictoriaMapServices victoriaMapServices, List<String> tenementNoList) {
		if (useAmg) {
			if (victoriaMapServices.isWithinAgd55NorthEast(easting, northing)) {
				if (CollectionUtils.isEmpty(tenementNoList)) {
					contructNoTenementWarningMessage(messages);
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
							handleNoTenementFound(messages, boreHoleId, easting, northing, 
								tenementNoList);
						}
					}
				}
			} else {
				contructErrorMessage(messages, easting, northing);
			}
		} else {
			if (victoriaMapServices.isWithinMga55NorthEast(easting, northing)) {
				if (CollectionUtils.isEmpty(tenementNoList)) {
					contructNoTenementWarningMessage(messages);
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
							handleNoTenementFound(messages, boreHoleId, easting, northing, 
								tenementNoList);
						}
					}
				}
			} else {
				contructErrorMessage(messages, easting, northing);
			}
		}
	}

	protected final void doZone54Check(List<String> messages, String boreHoleId, boolean useAmg, 
			BigDecimal easting, BigDecimal northing,
			VictoriaMapServices victoriaMapServices, List<String> tenementNoList) {
		if (useAmg) {
			if (victoriaMapServices.isWithinAgd54NorthEast(easting, northing)) {
				if (CollectionUtils.isEmpty(tenementNoList)) {
					contructNoTenementWarningMessage(messages);
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
							handleNoTenementFound(messages, boreHoleId, easting, northing, 
									tenementNoList);
						}
					}
				}
			} else {
				contructErrorMessage(messages, easting, northing);
			}
		} else {
			if (victoriaMapServices.isWithinMga54NorthEast(easting, northing)) {
				if (CollectionUtils.isEmpty(tenementNoList)) {
					contructNoTenementWarningMessage(messages);
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
							handleNoTenementFound(messages, boreHoleId, easting, northing, 
								tenementNoList);
						}
					}
				}
			} else {
				contructErrorMessage(messages, easting, northing);
			}
		}
	}

	protected final void handleNoTenementFound(List<String> messages, String boreHoleId,
			BigDecimal easting, BigDecimal northing, List<String> tenementNoList) {
		contructNoTenementFoundWarningMessage(messages, boreHoleId, easting, northing, 
				tenementNoList);
		List<String> boreHoleIds = this.context.getMessage().getBoreHoleIdsOutSideTenement();
		boreHoleIds.add(boreHoleId);
	}
	
	private void contructErrorMessage(List<String> messages, BigDecimal easting, 
			BigDecimal northing) {
		String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
				.append("Line number ")
				.append(lineNumber)
				.append("'s bore hole coordinate ")
				.append(easting.doubleValue())
				.append(Strings.COMMA)
				.append(Strings.SPACE)
				.append(northing.doubleValue())
				.append(" are not inside Victoria")
				.toString();
		messages.add(message);
	}
	
	private final void constructInvalidNumberDataMessage(List<String> messages, 
			String code, String string) {
		String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
				.append("Line number ")
				.append(lineNumber)
				.append(" has invalid data: ")
				.append(string)
				.append(" for column: ")
				.append(code)
				.toString();
		messages.add(message);
	}
	
	private final void constructMissingHeaderMessages(List<String> messages, String code) {
		String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
				.append("Line number ")
				.append(lineNumber)
				.append(" is missing header ")
				.append(code)
				.toString();
		messages.add(message);
	}
	
	private void contructNoTenementWarningMessage(List<String> messages) {
		String message = new StringBuilder(Strings.LOG_WARNING_HEADER)
				.append("No tenement no provided!")
				.toString();
		messages.add(message);		
	}
	
	private void contructNoTenementFoundWarningMessage(List<String> messages, String boreHoleId,
			BigDecimal x, BigDecimal y, List<String> tenementNoList) {
		String message = new StringBuilder(Strings.LOG_WARNING_HEADER)
				.append("Line number ")
				.append(lineNumber)
				.append(" bore hole ID: ")
				.append(boreHoleId)
				.append(", coordinate: ")
				.append(x)
				.append(", ")
				.append(y)
				.append(" is not inside the tenement: ")
				.append(String.join(Strings.COMMA, tenementNoList))
				.toString();
		messages.add(message);
		
	}
}

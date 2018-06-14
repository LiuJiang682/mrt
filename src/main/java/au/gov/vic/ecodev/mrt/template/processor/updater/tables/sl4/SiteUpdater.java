package au.gov.vic.ecodev.mrt.template.processor.updater.tables.sl4;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import au.gov.vic.ecodev.common.util.NullSafeCollections;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.dao.sl4.SiteDao;
import au.gov.vic.ecodev.mrt.model.sl4.Site;
import au.gov.vic.ecodev.mrt.template.fields.SL4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.updater.helper.ProjectionZoneExtractionHelper;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.index.finder.sl4.HoleIdIndexFinder;

public class SiteUpdater {

	private static final String N_A = "N/A";
	private static final String KEY_H0102 = "H0102";
	private static final String KEY_H0506 = "H0506";
	private static final String KEY_H0501 = "H0501";
	private static final String KEY_H0502 = "H0502";
	
	private final Template template;
	private final SiteDao siteDao;
	private final long sessionId;
	
	private int holeIdIndex;
	private int eastingIndex;
	private int northingIndex;
	private int latitudeIndex;
	private int longitudeIndex;
	private String prospect;
	private BigDecimal amgZone;
	private BigDecimal locnAcc;
	private String locnDatumCd;
	private String elevDatumCd;
	
	public SiteUpdater(final long sessionId, final Template template, final SiteDao siteDao) {
		this.sessionId = sessionId;
		this.template = template;
		this.siteDao = siteDao;
	}

	public void init(final List<Integer> mandatoryFieldsIndexList) throws TemplateProcessorException {
		List<String> headers = template.get(Strings.KEY_H1000);
		holeIdIndex = new HoleIdIndexFinder(headers).find();
		mandatoryFieldsIndexList.add(holeIdIndex);
		eastingIndex = extractEastingIndex(headers);
		mandatoryFieldsIndexList.add(eastingIndex);
		northingIndex = extractNorthingIndex(headers);
		mandatoryFieldsIndexList.add(northingIndex);
		latitudeIndex = headers.indexOf(SL4ColumnHeaders.LATITUDE.getCode());
		if (Numeral.NOT_FOUND != latitudeIndex) {
			mandatoryFieldsIndexList.add(latitudeIndex);
		}
		longitudeIndex = headers.indexOf(SL4ColumnHeaders.LONGITUDE.getCode());
		if (Numeral.NOT_FOUND != longitudeIndex)  {
			mandatoryFieldsIndexList.add(longitudeIndex);
		}
		prospect = extractProjectNameFromTemplate(template);
		amgZone = new ProjectionZoneExtractionHelper(template).extractAmgZoneFromTemplate();
		locnAcc = extractLocnAccFromTemplate(template);
		locnDatumCd = extractLocnDatumCdFromTemplate(template);
		elevDatumCd = extractElevDatumCdFromTemplate(template);
	}
	
	public void update(List<String> dataRecordList) {
		Site site = new Site();
		site.setLoaderId(sessionId);
		site.setSiteId((String) new NullSafeCollections(dataRecordList).get(holeIdIndex));
		site.setParish(N_A);
		site.setProspect(prospect);
		site.setAmgZone(amgZone);
		site.setEasting(extractEastingFromRecord(dataRecordList, eastingIndex));
		site.setNorthing(extractNorthingFromRecord(dataRecordList, northingIndex));
		site.setLatitude(extractOptionalNumberFromRecord(dataRecordList, latitudeIndex));
		site.setLongitude(extractOptionalNumberFromRecord(dataRecordList, longitudeIndex));
		site.setLocnAcc(locnAcc);
		site.setLocnDatumCd(locnDatumCd);
		site.setElevDatumCd(elevDatumCd);
		siteDao.updateOrSave(site);
	}
	
	protected final BigDecimal extractEastingFromRecord(final List<String> dataRecordList, 
			final int eastingIndex) {
		String eastingString = (String) new NullSafeCollections(dataRecordList).get(eastingIndex);
		return convertStringToBigDecimal(eastingString);
	}
	
	protected final BigDecimal extractOptionalNumberFromRecord(List<String> dataRecordList, int index) {
		BigDecimal number = null;
		if (Numeral.NOT_FOUND != index) {
			String numberString = (String) new NullSafeCollections(dataRecordList).get(index);
			number = convertStringToBigDecimal(numberString);
		}
		return number;
	}

	protected final BigDecimal extractNorthingFromRecord(List<String> dataRecordList, int northingIndex) {
		String northingString = (String) new NullSafeCollections(dataRecordList).get(northingIndex);
		return convertStringToBigDecimal(northingString);
	}
	
	protected final BigDecimal convertStringToBigDecimal(final String numberString) {
		BigDecimal number = null;
		if (!StringUtils.isEmpty(numberString)) {
			try {
				number = new BigDecimal(numberString);
			} catch (NumberFormatException e) {
				//Ignore
			}
		}
		return number;
	}
	
	protected final String extractElevDatumCdFromTemplate(Template template) {
		String elevDatumCd = null;
		List<String> elevDatumCdList = template.get(KEY_H0502);
		if ((!CollectionUtils.isEmpty(elevDatumCdList)) 
				&& (Numeral.ONE < elevDatumCdList.size())) {
			elevDatumCd = elevDatumCdList.get(Numeral.ONE);
		}
		return elevDatumCd;
	}
	
	protected final String extractLocnDatumCdFromTemplate(Template template) {
		String locnDatumCd = null;
		List<String> locnDatumCdList = template.get(KEY_H0501);
		if ((!CollectionUtils.isEmpty(locnDatumCdList)) 
				&& (Numeral.ONE < locnDatumCdList.size())) {
			locnDatumCd = locnDatumCdList.get(Numeral.ONE);
		}
		return locnDatumCd;
	}
	
	protected final BigDecimal extractLocnAccFromTemplate(Template template2) {
		BigDecimal locnAcc = null;
		List<String> locnAccList = template.get(KEY_H0506);
		if ((!CollectionUtils.isEmpty(locnAccList)) 
				&& (Numeral.ONE < locnAccList.size())) {
			String locnAccString = locnAccList.get(Numeral.ONE);
			try {
				locnAcc = new BigDecimal(locnAccString);
			}catch (NumberFormatException e) {
				//Ignore exception
			}
		}
		
		return locnAcc;
	}
	
	protected final String extractProjectNameFromTemplate(Template template) {
		String projectName = null;
		List<String> projectNameList = template.get(KEY_H0102);
		if ((!CollectionUtils.isEmpty(projectNameList)) 
				&& (Numeral.ONE < projectNameList.size())) {
			projectName = projectNameList.get(Numeral.ONE);
		}
		return projectName;
	}
	
	protected final int extractNorthingIndex(final List<String> headers) throws TemplateProcessorException {
		int index = headers.indexOf(SL4ColumnHeaders.NORTHING_MGA.getCode());
		if (Numeral.NOT_FOUND == index) {
			index = headers.indexOf(SL4ColumnHeaders.NORTHING_AMG.getCode());
		}
		if (Numeral.NOT_FOUND == index) {
			throw new TemplateProcessorException("No Easting index in the header");
		}
		return index;
	}
	
	protected final int extractEastingIndex(final List<String> headers) throws TemplateProcessorException {
		int index = headers.indexOf(SL4ColumnHeaders.EASTING_MGA.getCode());
		if (Numeral.NOT_FOUND == index) {
			index = headers.indexOf(SL4ColumnHeaders.EASTING_AMG.getCode());
		}
		if (Numeral.NOT_FOUND == index) {
			throw new TemplateProcessorException("No Easting index in the header");
		}
		return index;
	}
}

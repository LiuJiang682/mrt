package au.gov.vic.ecodev.mrt.template.processor.updater.tables.sl4;

import java.util.Date;
import java.util.List;
import java.util.Map;

import au.gov.vic.ecodev.common.util.NullSafeCollections;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.dao.sl4.BoreHoleDao;
import au.gov.vic.ecodev.mrt.model.sl4.BoreHole;
import au.gov.vic.ecodev.mrt.template.fields.SL4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.updater.helper.DataExtractionHelper;
import au.gov.vic.ecodev.mrt.template.processor.updater.tables.index.finder.sl4.HoleIdIndexFinder;
import au.gov.vic.ecodev.utils.file.helper.FileNameExtractionHelper;

public class BoreHoleUpdater {
	
	private static final String DEFAULT_BH_AUTHORITY_CD_U = "U";

	private static final String DEFAULT_UNKNOWN = "UNK";

	private static final String KEY_H0201 = "H0201";

	private static final String KEY_H0200 = "H0200";

	private static final String DATE_FORMAT_DD_MMM_YY = "dd-MMM-yy";

	private final long sessionId;
	private final Template template;
	private final BoreHoleDao boreHoleDao;
	private final Map<String, Long> drillingCodes;
	
	private int holeIdIndex;
	private int mdDepthIndex;
	private int elevationKbIndex;
	private int drillingCodeIndex;
	private int azimuthMagIndex;
	private Date drillingStartDate;
	private Date drillingCompletionDate;
	private String fileName;
	
	public BoreHoleUpdater(long sessionId, Template template, BoreHoleDao boreHoleDao,
			Map<String, Long> drillingCodes) {
		this.sessionId = sessionId;
		this.template = template;
		this.boreHoleDao = boreHoleDao;
		this.drillingCodes = drillingCodes;
	}

	public void init(final List<Integer> mandatoryFieldsIndexList) throws TemplateProcessorException {
		List<String> headers = template.get(Strings.KEY_H1000);
		holeIdIndex = new HoleIdIndexFinder(headers).find();
		mdDepthIndex = new DataExtractionHelper(headers)
				.extractMandatoryFieldIndex(SL4ColumnHeaders.TOTAL_HOLE_DEPTH.getCode());
		mandatoryFieldsIndexList.add(mdDepthIndex);
		elevationKbIndex = new DataExtractionHelper(headers)
				.extractMandatoryFieldIndex(SL4ColumnHeaders.ELEVATION.getCode());
		mandatoryFieldsIndexList.add(elevationKbIndex);
		drillingCodeIndex = new DataExtractionHelper(headers)
				.extractMandatoryFieldIndex(SL4ColumnHeaders.DRILL_CODE.getCode());
		mandatoryFieldsIndexList.add(drillingCodeIndex);
		azimuthMagIndex = new DataExtractionHelper(headers)
				.extractMandatoryFieldIndex(SL4ColumnHeaders.AZIMUTH_MAG.getCode());
		mandatoryFieldsIndexList.add(azimuthMagIndex);
		drillingStartDate = new DataExtractionHelper(template.get(KEY_H0200))
				.extractDate(Numeral.ONE, DATE_FORMAT_DD_MMM_YY);
		drillingCompletionDate = new DataExtractionHelper(template.get(KEY_H0201))
				.extractDate(Numeral.ONE, DATE_FORMAT_DD_MMM_YY);
		fileName = new FileNameExtractionHelper(template, Strings.CURRENT_FILE_NAME)
				.doFileNameExtraction();
	}

	public void update(final List<String> dataRecordList, final int index) throws TemplateProcessorException {
		BoreHole boreHole = new BoreHole();
		boreHole.setLoaderId(sessionId);
		boreHole.setHoleId((String) new NullSafeCollections(dataRecordList).get(holeIdIndex));
		boreHole.setFileName(fileName);
		boreHole.setRowNumber(String.valueOf(index));
		boreHole.setBhAuthorityCd(DEFAULT_BH_AUTHORITY_CD_U);
		boreHole.setBhRegulationCd(DEFAULT_UNKNOWN);
		boreHole.setDillingDetailsId(getDillingDetailsId(dataRecordList));
		boreHole.setDrillingStartDate(drillingStartDate);
		boreHole.setDrillingCompletionDate(drillingCompletionDate);
		boreHole.setMdDepth(new DataExtractionHelper(dataRecordList).extractBigDecimal(mdDepthIndex));
		boreHole.setElevationKb(new DataExtractionHelper(dataRecordList)
				.extractBigDecimal(elevationKbIndex));
		boreHole.setAzimuthMag(new DataExtractionHelper(dataRecordList)
				.extractBigDecimal(azimuthMagIndex));
		boreHoleDao.updateOrSave(boreHole);
	}

	protected final long getDillingDetailsId(final List<String> dataRecordList) throws TemplateProcessorException {
		String drillingCode = (String) new NullSafeCollections(dataRecordList).get(drillingCodeIndex);
		if (null == drillingCode) {
			throw new TemplateProcessorException("No drilling code found in data record: " + dataRecordList);
		}
		Long drillingDetailsId = drillingCodes.get(drillingCode);
		if (null == drillingDetailsId) {
			throw new TemplateProcessorException("No drillingDetailsId found for drillingCode: " + drillingCode);
		}
		return drillingDetailsId;
	}

}

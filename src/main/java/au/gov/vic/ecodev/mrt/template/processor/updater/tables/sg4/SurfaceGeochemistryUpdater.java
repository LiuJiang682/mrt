package au.gov.vic.ecodev.mrt.template.processor.updater.tables.sg4;

import java.math.BigDecimal;
import java.util.List;

import au.gov.vic.ecodev.common.util.IDGenerator;
import au.gov.vic.ecodev.common.util.NullSafeCollections;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.dao.sg4.SurfaceGeochemistryDao;
import au.gov.vic.ecodev.mrt.model.sg4.SurfaceGeochemistry;
import au.gov.vic.ecodev.mrt.template.fields.Sg4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.MrtTemplateValue;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.model.TemplateValue;
import au.gov.vic.ecodev.mrt.template.processor.updater.helper.DataExtractionHelper;
import au.gov.vic.ecodev.mrt.template.processor.updater.helper.FileNameExtractionHelper;
import au.gov.vic.ecodev.mrt.template.processor.updater.helper.ProjectionZoneExtractionHelper;

public class SurfaceGeochemistryUpdater {

	private final SurfaceGeochemistryDao surfaceGeochemistryDao;
	private final long sessionId;
	private final Template template;
	
	private int sampleIdIndex;
	private int eastingIndex;
	private int northingIndex;
	private int sampleTypeIndex;
	private BigDecimal amgZone;
	private String fileName;
	
	public SurfaceGeochemistryUpdater(SurfaceGeochemistryDao surfaceGeochemistryDao, long sessionId,
			Template template) {
		this.surfaceGeochemistryDao = surfaceGeochemistryDao;
		this.sessionId = sessionId;
		this.template = template;
	}

	public void init(List<Integer> mandatoryFieldIndexList) throws TemplateProcessorException {
		List<String> headers = template.get(Strings.KEY_H1000);
		List<String> sampleIdName = template.get(Sg4ColumnHeaders.SAMPLE_ID.getCode());
		sampleIdIndex = new DataExtractionHelper(headers)
				.extractMandatoryFieldIndex(sampleIdName.get(Numeral.ZERO));
		mandatoryFieldIndexList.add(sampleIdIndex);
		eastingIndex = extractEastingIndex(headers);
		mandatoryFieldIndexList.add(eastingIndex);
		northingIndex = extractNorthingIndex(headers);
		mandatoryFieldIndexList.add(northingIndex);
		List<String> sampleTypeName = template.get(Sg4ColumnHeaders.SAMPLE_TYPE.getCode());
		sampleTypeIndex = new DataExtractionHelper(headers)
				.extractMandatoryFieldIndex(sampleTypeName.get(Numeral.ZERO));
		mandatoryFieldIndexList.add(sampleTypeIndex);
		amgZone = new ProjectionZoneExtractionHelper(template).extractAmgZoneFromTemplate();
		fileName = new FileNameExtractionHelper(template, Strings.CURRENT_FILE_NAME)
				.doFileNameExtraction();
	}

	public void update(final TemplateValue templateValue, final int index) {
		MrtTemplateValue mrtTemplateValue = (MrtTemplateValue)templateValue;
		List<String> dataRecordList = mrtTemplateValue.getDatas();
		SurfaceGeochemistry surfaceGeochemistry = new SurfaceGeochemistry();
		surfaceGeochemistry.setId(IDGenerator.getUIDAsAbsLongValue());
		surfaceGeochemistry.setLoaderId(sessionId);
		surfaceGeochemistry.setSampleId((String) new NullSafeCollections(dataRecordList)
				.get(sampleIdIndex));
		surfaceGeochemistry.setFileName(fileName);
		surfaceGeochemistry.setRowNumber(String.valueOf(index));
		surfaceGeochemistry.setEasting(new DataExtractionHelper(dataRecordList)
				.extractBigDecimal(eastingIndex));
		surfaceGeochemistry.setNorthing(new DataExtractionHelper(dataRecordList)
				.extractBigDecimal(northingIndex));
		surfaceGeochemistry.setSampleType((String) new NullSafeCollections(dataRecordList)
				.get(sampleTypeIndex));
		surfaceGeochemistry.setAmgZone(amgZone);
		surfaceGeochemistry.setIssueColumnIndex(mrtTemplateValue.getIssueColumnIndex());
		surfaceGeochemistryDao.updateOrSave(surfaceGeochemistry);
	}
	
	protected final int extractNorthingIndex(List<String> headers) throws TemplateProcessorException {
		int index = headers.indexOf(Sg4ColumnHeaders.NORTHING_MGA.getCode());
		if (Numeral.NOT_FOUND == index) {
			index = headers.indexOf(Sg4ColumnHeaders.NORTHING_AMG.getCode());
		}
		if (Numeral.NOT_FOUND == index) {
			throw new TemplateProcessorException("No Easting index in the header");
		}
		return index;
	}

	protected final int extractEastingIndex(final List<String> headers) throws TemplateProcessorException {
		int index = headers.indexOf(Sg4ColumnHeaders.EASTING_MGA.getCode());
		if (Numeral.NOT_FOUND == index) {
			index = headers.indexOf(Sg4ColumnHeaders.EASTING_AMG.getCode());
		}
		if (Numeral.NOT_FOUND == index) {
			throw new TemplateProcessorException("No Easting index in the header");
		}
		return index;
	}
}

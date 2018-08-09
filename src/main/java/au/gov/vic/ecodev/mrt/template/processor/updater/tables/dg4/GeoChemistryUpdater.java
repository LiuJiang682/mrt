package au.gov.vic.ecodev.mrt.template.processor.updater.tables.dg4;

import java.util.List;

import au.gov.vic.ecodev.common.util.IDGenerator;
import au.gov.vic.ecodev.common.util.NullSafeCollections;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.dao.dg4.GeoChemistryDao;
import au.gov.vic.ecodev.mrt.model.dg4.GeoChemistry;
import au.gov.vic.ecodev.mrt.template.fields.Dg4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.updater.helper.DataExtractionHelper;
import au.gov.vic.ecodev.mrt.template.processor.updater.helper.FileNameExtractionHelper;

public class GeoChemistryUpdater {

	private final GeoChemistryDao geoChemistryDao;
	private final long sessionId;
	private final Template template;
	
	private int holeIdIndex;
	private int sampleIdIndex;
	private int fromIndex;
	private int toIndex;
	private String fileName;
	
	public GeoChemistryUpdater(final GeoChemistryDao geoChemistryDao, long sessionId, 
			Template template) {
		this.geoChemistryDao = geoChemistryDao;
		this.sessionId = sessionId;
		this.template = template;
	}
	
	public void init(List<Integer> mandatoryFieldIndexList) throws TemplateProcessorException {
		List<String> headers = template.get(Strings.KEY_H1000);
		List<String> holeIdName = template.get(Dg4ColumnHeaders.HOLE_ID.getCode());
		holeIdIndex = new DataExtractionHelper(headers)
				.extractMandatoryFieldIndex(holeIdName.get(Numeral.ZERO));
		mandatoryFieldIndexList.add(holeIdIndex);
		List<String> sampleIdName = template.get(Dg4ColumnHeaders.SAMPLE_ID.getCode());
		sampleIdIndex = new DataExtractionHelper(headers)
				.extractMandatoryFieldIndex(sampleIdName.get(Numeral.ZERO));
		mandatoryFieldIndexList.add(sampleIdIndex);
		List<String> fromName = template.get(Dg4ColumnHeaders.FROM.getCode());
		fromIndex = new DataExtractionHelper(headers)
				.extractMandatoryFieldIndex(fromName.get(Numeral.ZERO));
		mandatoryFieldIndexList.add(fromIndex);
		List<String> toName = template.get(Dg4ColumnHeaders.TO.getCode());
		toIndex = new DataExtractionHelper(headers)
				.extractMandatoryFieldIndex(toName.get(Numeral.ZERO));
		mandatoryFieldIndexList.add(toIndex);
		fileName = new FileNameExtractionHelper(template, Strings.CURRENT_FILE_NAME)
				.doFileNameExtraction();
	}

	public void update(final List<String> dataRecordList, final int index) {
		GeoChemistry geoChemistry = new GeoChemistry();
		geoChemistry.setId(IDGenerator.getUID().longValue());
		geoChemistry.setLoaderId(sessionId);
		geoChemistry.setHoleId((String) new NullSafeCollections(dataRecordList)
				.get(holeIdIndex));
		geoChemistry.setSampleId((String) new NullSafeCollections(dataRecordList)
				.get(sampleIdIndex));
		geoChemistry.setFileName(fileName);
		geoChemistry.setRowNumber(String.valueOf(index));
		geoChemistry.setFrom(new DataExtractionHelper(dataRecordList)
				.extractBigDecimal(fromIndex));
		geoChemistry.setTo(new DataExtractionHelper(dataRecordList)
				.extractBigDecimal(toIndex));
		geoChemistryDao.updateOrSave(geoChemistry);
	}

}

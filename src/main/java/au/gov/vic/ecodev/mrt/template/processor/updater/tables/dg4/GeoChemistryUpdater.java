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

public class GeoChemistryUpdater {

	private final GeoChemistryDao geoChemistryDao;
	private final long sessionId;
	private final Template template;
	
	private int holeIdIndex;
	private int sampleIdIndex;
	private int fromIndex;
	private int toIndex;
	private int drillCodeIndex;
	
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
		List<String> drillCodeName = template.get(Dg4ColumnHeaders.DRILL_CODE.getCode());
		drillCodeIndex = new DataExtractionHelper(headers)
				.extractMandatoryFieldIndex(drillCodeName.get(Numeral.ZERO));
		mandatoryFieldIndexList.add(drillCodeIndex);
	}

	public void update(List<String> dataRecordList) {
		GeoChemistry geoChemistry = new GeoChemistry();
		geoChemistry.setId(IDGenerator.getUID().longValue());
		geoChemistry.setLoaderId(sessionId);
		geoChemistry.setHoleId((String) new NullSafeCollections(dataRecordList)
				.get(holeIdIndex));
		geoChemistry.setSampleId((String) new NullSafeCollections(dataRecordList)
				.get(sampleIdIndex));
		geoChemistry.setFrom(new DataExtractionHelper(dataRecordList)
				.extractBigDecimal(fromIndex));
		geoChemistry.setTo(new DataExtractionHelper(dataRecordList)
				.extractBigDecimal(toIndex));
		geoChemistry.setDrillCode((String) new NullSafeCollections(dataRecordList)
				.get(drillCodeIndex));
		geoChemistryDao.updateOrSave(geoChemistry);
	}

}
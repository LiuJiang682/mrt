package au.gov.vic.ecodev.mrt.template.processor.updater.tables.dl4;

import java.util.List;

import au.gov.vic.ecodev.common.util.IDGenerator;
import au.gov.vic.ecodev.common.util.NullSafeCollections;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.dao.dl4.LithologyDao;
import au.gov.vic.ecodev.mrt.model.dl4.Lithology;
import au.gov.vic.ecodev.mrt.template.fields.Dl4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.updater.helper.DataExtractionHelper;
import au.gov.vic.ecodev.mrt.template.processor.updater.helper.FileNameExtractionHelper;

public class LithologyUpdater {

	private final LithologyDao lithologyDao;
	private final long sessionId;
	private final Template template;

	private int holeIdIndex;
	private int depthFromIndex;
	private String fileName;

	public LithologyUpdater(LithologyDao lithologyDao, long sessionId, Template template) {
		this.lithologyDao = lithologyDao;
		this.sessionId = sessionId;
		this.template = template;
	}

	public void init(List<Integer> mandatoryFieldIndexList) throws TemplateProcessorException {
		List<String> headers = template.get(Strings.KEY_H1000);
		List<String> holeIdName = template.get(Dl4ColumnHeaders.HOLE_ID.getCode());
		holeIdIndex = new DataExtractionHelper(headers)
				.extractMandatoryFieldIndex(holeIdName.get(Numeral.ZERO));
		mandatoryFieldIndexList.add(holeIdIndex);
		List<String> depthFromName = template.get(Dl4ColumnHeaders.DEPTH_FROM.getCode());
		depthFromIndex = new DataExtractionHelper(headers)
				.extractMandatoryFieldIndex(depthFromName.get(Numeral.ZERO));
		mandatoryFieldIndexList.add(depthFromIndex);
		this.fileName = new FileNameExtractionHelper(template, Strings.CURRENT_FILE_NAME)
				.doFileNameExtraction();
	}

	public void update(List<String> dataRecordList) {
		Lithology lithology = new Lithology();
		lithology.setId(IDGenerator.getUID().longValue());
		lithology.setLoaderId(sessionId);
		lithology.setHoleId((String) new NullSafeCollections(dataRecordList).get(holeIdIndex));
		lithology.setFileName(fileName);
		lithology.setDepthFrom(new DataExtractionHelper(dataRecordList)
				.extractBigDecimal(depthFromIndex));
		lithologyDao.updateOrSave(lithology);
	}
}

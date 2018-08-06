package au.gov.vic.ecodev.mrt.template.processor.updater.tables.ds4;

import java.util.List;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.common.util.IDGenerator;
import au.gov.vic.ecodev.common.util.NullSafeCollections;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.dao.ds4.DownHoleDao;
import au.gov.vic.ecodev.mrt.model.ds4.DownHole;
import au.gov.vic.ecodev.mrt.template.fields.Ds4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.updater.helper.DataExtractionHelper;
import au.gov.vic.ecodev.mrt.template.processor.updater.helper.FileNameExtractionHelper;

public class DownHoleUpdater {

	private final DownHoleDao downHoleDao;
	private final long sessionId;
	private final Template template;

	private int holeIdIndex;
	private int surveyedDepthIndex;
	private int azimuthMagIndex = Numeral.NOT_FOUND;
	private int dipIndex;
	private int azimuthTrueIndex = Numeral.NOT_FOUND;
	private String fileName;

	public DownHoleUpdater(DownHoleDao downHoleDao, long sessionId, Template template) {
		this.downHoleDao = downHoleDao;
		this.sessionId = sessionId;
		this.template = template;
	}

	public void update(List<String> dataRecordList) {
		DownHole downHole = new DownHole();
		downHole.setId(IDGenerator.getUID().longValue());
		downHole.setLoaderId(sessionId);
		downHole.setHoleId((String) new NullSafeCollections(dataRecordList).get(holeIdIndex));
		downHole.setFileName(fileName);
		downHole.setSurveyedDepth(new DataExtractionHelper(dataRecordList)
				.extractBigDecimal(surveyedDepthIndex));
		if (Numeral.NOT_FOUND != azimuthMagIndex) {
			downHole.setAzimuthMag(new DataExtractionHelper(dataRecordList)
					.extractBigDecimal(azimuthMagIndex));
		}
		downHole.setDip(new DataExtractionHelper(dataRecordList).extractBigDecimal(dipIndex));
		if (Numeral.NOT_FOUND != azimuthTrueIndex) {
			downHole.setAzimuthTrue(new DataExtractionHelper(dataRecordList)
					.extractBigDecimal(azimuthTrueIndex));
		}
		downHoleDao.updateOrSave(downHole);
	}

	public void init(List<Integer> mandatoryFieldIndexList) throws TemplateProcessorException {
		List<String> headers = template.get(Strings.KEY_H1000);
		List<String> holeIdName = template.get(Ds4ColumnHeaders.HOLE_ID.getCode());
		holeIdIndex = new DataExtractionHelper(headers).extractMandatoryFieldIndex(holeIdName
				.get(Numeral.ZERO));
		mandatoryFieldIndexList.add(holeIdIndex);
		List<String> surveyedDepthName = template.get(Ds4ColumnHeaders.SURVEYED_DEPTH.getCode());
		surveyedDepthIndex = new DataExtractionHelper(headers)
				.extractMandatoryFieldIndex(surveyedDepthName.get(Numeral.ZERO));
		mandatoryFieldIndexList.add(surveyedDepthIndex);
		List<String> azimuthMagName = template.get(Ds4ColumnHeaders.AZIMUTH_MAG.getCode());
		if (!CollectionUtils.isEmpty(azimuthMagName)) {
			azimuthMagIndex = new DataExtractionHelper(headers)
					.extractMandatoryFieldIndex(azimuthMagName.get(Numeral.ZERO));
			mandatoryFieldIndexList.add(azimuthMagIndex);
		}
		List<String> dipName = template.get(Ds4ColumnHeaders.DIP.getCode());
		dipIndex = new DataExtractionHelper(headers)
				.extractMandatoryFieldIndex(dipName.get(Numeral.ZERO));
		mandatoryFieldIndexList.add(dipIndex);
		List<String> azimuthTrueName = template.get(Strings.AZIMUTH_TRUE);
		if (!CollectionUtils.isEmpty(azimuthTrueName)) {
			azimuthTrueIndex = new DataExtractionHelper(headers)
					.extractMandatoryFieldIndex(azimuthTrueName.get(Numeral.ZERO));
			mandatoryFieldIndexList.add(azimuthTrueIndex);
		}
		if ((Numeral.NOT_FOUND == azimuthMagIndex) && (Numeral.NOT_FOUND == azimuthTrueIndex)) {
			throw new TemplateProcessorException("Missing Azimuth_MAG or Azimuth_TRUE");
		}
		fileName = new FileNameExtractionHelper(template, Strings.CURRENT_FILE_NAME)
				.doFileNameExtraction();
	}

}

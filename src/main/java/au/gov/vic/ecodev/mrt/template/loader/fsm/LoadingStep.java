package au.gov.vic.ecodev.mrt.template.loader.fsm;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;

public enum LoadingStep {

	SCAN_DIR(au.gov.vic.ecodev.mrt.constants.Constants.Numeral.ZERO, 
			au.gov.vic.ecodev.mrt.template.loader.fsm.ScanDirectoryState.class), 
	EXTRACT_TEMPLATE_NAME(au.gov.vic.ecodev.mrt.constants.Constants.Numeral.ONE, 
			au.gov.vic.ecodev.mrt.template.loader.fsm.ExtractTemplateNameState.class), 
	RETRIEVE_TEMPLATE(Numeral.TWO, au.gov.vic.ecodev.mrt.template.loader.fsm.RetrieveTemplateState.class),
	UNZIP_ZIP_FILE(Numeral.THREE, au.gov.vic.ecodev.mrt.template.loader.fsm.UnzipZipFileState.class),
	PROCESS_TEMPLATE_FILE(Numeral.FOUR, au.gov.vic.ecodev.mrt.template.loader.fsm.ProcessTemplateFileState.class),
	GENERATE_STATUS_LOG(Numeral.FIVE, au.gov.vic.ecodev.mrt.template.loader.fsm.GenerateStatusLogState.class),
	MOVE_FILE_TO_NEXT_STAGE(Numeral.SIX, au.gov.vic.ecodev.mrt.template.loader.fsm.MoveFileToNextStageState.class),
	NOTIFY_USER(Numeral.SEVEN, au.gov.vic.ecodev.mrt.template.loader.fsm.NotifyUserState.class);
	
	private final Class<?> stateClass;
	private final int id;
	
	LoadingStep(final int id, final Class<?> stateClass) {
		this.id = id;
		this.stateClass = stateClass;
	}
	
	public Class<?> getStateClass() {
		return stateClass;
	}
	
	public int getId() {
		return id;
	}

	public LoaderState getState() throws Exception {
		return (LoaderState) getStateClass().newInstance();
	}

	public LoadingStep getNextStep() {
		int nextId = this.id + 1;
		
		for (LoadingStep next : LoadingStep.values()) {
			if (next.getId() == nextId) {
				return next;
			}
		}
		
		return null;
	}
}

package au.gov.vic.ecodev.mrt.template.loader;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.gov.vic.ecodev.mrt.template.loader.fsm.LoaderState;
import au.gov.vic.ecodev.mrt.template.loader.fsm.TemplateLoaderStateMachineContext;

@Service("templateLoader")
public class TemplateLoaderImpl implements TemplateLoader {

	private static final Logger LOGGER = Logger.getLogger(TemplateLoaderImpl.class);
	
	private TemplateLoaderStateMachineContext templateLoaderStateMachineContext;
	
	@Autowired
	public TemplateLoaderImpl(final TemplateLoaderStateMachineContext templateLoaderStateMachineContext) {
		if (null == templateLoaderStateMachineContext) {
			throw new IllegalArgumentException("Parameter templateLoaderStateMachineContext cannot be null!");
		}
		this.templateLoaderStateMachineContext = templateLoaderStateMachineContext;
	}
	
	@Override
	public void load() throws Exception {
		LOGGER.info("Commencing loading...");

		LoaderState state;
		while(null != (state = templateLoaderStateMachineContext.getNextState())) {
			LOGGER.info("Next state: " + state);
			state.on(templateLoaderStateMachineContext); 
		}
		
		LOGGER.info("Completed load");
		templateLoaderStateMachineContext.resetCurrentState();
	}

}

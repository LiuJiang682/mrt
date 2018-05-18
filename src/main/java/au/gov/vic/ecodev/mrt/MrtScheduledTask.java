package au.gov.vic.ecodev.mrt;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import au.gov.vic.ecodev.mrt.template.loader.TemplateLoader;
import au.gov.vic.ecodev.mrt.template.processor.classloader.MrtProcessorClassLoader;

@Component
public class MrtScheduledTask {

	private static final Logger LOGGER = Logger.getLogger(MrtScheduledTask.class);
	
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	@Autowired
	private TemplateLoader templateLoader;
	
	@Autowired
	private MrtProcessorClassLoader mrtProcessorClassLoader;
	
	@PostConstruct
	private void init() throws Exception {
		mrtProcessorClassLoader.load();
	}
	
//	@Scheduled(fixedRate = 2000)
	@Scheduled(fixedRateString = "${mrt.scan.dir.rate}")
    public void scheduleTaskWithFixedRate() throws Exception {
        LOGGER.info("Fixed Rate Task :: Execution Time - " + dateTimeFormatter.format(LocalDateTime.now()) );
        templateLoader.load();
	}
}

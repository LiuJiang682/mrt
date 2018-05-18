package au.gov.vic.ecodev.mrt;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import au.gov.vic.ecodev.mrt.template.loader.TemplateLoader;
import au.gov.vic.ecodev.mrt.template.processor.classloader.MrtProcessorClassLoader;

@SpringBootApplication
@EnableScheduling
//public class Mrt implements CommandLineRunner {
public class Mrt {

//	private static final Logger LOGGER = Logger.getLogger(Mrt.class);
//	
//	@Autowired
//	private TemplateLoader templateLoader;
//	
//	@Autowired
//	private MrtProcessorClassLoader mrtProcessorClassLoader;
	
	public static void main(String[] args) {
		SpringApplication.run(Mrt.class, args);
//		SpringApplication app = new SpringApplication(Mrt.class);
//		app.setBannerMode(Banner.Mode.CONSOLE);
//		app.run(args);
	}

//	@Override
//	public void run(String... arg0) throws Exception {
//		LOGGER.info("Starting the MRT Loader...");
//		mrtProcessorClassLoader.load();
//		templateLoader.load();
//	}
}

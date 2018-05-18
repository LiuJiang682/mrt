package au.gov.vic.ecodev.mrt.web;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	private static final Logger LOGGER = Logger.getLogger(HomeController.class);
	
    @RequestMapping(value = "/")
    public String index() {
    	LOGGER.info("Inside HomeController");
        return "index";
    }

}

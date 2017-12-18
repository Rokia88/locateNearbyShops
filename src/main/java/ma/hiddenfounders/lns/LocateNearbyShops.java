package ma.hiddenfounders.lns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;




/**
 * Main class 
 * @author rokia
 * @version 1.0
 */
@SpringBootApplication(scanBasePackages={"ma.hiddenfounders.lns"})
@ImportResource(value= { 
	    "classpath:daoContext.xml",
	    "classpath:serviceContext.xml"
	    })
@PropertySource("classpath:application.properties")
public class LocateNearbyShops  extends SpringBootServletInitializer {
	
	/**
	 * bootstrap spring web application
	 * @param args
	 */
	public static void main(String[] args) {
		
	SpringApplication.run(LocateNearbyShops.class, args);
	
	}
	

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(LocateNearbyShops.class);
	}

	
	
    
}

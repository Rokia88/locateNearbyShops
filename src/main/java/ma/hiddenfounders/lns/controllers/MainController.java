package ma.hiddenfounders.lns.controllers;


import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ma.hiddenfounders.lns.dao.mongo.classes.Location;
import ma.hiddenfounders.lns.exceptions.ApplicationExceptions;
import ma.hiddenfounders.lns.exceptions.BusinessExceptions;
import ma.hiddenfounders.lns.services.MainService;


@Controller
public class MainController {
	
	private static final Logger logger = LogManager.getLogger(MainController.class);
	
	
	@Autowired
	private MainService mainService;
	
	
	@RequestMapping(value = "/remove", method=RequestMethod.GET)
	public String removeShops(@RequestParam("id") String idShop) {
		
		logger.info("Removed shop Id:"+idShop, this);
		//remove shop
		 try {
			mainService.remove(idShop);
		} catch (BusinessExceptions e) {
			
			return "applicationErrors";
		}
	
		return "preferredShops";
	}
	
	@RequestMapping(value = "/dislike", method=RequestMethod.GET)
	public String dislikeShops(@RequestParam("id") String idShop) {
		
		logger.info("Disliked shop Id:"+idShop, this);
		
		//dislike shop
		 try {
			mainService.dislike(idShop);
		} catch (BusinessExceptions e) {
			
			return "applicationErrors";
		}
	
		return "nearbyShops";
	}
	
	
	@RequestMapping(value = "/like", method=RequestMethod.GET)
	public String likeShops(@RequestParam("id") String idShop) {
		
		logger.info("Liked shop Id:"+idShop, this);
		//like shop
		 try {
			mainService.like(idShop);
		} catch (BusinessExceptions e) {
			
			return "applicationErrors";
		}
	
		return "nearbyShops";
	}
	
	@RequestMapping(value = "/", method=RequestMethod.GET)
	public String getCurrentLocation(Map<String, Object> model) {
		
		logger.info("Current location", this);
		
		logger.info("session timout:"+mainService.getSessionTimeout(), this);
	
		model.put("location", new Location());
			
		return "index";
	}
	
	
	@RequestMapping( value="/nearbyShops", method=RequestMethod.POST)
	public String displayNearByShops(@ModelAttribute("location") Location location,BindingResult result) {
		
		
		//get current location  
		
		logger.info("get Current position X:"+location.getX(), this);
		logger.info("get Current position Y:"+location.getY(), this);
		
		location.setX(-6.81134);
		location.setY(33.95564);
			
		//get shops near location 
		try {
			mainService.getNearbyShops(location);
		} catch (ApplicationExceptions e) {
			
			return "applicationErrors";
			
		}
		
		return "nearbyShops";
	}
	
	@RequestMapping( value="/displayShops", method=RequestMethod.GET)
	public String displayNearByShops() {
		
		logger.info("displayNearByShops() direct link:", this);
		
		mainService.getNearbyShops_();
		
		return "nearbyShops";
	}
	
	@RequestMapping(value = "/myPreferredShops", method = RequestMethod.GET)
	public String displayPreferredShops() {
		
		logger.info("displayPreferredShops() direct link:", this);
		
		mainService.getPreferredShops();
	
		
		return "preferredShops";
	}
	

}

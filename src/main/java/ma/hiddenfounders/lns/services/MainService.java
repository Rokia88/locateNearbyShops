package ma.hiddenfounders.lns.services;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import ma.hiddenfounders.lns.dao.mongo.ShopsRepository;
import ma.hiddenfounders.lns.dao.mongo.classes.Location;
import ma.hiddenfounders.lns.dao.mongo.classes.Shops;
import ma.hiddenfounders.lns.exceptions.ApplicationExceptions;
import ma.hiddenfounders.lns.exceptions.BusinessExceptions;

/**
 * Services main class
 * @author rokia
 * @version 1.0
 */
@Component
@Service
public class MainService {
	
	private static final Logger logger = LogManager.getLogger(MainService.class);
	
	@Autowired 
	private HttpSession httpSession;
	
	private ShopsRepository shopsRepository;
	
	private List<Shops> nearbyShops;
	
	private List<Shops> preferredShops;
	
	/**
	 * Constructor 
	 * @param shopsRepository used to interface with DAO layer  
	 */
	@Autowired
	public MainService(ShopsRepository shopsRepository) {
		
		logger.info("Constructor Main Service", this);
		
		this.shopsRepository = shopsRepository;
		this.nearbyShops = new LinkedList<Shops>();
		this.preferredShops = new LinkedList<Shops>();
	}
	
	/**
	 * get nearby shops from current location
	 * @param currentlocation 
	 * @throws BusinessExceptions
	 */
	public void getNearbyShops(Location currentlocation) throws BusinessExceptions {
				
		//we truncate so to comply with location values stored in the database
		currentlocation.truncate();
		
		logger.info("current position x:"+currentlocation.getX()+",y:"+currentlocation.getY(), this);
		
		//retreive the "nearbyShops" attribute from the current session if it is present
		if(httpSession.getAttribute("nearbyShops") != null)
		{
			logger.info("getNearbyShops: current session contains the nearbyShops attribute", this);
			nearbyShops = (LinkedList<Shops>) httpSession.getAttribute("nearbyShops");
		}
		
		//otherwise, call the DAO layer to get the closest shops
		else if (httpSession.getAttribute("nearbyShops") == null)
		{
			logger.info("getNearbyShops: current session does not contain the nearbyShops attribute", this);
			
			httpSession.setAttribute("currentlocation", currentlocation);
			
			
			try {
				Iterator<GeoResult<Shops>> it = shopsRepository.getNearbyShops(currentlocation).iterator();			
				nearbyShops.clear();			
				while(it.hasNext()) {
					nearbyShops.add(it.next().getContent());
				}		
				//add the shops and their total number to the current session
				httpSession.setAttribute("nearbyShops", nearbyShops);
				httpSession.setAttribute("totalshops", nearbyShops.size());
				logger.info("number of nearby shops:"+nearbyShops.size(), this);
				
			} catch (ApplicationExceptions e) {			
				throw new BusinessExceptions("something goes wrong with getNearbyShops method");
			}	
			
		}		
	}
	
	/**
	 * set session timout to 2H
	 */
	public void setSessionTimeout() {
		 
		httpSession.setMaxInactiveInterval(7200);
	}
	
	/**
	 * display the value of the session timeout
	 * @return MaxInactiveInterval
	 */
	public int getSessionTimeout() {
		 
		return httpSession.getMaxInactiveInterval();
	}
	
	/**
	 * add the liked shop to preferred shops and remove it from nearby shops during the session lifetime (i.e. 2H)
	 * @param idShop 
	 * @throws BusinessExceptions
	 */
	public void like(String idShop) throws BusinessExceptions {
		
		try {			
			Shops shop = shopsRepository.findById(idShop);			
			logger.info("Liked Shop name and id:"+shop.getId()+","+shop.getName(), this);
			
			//used to restore the shop when it is removed from preferred shops
			int indexLikedShops = nearbyShops.indexOf(shop);
			
			preferredShops.add(shop);		
			nearbyShops.remove(shop);			
			logger.info("new number of nearby shops:"+nearbyShops.size(), this);
			logger.info("new number of preferred shops:"+preferredShops.size(), this);
			
			httpSession.setAttribute("nearbyShops", nearbyShops);
			httpSession.setAttribute("totalshops", nearbyShops.size());
			httpSession.setAttribute("preferredShops", preferredShops);
			httpSession.setAttribute("totalPrefShops", preferredShops.size());
			httpSession.setAttribute(idShop, indexLikedShops);
		} catch (ApplicationExceptions  e) {		
			throw new BusinessExceptions("something goes wrong with like method");
		}
	}
	
	/**
	 * remove the disliked shop from nearby shops during the session lifetime (i.e. 2H)
	 * @param idShop
	 * @throws BusinessExceptions
	 */
	public void dislike(String idShop) throws BusinessExceptions {
		
		try {
			Shops shop = shopsRepository.findById(idShop);		
			logger.info("Disliked Shop name and id:"+shop.getId()+","+shop.getName(), this);
			
			nearbyShops.remove(shop);			
			logger.info("new number of nearby shops:"+nearbyShops.size(), this);
				
			httpSession.setAttribute("nearbyShops", nearbyShops);
			httpSession.setAttribute("totalshops", nearbyShops.size());
		} catch (ApplicationExceptions  e) {			
			throw new BusinessExceptions("something goes wrong with dislike method");
		}		
	}
	
	/**
	 * return the shop removed from preferred shops to nearby shops
	 * @param idShop
	 * @throws BusinessExceptions
	 */
    public void remove(String idShop) throws BusinessExceptions {
    		
    	try {
    		Shops shop = shopsRepository.findById(idShop); 		
    		logger.info("Removed Shop name and id:"+shop.getId()+","+shop.getName(), this);
    		
    		preferredShops.remove(shop);  		
    		nearbyShops.add((Integer)(httpSession.getAttribute(shop.getId())), shop);
    		
    		logger.info("new number of nearby shops:"+nearbyShops.size(), this);
    		logger.info("new number of preferred shops:"+preferredShops.size(), this);
    
    		httpSession.setAttribute("nearbyShops", nearbyShops);
    		httpSession.setAttribute("totalshops", nearbyShops.size());
    		httpSession.setAttribute("preferredShops", preferredShops);
    		httpSession.setAttribute("totalPrefShops", preferredShops.size());		
		} catch (ApplicationExceptions  e) {		
			throw new BusinessExceptions("something goes wrong with remove method");
		} 	
	}

    /**
     * when the user clicks on the link "Nearby Shops", get nearby shops from current session,
     * add nearby shops to the current session if it does not contain it
     */
	public void getNearbyShops_() {
		
		if(httpSession.getAttribute("nearbyShops")==null)
		{			
			logger.info("current session does not contain the nearbyShops attribute ", this);
			
			httpSession.setAttribute("nearbyShops", nearbyShops);
			httpSession.setAttribute("totalshops", nearbyShops.size());
		}
	}

	/**
	 *  when the user clicks on the link "My Preferred Shops", get preferred shops from current session
	 *  add preferred shops to the current session if it does not contain it
	 */
	public void getPreferredShops() {
		
		if(httpSession.getAttribute("preferredShops")==null)
		{
			preferredShops.clear();
			
			logger.info("current session does not contain the preferredShops attribute ", this);
			
			httpSession.setAttribute("preferredShops", preferredShops);
			httpSession.setAttribute("totalPrefShops", preferredShops.size());
		}		
	}
    
}

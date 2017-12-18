package ma.hiddenfounders.lns.services;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import ma.hiddenfounders.lns.dao.mongo.ShopsRepository;
import ma.hiddenfounders.lns.dao.mongo.classes.Location;
import ma.hiddenfounders.lns.dao.mongo.classes.Shops;

@Component
@Service
//@Scope("prototype")
public class MainService {
	
	private static final Logger logger = LogManager.getLogger(MainService.class);
	
	 @Autowired 
	 private HttpSession httpSession;
	
	private ShopsRepository shopsRepository;
	
	private List<Shops> nearbyShops;
	
	private List<Shops> preferredShops;
	
	private Point currentlocation;

	@Autowired
	public MainService(ShopsRepository shopsRepository) {
		
		this.shopsRepository = shopsRepository;
		this.nearbyShops = new LinkedList<Shops>();
		this.preferredShops = new LinkedList<Shops>();
	}
	
	public void getNearbyShops(Location location) {
		
		logger.info("session timout:"+getSessionTimeout(), this);
		
		location.truncate();
		
		Point previouslocation = (Point) httpSession.getAttribute("currentlocation");
		
		currentlocation = new Point(location.getX(),location.getY());
		
		if(currentlocation.equals(previouslocation))
		{
			logger.info("current location is same as previous one", this);
			nearbyShops = (LinkedList<Shops>) httpSession.getAttribute("nearbyShops");
		}
		
		else if (!(currentlocation.equals(previouslocation))|| previouslocation == null)
		{
			logger.info("current location differs from previous one or session is new", this);
			
			httpSession.setAttribute("currentlocation", currentlocation);
			
			Iterator<GeoResult<Shops>> it = shopsRepository.getNearbyShops(currentlocation).iterator();
			
			while(it.hasNext()) {
				nearbyShops.add(it.next().getContent());
			}
			
			httpSession.setAttribute("nearbyShops", nearbyShops);
			httpSession.setAttribute("totalshops", nearbyShops.size());
		}
		
		
		
		logger.info("number of nearby shops:"+nearbyShops.size(), this);
		logger.info("current poistion x:"+location.getX(), this);
		logger.info("current poistion y:"+location.getY(), this);
		
		
	}
	
	public void setSessionTimeout() {
		httpSession.setMaxInactiveInterval(600);
	}
	
	public int getSessionTimeout() {
		return httpSession.getMaxInactiveInterval();
	}
	
	public void like(String idShop) {
		
		logger.info("session timout:"+getSessionTimeout(), this);
		
		Shops shop = shopsRepository.findById(idShop);
		
		logger.info("Liked Shop name and id:"+shop.getId()+","+shop.getName(), this);
		
		preferredShops.add(shop);
		
		nearbyShops.remove(shop);
		httpSession.removeAttribute("nearbyShops");
		httpSession.removeAttribute("totalshops");
		logger.info("new number of nearby shops:"+nearbyShops.size(), this);
		logger.info("new number of preferred shops:"+preferredShops.size(), this);
		
		if(httpSession.getAttribute("preferredShops")!=null)
		{
			logger.info("current session already contains the preferredShops attribute ", this);
			
			httpSession.removeAttribute("preferredShops");
			httpSession.removeAttribute("totalPrefShops");
		}
		httpSession.setAttribute("nearbyShops", nearbyShops);
		httpSession.setAttribute("totalshops", nearbyShops.size());
		httpSession.setAttribute("preferredShops", preferredShops);
		httpSession.setAttribute("totalPrefShops", preferredShops.size());
	}
	
	public void dislike(String idShop) {
		
		logger.info("session timout:"+getSessionTimeout(), this);
		
		Shops shop = shopsRepository.findById(idShop);
		
		logger.info("Liked Shop name and id:"+shop.getId()+","+shop.getName(), this);
		
		nearbyShops.remove(shop);
		
		logger.info("new number of nearby shops:"+nearbyShops.size(), this);
			
		httpSession.removeAttribute("nearbyShops");
		httpSession.removeAttribute("totalshops");
		httpSession.setAttribute("nearbyShops", nearbyShops);
		httpSession.setAttribute("totalshops", nearbyShops.size());
		
	}
	
    public void remove(String idShop) {
    	
    	logger.info("session timout:"+getSessionTimeout(), this);
		
    	Shops shop = shopsRepository.findById(idShop);
		
		logger.info("Removed Shop name and id:"+shop.getId()+","+shop.getName(), this);
		
		preferredShops.remove(shop);
		
		nearbyShops.add(shop);
		
		logger.info("new number of nearby shops:"+nearbyShops.size(), this);
		logger.info("new number of preferred shops:"+preferredShops.size(), this);
		
		httpSession.removeAttribute("nearbyShops");
		httpSession.removeAttribute("totalshops");
		httpSession.removeAttribute("preferredShops");
		httpSession.removeAttribute("totalPrefShops");
		
		httpSession.setAttribute("nearbyShops", nearbyShops);
		httpSession.setAttribute("totalshops", nearbyShops.size());
		httpSession.setAttribute("preferredShops", preferredShops);
		httpSession.setAttribute("totalPrefShops", preferredShops.size());
		
	}

	public void getNearbyShops_() {
		
		if(httpSession.getAttribute("nearbyShops")==null)
		{
			logger.info("current session already contains the preferredShops attribute ", this);
			
			httpSession.setAttribute("nearbyShops", nearbyShops);
			httpSession.setAttribute("otalshops", nearbyShops.size());
		}
	}

	public void getPreferredShops() {
		
		if(httpSession.getAttribute("preferredShops")==null)
		{
			logger.info("current session already contains the preferredShops attribute ", this);
			
			httpSession.setAttribute("preferredShops", preferredShops);
			httpSession.setAttribute("totalPrefShops", preferredShops.size());
		}
		
	}
    
    
	

}

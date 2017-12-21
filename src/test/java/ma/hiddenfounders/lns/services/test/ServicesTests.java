package ma.hiddenfounders.lns.services.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ma.hiddenfounders.lns.dao.mongo.classes.Location;
import ma.hiddenfounders.lns.exceptions.BusinessExceptions;
import ma.hiddenfounders.lns.services.MainService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServicesTests {
	
	@Autowired 
	 private HttpSession httpSession;
	
	@Autowired
	private MainService mainService;
	

	
	@Test
    public void testallservice() throws BusinessExceptions
    {
		httpSession.setMaxInactiveInterval(7200);
		
		Location location = new Location();
		location.setX(-6.81134);
		location.setY(33.95564);
		//nearby shops
		mainService.getNearbyShops(location);
		assertNotNull(httpSession.getAttribute("currentlocation"));
		assertNotNull(httpSession.getAttribute("nearbyShops"));
		assertEquals(httpSession.getAttribute("totalshops"), 49);
		//liked shops
		mainService.like("5a0c6711fb3aac66aafe26c4");
		assertNotNull(httpSession.getAttribute("preferredShops"));
		assertEquals(httpSession.getAttribute("totalshops"), 48);
		assertEquals(httpSession.getAttribute("totalPrefShops"), 1);
		//dislike shop
		mainService.dislike("5a0c6b1dfd3eb67969316d5c");
		assertEquals(httpSession.getAttribute("totalshops"), 47);
		assertEquals(httpSession.getAttribute("totalPrefShops"), 1);
		//remove shop
		mainService.remove("5a0c6711fb3aac66aafe26c4");
		assertEquals(httpSession.getAttribute("totalshops"), 48);
		assertEquals(httpSession.getAttribute("totalPrefShops"), 0);
		
		//invalidate session
		httpSession.invalidate();
		
		assertEquals(httpSession.getAttribute("currentlocation"),null);
		assertEquals(httpSession.getAttribute("nearbyShops"),null);
		assertEquals(httpSession.getAttribute("totalshops"), null);
		assertEquals(httpSession.getAttribute("totalPrefShops"), null);
		assertEquals(httpSession.getAttribute("preferredShops"), null);
    }
	
	
}

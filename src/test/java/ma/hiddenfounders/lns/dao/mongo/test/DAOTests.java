package ma.hiddenfounders.lns.dao.mongo.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit4.SpringRunner;

import ma.hiddenfounders.lns.dao.mongo.ShopsRepository;
import ma.hiddenfounders.lns.dao.mongo.classes.Shops;
import ma.hiddenfounders.lns.exceptions.ApplicationExceptions;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DAOTests {
	
	
	@Autowired
	private ShopsRepository shopsRepository;
	 
	    
	  

    @Test
    public void testfindByName()
    {
    
        Shops shopA = shopsRepository.findByName("Gushkool");
        assertNotNull(shopA);
        assertEquals("Rabat", shopA.getCity());
        
    }
    
    
    @Test
    public void testgetNearbyShops() throws ApplicationExceptions
    {
   
	Point location = new Point(-6.81134, 33.95564);
	GeoResults<Shops> shops= shopsRepository.getNearbyShops(location);
	assertEquals(49,shops.getContent().size());
	Shops shopA = shops.getContent().get(0).getContent();
	assertEquals("5a0c6711fb3aac66aafe26c4", shopA.getId());
	
   }
    
    
    @Test
    public void testfindById() throws ApplicationExceptions
    {
    
        Shops shopA = shopsRepository.findById("5a0c6711fb3aac66aafe26c4");
        assertNotNull(shopA);
       
    }
    
   

 
}

		
	


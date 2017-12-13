package ma.hiddenfounders.lns.dao.mongo.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit4.SpringRunner;

import ma.hiddenfounders.lns.dao.mongo.ShopsRepositoryCustom;
import ma.hiddenfounders.lns.dao.mongo.classes.Shops;
import ma.hiddenfounders.lns.exceptions.DAOExceptions;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DAOTests {
	
	 
	private ConfigurableApplicationContext context;
	
	@Autowired
	private ShopsRepositoryCustom shopsRepository;
	 
	 @Before
	 public void testconnect() throws DAOExceptions
	 {
		 context = new ClassPathXmlApplicationContext("applicationContext.xml");
		 shopsRepository  =  context.getBean(ShopsRepositoryCustom.class);
	 }
	    
	  

    @Test
    public void testfindByName()
    {
    
        Shops shopA = shopsRepository.findByName("Gushkool");
        assertNotNull(shopA);
        assertEquals("Rabat", shopA.getCity());
        
    }
    
    
    @Test
    public void testgetNearbyShops()
    {
   
	Point location = new Point(-6.81134, 33.95564);
	GeoResults<Shops> shops= shopsRepository.getNearbyShops(location);
	assertEquals(49,shops.getContent().size());
	Shops shopA = shops.getContent().get(0).getContent();
	assertEquals("5a0c6711fb3aac66aafe26c4", shopA.getId());
	
   }
    
    
    @Test
    public void testfindById()
    {
    
        Shops shopA = shopsRepository.findById("5a0c6711fb3aac66aafe26c4");
        assertNotNull(shopA);
       
    }
    
   
 
    @After
    public void closeContext() throws Exception {
      context.close();
    }
 
}

		
	


package ma.hiddenfounders.lns.controllers.test;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import ma.hiddenfounders.lns.controllers.MainController;
import ma.hiddenfounders.lns.dao.mongo.classes.Location;
import ma.hiddenfounders.lns.exceptions.BusinessExceptions;
import ma.hiddenfounders.lns.services.MainService;

@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
public class ControllersTests {
	
	@Autowired
    private MockMvc mockMvc;

    @MockBean
	private MainService mainService;
    
    @Test
    public void returnIndex() throws Exception {	
		
    	this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/jsp/index.jsp"));               
    }
	
    @Test
    public void returnNearbyShopsPost() throws BusinessExceptions, Exception {
    	
    	Location mockedLocation = mock(Location.class);
    	doNothing().when(mainService).getNearbyShops(mockedLocation);
    	this.mockMvc.perform(post("/nearbyShops")).andDo(print()).andExpect(status().isOk())
    	.andExpect(forwardedUrl("/WEB-INF/jsp/nearbyShops.jsp"));
        
    }
    
    @Test
    public void returnPreferredShops() throws Exception  {
    	
    	doNothing().when(mainService).getPreferredShops();
    	this.mockMvc.perform(get("/myPreferredShops")).andDo(print()).andExpect(status().isOk())
    	.andExpect(forwardedUrl("/WEB-INF/jsp/preferredShops.jsp"));	
    }
    
    @Test
    public void returnNearbyShopsGet() throws Exception  {
    	
    	doNothing().when(mainService).getNearbyShops_();
    	this.mockMvc.perform(get("/displayShops")).andDo(print()).andExpect(status().isOk())
    	.andExpect(forwardedUrl("/WEB-INF/jsp/nearbyShops.jsp"));	
    }
    
    @Test
    public void returnErrorPage() throws BusinessExceptions, Exception   {
    	
    	String mockedId = "1234";
    	
    	doThrow(new BusinessExceptions("mock exception")).when(mainService).like(mockedId);
    	 this.mockMvc.perform(get("/like?id="+mockedId)).andDo(print()).andExpect(forwardedUrl("/WEB-INF/jsp/applicationErrors.jsp"))
    	 .andExpect(status().isOk());
    	
    }
    
    
    @Test
    public void testLike() throws BusinessExceptions, Exception   {
    	
    	String mockedId = "1234";   	
    	doNothing().when(mainService).like(mockedId);
    	 this.mockMvc.perform(get("/like?id="+mockedId)).andDo(print()).andExpect(forwardedUrl("/WEB-INF/jsp/nearbyShops.jsp"))
    	 .andExpect(status().isOk());
    	
    }
    
    @Test
    public void testDislike() throws BusinessExceptions, Exception   {
    	
    	String mockedId = "1234";	
    	doNothing().when(mainService).dislike(mockedId);
    	 this.mockMvc.perform(get("/dislike?id="+mockedId)).andDo(print()).andExpect(forwardedUrl("/WEB-INF/jsp/nearbyShops.jsp"))
    	 .andExpect(status().isOk());
    	
    }
    
    @Test
    public void testRemove() throws BusinessExceptions, Exception   {
    	
    	String mockedId = "1234";  	
    	doNothing().when(mainService).remove(mockedId);
    	 this.mockMvc.perform(get("/remove?id="+mockedId)).andDo(print()).andExpect(forwardedUrl("/WEB-INF/jsp/preferredShops.jsp"))
    	 .andExpect(status().isOk());
    	
    }
    
   
}

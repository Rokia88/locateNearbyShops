package ma.hiddenfounders.lns.dao.mongo.classes;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * POJO class mapping shops collection to Shops object 
 * @author rokia
 * @version 1.0
 */
@Document(collection = "shops")
public class Shops implements Serializable{

	/**
	 * @serial 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	private String name;
	
	private String email;
	
	private String city;
	
	@GeoSpatialIndexed(type=GeoSpatialIndexType.GEO_2DSPHERE)
	private Point location;
	
	/**
	 * Constructor
	 * @param id  unique identifier
	 * @param name shop's name
	 * @param email shop's email
	 * @param city shop's address
	 * @param location shop's location
	 */
	public Shops(String id, String name, String email, String city, Point location) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.city = city;
		this.location = location;
	}
	
	

	public Shops() {
	}


	/**
	 * getter method of id field
	 * @return returns id
	 */
	public String getId() {
		return id;
	}

	/**
	 * setter method of id field
	 * @param id identifier
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * getter method of name field
	 * @return returns name
	 */
	public String getName() {
		return name;
	}

	/**
	 * setter method of name field
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * getter method of email field
	 * @return returns email 
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * setter method of email field
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * getter method of city field
	 * @return returns city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * setter method of city field
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * getter method of location field
	 * @return returns location
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * setter method of location field
	 * @param location
	 */
	public void setLocation(Point location) {
		this.location = location;
	}

	/**
	 * displays the content of a Shops instance 
	 * @return information about a given Shops instance
	 */
	@Override
	public String toString() {
		return "Shop [id=" + id + ", name=" + name + ", email=" + email + ", city=" + city + ", longitude=" + location.getX()+ ", latitude=" + location.getY()
				+ "]";
	}
	
	
	
}

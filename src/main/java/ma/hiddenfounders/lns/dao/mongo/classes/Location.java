package ma.hiddenfounders.lns.dao.mongo.classes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.geo.Point;

/**
 * Class wrapper of {@link Point}
 * @author rokia
 * @version 1.0
 */
public class Location {

	private static final Logger logger = LogManager.getLogger(Location.class);
	
	private double x;
	
	private double y;

	/**
	 * getter of X field
	 * @return longitude 
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * setter of X field
	 * @param x
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * getter of Y field
	 * @return latitude
	 */
	public double getY() {
		return y;
	}

	/**
	 * setter of Y field
	 * @param y
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * this method truncates x and y to 5 decimal places
	 */
	public void truncate() {
		
		logger.info("truncate x and y to 5 places" );
		
		String sX = (String) String.format("%.5f", this.x);
		Double newX = Double.parseDouble(sX);
		this.setX(newX);
		String sY = (String) String.format("%.5f", this.y);
		Double newY = Double.parseDouble(sY);
		this.setY(newY);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}
	
	
	
	
	
}

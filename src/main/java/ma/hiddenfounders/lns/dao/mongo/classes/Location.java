package ma.hiddenfounders.lns.dao.mongo.classes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Location {

	private static final Logger logger = LogManager.getLogger(Location.class);
	
	private double x;
	
	private double y;

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public void truncate() {
		
		logger.info("truncate x and y to 5 places" );
		
		String sX = (String) String.format("%.5f", this.x);
		Double newX = Double.parseDouble(sX);
		this.setX(newX);
		String sY = (String) String.format("%.5f", this.y);
		Double newY = Double.parseDouble(sY);
		this.setY(newY);
	}
	
	
	
}

package ma.hiddenfounders.lns.dao.mongo;

import org.springframework.data.geo.GeoResults;
import org.springframework.stereotype.Component;

import ma.hiddenfounders.lns.dao.mongo.classes.Location;
import ma.hiddenfounders.lns.dao.mongo.classes.Shops;
import ma.hiddenfounders.lns.exceptions.ApplicationExceptions;

/**
 * Custom Repository of database queries  
 * @version 1.0
 * @author rokia
 *
 */

@Component
public interface ShopsRepositoryCustom {

	public GeoResults<Shops> getNearbyShops(Location location) throws ApplicationExceptions;
	
}

package ma.hiddenfounders.lns.dao.mongo;

import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;

import ma.hiddenfounders.lns.dao.mongo.classes.Shops;

/**
 * Custom Repository of database queries  
 * @version 1.0
 * @author rokia
 *
 */

@Component
public interface ShopsRepositoryCustom {

	public GeoResults<Shops> getNearbyShops(Point location);
	public Shops findByName(String name);
	public Shops findById(String id);
	
}

package ma.hiddenfounders.lns.dao.mongo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.data.mongodb.core.query.NearQuery;

import ma.hiddenfounders.lns.dao.mongo.classes.Shops;
import ma.hiddenfounders.lns.exceptions.ApplicationExceptions;
import ma.hiddenfounders.lns.exceptions.DAOExceptions;

/**
 * implementation of the interface {@link ShopsRepositoryCustom}
 * @author rokia
 * @version 1.0 
 */
//@Repository
public class ShopsRepositoryImpl implements ShopsRepositoryCustom {

	private static final Logger logger = LogManager.getLogger(ShopsRepositoryImpl.class);
	
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	/**
	 * Constructor 
	 * @param mongoTemplate configures database connection and helps writing database queries 
	 */
	@Autowired
	public ShopsRepositoryImpl(MongoTemplate mongoTemplate) {
		
		logger.info("constructor ShopsRepositoryImpl",this);
		this.mongoTemplate = mongoTemplate;
	}


	/**
	 * returns the closest shops to location
	 * @param location current position of the user
	 * @return returns nearby shops ordered by their average distance from location
	 * @throws DAOExceptions 
	 */
	public GeoResults<Shops> getNearbyShops(Point location) throws ApplicationExceptions{
		
		GeoResults<Shops> shops = null;
		
		try {
			GeospatialIndex geospatialindex = new GeospatialIndex("location");
			geospatialindex.typed(GeoSpatialIndexType.GEO_2DSPHERE);
			mongoTemplate.indexOps("shops").ensureIndex(geospatialindex);
			NearQuery query = NearQuery.near(location).maxDistance(new Distance(3, Metrics.KILOMETERS));
			shops= mongoTemplate.geoNear(query, Shops.class, "shops");
			logger.info("number of nearby shops:"+shops.getContent().size(), this);
		} catch (Exception e) {
			throw new DAOExceptions("something goes wrong with getNearbyShops method");
		}
		return shops;
	}
	
	

	
	

	

}

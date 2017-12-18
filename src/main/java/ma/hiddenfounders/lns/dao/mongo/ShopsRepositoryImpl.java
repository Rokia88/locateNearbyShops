package ma.hiddenfounders.lns.dao.mongo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ma.hiddenfounders.lns.dao.mongo.classes.Shops;

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
		
		logger.info("constructor",this);
		this.mongoTemplate = mongoTemplate;
	}


	/**
	 * returns the closest shops to location
	 * @param location current position of the user
	 * @return returns nearby shops ordered by their average distance from location
	 */
	public GeoResults<Shops> getNearbyShops(Point location){
		
		GeospatialIndex geospatialindex = new GeospatialIndex("location");
		geospatialindex.typed(GeoSpatialIndexType.GEO_2DSPHERE);
		mongoTemplate.indexOps("shops").ensureIndex(geospatialindex);
		NearQuery query = NearQuery.near(location).maxDistance(new Distance(3, Metrics.KILOMETERS));
		GeoResults<Shops> shops= mongoTemplate.geoNear(query, Shops.class, "shops");
		//logger.info("number of nearby shops:"+shops.getContent().size());
		logger.info("number of nearby shops:"+shops.getContent().size(), this);
		return shops;
	}
	
	/**
	 * search in the database a shop whose name is name
	 * @param name name of the required shop  
	 * @return returns one shop of the specified name
	 */
	/*public Shops findByName(String name){

		logger.info("search shops named:"+ name);
		
		Query query = new Query(Criteria.where("name").is(name));
		Shops shop = mongoTemplate.findOne(query, Shops.class);		
		return shop;
	}


	/**
	 * search in the database a shop whose id is id
	 * @param id id of of the required shop  
	 * @return returns the shop identified by id
	 */
	/*public Shops findById(String id) {
		
		logger.info("search shops with id:"+ id);
		
		Query query = new Query(Criteria.where("_id").is(new ObjectId(id)));
		Shops shop = mongoTemplate.findOne(query, Shops.class);		
		return shop;
	}*/


	
	

	

}

package ma.hiddenfounders.lns.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ma.hiddenfounders.lns.dao.mongo.classes.Shops;
import ma.hiddenfounders.lns.exceptions.ApplicationExceptions;

/**
 * Repository of auto-configured database queries
 * @author rokia
 * @version 1.0
 */
@Repository
public interface ShopsRepository extends MongoRepository<Shops, String>, ShopsRepositoryCustom{
	
	public Shops findByName(String name);
	public Shops findById(String id) throws ApplicationExceptions;

}

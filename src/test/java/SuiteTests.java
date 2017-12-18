

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import ma.hiddenfounders.lns.controllers.test.ControllersTests;
import ma.hiddenfounders.lns.dao.mongo.test.DAOTests;
import ma.hiddenfounders.lns.services.test.ServicesTests;

@RunWith(Suite.class)

@Suite.SuiteClasses({
  DAOTests.class,
  ControllersTests.class,
  ServicesTests.class
})
public class SuiteTests {
	
	

}

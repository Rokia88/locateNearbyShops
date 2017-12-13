package ma.hiddenfounders.lns.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Exceptions related to DAO layer
 * @author rokia
 * @version 1.0
 */
public class DAOExceptions extends ApplicationExceptions {
	
	/**
	 * @serial
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LogManager.getLogger(DAOExceptions.class);
	
	private static final String initMsg = "DAO Layer exceptions:";
	
	/**
	 * Constructor 
	 * @param msg describes the cause of the exception
	 */
	public DAOExceptions(String msg) {
	  logger.error(initMsg+msg, this);
	  
	}

}

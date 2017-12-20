package ma.hiddenfounders.lns.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BusinessExceptions extends ApplicationExceptions{

	/**
	 * @serial
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LogManager.getLogger(BusinessExceptions.class);
	
	private static final String initMsg = "Business Layer exceptions:";
	
	/**
	 * Constructor 
	 * @param msg describes the cause of the exception
	 */
	public BusinessExceptions(String msg) {
		
	  logger.error(initMsg+msg, this);
	  
	}
}

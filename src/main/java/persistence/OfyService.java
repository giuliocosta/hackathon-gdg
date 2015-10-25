package persistence;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import Model.Location;

public class OfyService implements ServletContextListener {

	/** Define all entities first. */
	static {
		ObjectifyService.register(Location.class);

	}
	
	  /**
	   * Method that returns the objectify service reference.
	   * 
	   * @return Objectify.
	   */
	  public static Objectify ofy() {
	    return ObjectifyService.ofy();
	    // prior to v.4.0 use .begin() ,
	    // since v.4.0 use ObjectifyService.ofy();
	  }

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}
	
	  /**
	   * Method that returns the objectify factory reference.
	   * 
	   * @return Objectify.
	   */
	  public static ObjectifyFactory factory() {
	    return ObjectifyService.factory();
	  }
}

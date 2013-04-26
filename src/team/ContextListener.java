package team;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.rosuda.JRI.Rengine;

import database.DBConnection;

/**
 * Application Lifecycle Listener implementation class ContextListener
 *
 */
@WebListener
public class ContextListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public ContextListener() {}

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
        ServletContext context = arg0.getServletContext();
        DBConnection db = new DBConnection();
        context.setAttribute("database", db);
        
        Rengine re=new Rengine (new String [] {"--vanilla"}, false, null);
        if (!re.waitForR())
        {
            System.out.println ("Cannot load R");
        }
        System.out.println("2");
        re.eval("library(quantmod)");
        re.eval("library(tseries)");
        context.setAttribute("rengine", re);
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}

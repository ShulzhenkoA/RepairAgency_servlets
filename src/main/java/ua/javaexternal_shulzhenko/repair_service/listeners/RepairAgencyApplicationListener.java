package ua.javaexternal_shulzhenko.repair_service.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.javaexternal_shulzhenko.repair_service.services.proper_request.ProperRequestService;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.connection.DBConnectionsPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.sql.SQLException;

@WebListener
public class RepairAgencyApplicationListener implements ServletContextListener {

    private final static Logger LOGGER = LogManager.getLogger(RepairAgencyApplicationListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ProperRequestService.initializeProperRequestService();
        } catch (IOException exc) {
            LOGGER.error("Can't initialize ProperRequest service: " ,exc);
        }
        LOGGER.info("Webapp 'Repair Service' was started.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            DBConnectionsPool.getConnectionsPool().close();
        } catch (SQLException exc) {
            LOGGER.error("DBConnectionPool closing error: " + exc.getMessage(), exc);
        }
        LOGGER.info("Webapp 'Repair Service' was closed");
    }
}

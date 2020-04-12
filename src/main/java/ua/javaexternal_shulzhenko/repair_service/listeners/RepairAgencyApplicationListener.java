package ua.javaexternal_shulzhenko.repair_service.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class RepairAgencyApplicationListener implements ServletContextListener {

    private final static Logger LOGGER = LogManager.getLogger(RepairAgencyApplicationListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("Webapp 'Repair Service' was initialized.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("Webapp 'Repair Service' was destroyed.");
    }
}

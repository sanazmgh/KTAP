import CLI.CLI;
import login.Login;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Main {
    static private final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        /*
        logger.info("hello");
        logger.debug("hello");
        logger.warn("hello");
        logger.fatal("hello");
        logger.error("hello");
        */
        Login.LoadData();
        logger.warn("Data Loaded");
        CLI.LoginResponder();
    }
}

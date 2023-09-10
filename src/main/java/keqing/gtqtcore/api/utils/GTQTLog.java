package keqing.gtqtcore.api.utils;

import org.apache.logging.log4j.Logger;

public class GTQTLog {

    public static Logger logger;

    public GTQTLog() {}

    public static void init(Logger modLogger) {
        logger = modLogger;
    }

}

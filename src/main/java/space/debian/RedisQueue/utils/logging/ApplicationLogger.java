package space.debian.RedisQueue.utils.logging;

import org.beryx.textio.AbstractTextTerminal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.SimpleLogger;
import org.slf4j.impl.SimpleLoggerFactory;

public class ApplicationLogger {

    private static SimpleLogger logger = (SimpleLogger) new SimpleLoggerFactory().getLogger("RedisQueue");

    public static SimpleLogger get() {
        return logger;
    }

}

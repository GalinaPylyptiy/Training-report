package stepDef;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.slf4j.LoggerFactory;

class LogbackTestUtil {

    static ListAppender<ILoggingEvent> getListAppenderForLogger(Class<?> clazz) {
        Logger logger = (Logger) LoggerFactory.getLogger(clazz);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
        return listAppender;
    }

    static void detachListAppender(Class<?> clazz, ListAppender<ILoggingEvent> listAppender) {
        Logger logger = (Logger) LoggerFactory.getLogger(clazz);
        logger.detachAppender(listAppender);
    }
}


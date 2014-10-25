package sample.sdr.auth.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

public class BookstoreLogger {
	private String logbackconfig;
	private static Logger logger = LoggerFactory.getLogger(BookstoreLogger.class);
	
	public BookstoreLogger(String logbackconfig) {
		this.logbackconfig = logbackconfig;
		init();
	}
	
	private void init() {
		JoranConfigurator configurator = new JoranConfigurator();
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        configurator.setContext(loggerContext);
        loggerContext.reset();
        try {
			configurator.doConfigure(logbackconfig);
		} catch (JoranException e) {
			// StatusPrinter will handle this
		}
        StatusPrinter.printInCaseOfErrorsOrWarnings(loggerContext);
        
        logger.info("Test Log");		
        logger.error("error msg");
	}
}

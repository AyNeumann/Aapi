package com.example.Aapi.cron;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Test scheduled task / cron. Will log current time every 10 minutes
 * @author Aymeric NEUMANN
 *
 */
@Component
public class ScheduledTasks {
	
	/** Reference to the log4j logger. */
	private static final Logger LOG = LogManager.getLogger();
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	/**
	 * Log current time every 10 minutes.
	 */
	@Scheduled(cron = "0 */10 * * * *")
	public void reportCurrentTime() {
		LOG.info("The time is now {}", dateFormat.format(new Date()));
	}
}

package com.epam.grandhackathon.deployment.sphere.plugin.utils;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateFormatUtilTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(DateFormatUtilTest.class);
	private String sampleDate = "10:20:30 December 03, 1999";
	private String wrongDate = "10 March 2015 12:00:00";
	private DateTime sampleDateTime = new DateTime (1999, 12, 3, 10, 20, 30);
	private String invalidFormatMessage = "java.lang.IllegalArgumentException: Invalid format: ";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFormatDate() throws Exception {
		LOGGER.info("Testing format date");
		assertTrue(DateFormatUtil.formatDate(sampleDateTime).equals(sampleDate));
		assertFalse(DateFormatUtil.formatDate(sampleDateTime).equals(wrongDate));
		LOGGER.info("Done.");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testToDate() throws Exception {
		LOGGER.info("Testing to date");		
		assertTrue(DateFormatUtil.toDate(sampleDate).equals(sampleDateTime));
		DateFormatUtil.toDate(wrongDate).toString();
	}

}

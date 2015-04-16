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
	private static final String SAMPLE_DATE = "10:20:30 December 03, 1999";
	private static final String WRONG_DATE = "10 March 2015 12:00:00";
	private static final DateTime SAMPLE_DATE_TIME = new DateTime (1999, 12, 3, 10, 20, 30);

	@Test
	public void testFormatDate() throws Exception {
		assertTrue(DateFormatUtil.formatDate(SAMPLE_DATE_TIME).equals(SAMPLE_DATE));
		assertFalse(DateFormatUtil.formatDate(SAMPLE_DATE_TIME).equals(WRONG_DATE));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testToDate() throws Exception {
		assertTrue(DateFormatUtil.toDate(SAMPLE_DATE).equals(SAMPLE_DATE_TIME));
		DateFormatUtil.toDate(WRONG_DATE).toString();
	}

}

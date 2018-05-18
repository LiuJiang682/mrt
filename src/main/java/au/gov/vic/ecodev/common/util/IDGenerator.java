package au.gov.vic.ecodev.common.util;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.server.UID;

import org.apache.log4j.Logger;

/**
 * Copyright 2003 Victorian State Government
 *
 * Generates a unique id using the java.rmi.server.UID class along with the
 * address of the host machine.
 *
 * @@version 1.0, Oct 6, 2003
 * @@author bm64
 *
 */
public class IDGenerator {

	private static final Logger LOGGER = Logger.getLogger(IDGenerator.class);

	/**
	 * Generates a unique id by combining a java.rmi.server.UID value and the ip
	 * address of the host machine.
	 *
	 * @@return a unique id.
	 */
	public static BigDecimal getUID() {
		UID uid = new UID();
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(Math.abs(InetAddress.getLocalHost().hashCode()));
			sb.append(Math.abs(uid.hashCode()));
		} catch (UnknownHostException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return new BigDecimal(sb.toString());
	}

}

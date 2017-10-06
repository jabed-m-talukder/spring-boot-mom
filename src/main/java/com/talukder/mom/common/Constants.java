package com.talukder.mom.common;

public class Constants {

	private static final String SENDGRID_API_KEY = System.getenv("SENDGRID_API_KEY");

	public static String getSendgridAPIKeys() {
		return SENDGRID_API_KEY;
	}

}

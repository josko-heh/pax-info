package com.josko.passenger.config;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class Definitions {

	private Definitions() {}

	public static final String PAX_ID_MDC = "passenger_id";

	public static final String ERROR_LOGGER = "errorLogger";
	public static final String DATA_LOGGER = "dataLogger";
	public static final String DEBUG_LOGGER = "debugLogger";
	public static final Marker REQ = MarkerManager.getMarker("REQ");
	public static final Marker RES = MarkerManager.getMarker("RES");      
}

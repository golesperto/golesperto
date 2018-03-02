package com.sdp.util;

public class Mapa {
	public static double distanciaEntrePontosKM(double lat1, double lng1, double lat2, double lng2) {
		return distanciaEntrePontosMts(lat1, lng1, lat2, lng2) / 1000;
	}

	public static double distanciaEntrePontosMts(double lat1, double lng1, double lat2, double lng2) {
		double earthRadius = 6371000; // meters
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = earthRadius * c;
		return dist;
	}

	public static double distanciaEntrePontos(double xA, double yA, double xB, double yB) {
		double distancia = Math.sqrt(Math.pow(xB - xA, 2) + Math.pow(yB - yA, 2));
		return distancia;
	}
}

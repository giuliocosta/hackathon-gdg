package util;

import java.awt.Point;
import java.util.List;

import Model.Location;

public class Util {

	/**
	 * Define a localização central a partir de uma lista de localizações. Tal
	 * localização é calculada a partir da média das latitudes e longitudes das
	 * localizações na lista.
	 * 
	 * @param locations
	 *            Lista de localizações.
	 * @return Localização central calculada.
	 */
	public static Location getCenterPoint(List<Location> locations) {
		double sumLatitude = 0.0;
		double sumLongitude = 0.0;
		for (Location location : locations) {
			sumLatitude += location.getLat();
			sumLongitude += location.getLng();
		}
		double meanLatitude = sumLatitude / locations.size();
		double meanLongitude = sumLongitude / locations.size();
		Location centerPoint = new Location();
		centerPoint.setLat(meanLatitude);
		centerPoint.setLng(meanLongitude);
		return centerPoint;
	}

}

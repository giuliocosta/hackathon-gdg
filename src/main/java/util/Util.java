package util;

import java.awt.Point;
import java.util.List;

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
	public static Point getCenterPoint(List<Point> locations) {
		double sumLatitude = 0.0;
		double sumLongitude = 0.0;
		for (Point location : locations) {
			sumLatitude += location.getX();
			sumLongitude += location.getY();
		}
		double meanLatitude = sumLatitude / locations.size();
		double meanLongitude = sumLongitude / locations.size();
		Point centerPoint = new Point();
		centerPoint.setLocation(meanLatitude, meanLongitude);
		return centerPoint;
	}

}

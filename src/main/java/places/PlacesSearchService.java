package places;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Model.Location;
import persistence.OfyService;

public class PlacesSearchService {
	String appkey = "AIzaSyC0nxX2sAv1EjQndaVD9gzDb3bjJshg15o";

	public void storeLocation(Double lat, Double lng) {
		Location loc = new Location();
		loc.setLat(lat);
		loc.setLng(lng);
		Objectify ofy = ObjectifyService.ofy();
		ofy.save().entity(loc).now();
	}

	public Map<String, Object> getBestPlaces(Float lat, Float lng)
			throws NotFoundException, BadRequestException, InternalServerErrorException {
		Objectify objectify = OfyService.ofy();
		List<Location> entities = objectify.load().type(Location.class).list();
		if (entities == null || entities.size() <= 0) {
			return null;
		}
		//List<Poi> allResults;
		for (Location location : entities) {
			
		}
		return null;//getBestPlacesSingleLoc(lat, lng);
	}

	private Map<String, Object> getBestPlacesSingleLoc(Double lat, Double lng)
			throws NotFoundException, BadRequestException {
		String fullRequest = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat.toString()
				+ "," + lng.toString() + "&radius=500&types=food&name=cruise&key=" + appkey;
		try {
			// InputStream resourceStream =
			// PlacesSearchService.class.getClassLoader().getResourceAsStream("people_basic_auth.txt");

			// String auth = convertStreamToString(resourceStream);

			URL url = new URL(fullRequest);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			ObjectMapper mapper = new ObjectMapper();

			if (conn.getResponseCode() == 200) {
				Map<String, Object> providerResponse = mapper.readValue(conn.getInputStream(), Map.class);
				return providerResponse;
			} else {
				throw new NotFoundException("operacao falhou");
			}

		} catch (JsonParseException e) {
			throw new BadRequestException("operacao falhou");

		} catch (MalformedURLException e) {
			throw new BadRequestException(e.getMessage());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("resource")
	private static String convertStreamToString(InputStream is) {
		Scanner scanner = new Scanner(is).useDelimiter("\\A");
		return scanner.hasNext() ? scanner.next() : "";
	}

}

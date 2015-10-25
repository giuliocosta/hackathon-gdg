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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Model.Location;
import Model.LocationResult;
import persistence.OfyService;
import util.Util;

public class PlacesSearchService {
	String appkey = "AIzaSyC0nxX2sAv1EjQndaVD9gzDb3bjJshg15o";

	public void storeLocation(Double lat, Double lng) {
		Location loc = new Location();
		loc.setLat(lat);
		loc.setLng(lng);
		Objectify ofy = ObjectifyService.ofy();
		ofy.save().entity(loc).now();
	}

	public Map<String, Object> getBestPlaces()
			throws NotFoundException, BadRequestException, InternalServerErrorException {
		Objectify objectify = OfyService.ofy();
		List<Location> entities = objectify.load().type(Location.class).list();
		if (entities == null || entities.size() <= 0) {
			return null;
		}
		
		Location centerLoc = Util.getCenterPoint(entities);
		return getBestPlacesSingleLoc(centerLoc.getLat(), centerLoc.getLng());
	}

	private Map<String, Object> getBestPlacesSingleLoc(Double lat, Double lng)
			throws NotFoundException, BadRequestException {
		String fullRequest = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat.toString()
				+ "," + lng.toString() + "&radius=500&types=food&name=cruise&key=" + appkey;
		try {
			//InputStream resourceStream =
			//PlacesSearchService.class.getClassLoader().getResourceAsStream("people_basic_auth.txt");

			//String auth = convertStreamToString(resourceStream);

			URL url = new URL(fullRequest);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			ObjectMapper mapper = new ObjectMapper();

			if (conn.getResponseCode() == 200) {
				Map<String, Object> providerResponse = mapper.readValue(conn.getInputStream(), Map.class);
				//return retrieveLocationsFromBestPlacesJSON(providerResponse);
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
	
	@SuppressWarnings("unchecked")
	public List<LocationResult> retrieveLocationsFromBestPlacesJSON(Map<String,Object> mapaJson) {
		List<Map<String,Object>> listaResults = (List<Map<String,Object>>) mapaJson.get("results");
		List<LocationResult> lista = new ArrayList<LocationResult>();
		for(Map<String,Object> result:listaResults) {
			LocationResult atual = new LocationResult();
			Map<String, Object> geometry = (Map<String, Object>) result.get("geometry");
			Map<String, Object> location = (Map<String, Object>) geometry.get("location");
			atual.setName(result.get("name").toString());
			atual.setLat(location.get("lat").toString());
			atual.setLng(location.get("lng").toString());
			lista.add(atual);
		}
		return lista;
	}

}
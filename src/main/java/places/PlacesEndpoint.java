package places;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;

import java.io.IOException;
import java.security.GeneralSecurityException;

import Model.LocationResults;
import constants.Constants;

@Api(name = "rest", version = "v1", clientIds = { Constants.WEB_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID })
public class PlacesEndpoint {
	private PlacesSearchService searchService = new PlacesSearchService();

	@ApiMethod(name = "getLocations", path = "locations", httpMethod = "get")
	public LocationResults getLocationsNearby()
			throws InternalServerErrorException, BadRequestException, NotFoundException, OAuthRequestException {
		return new LocationResults(searchService.getBestPlaces());
	}

	@ApiMethod(name = "addLocation", path = "locations", httpMethod = "post")
	public void addLocation(@Named("lat") String lat, @Named("lng") String lng)
			throws InternalServerErrorException, BadRequestException, IOException, GeneralSecurityException {
		searchService.storeLocation(Double.parseDouble(lat), Double.parseDouble(lng));
	}

}


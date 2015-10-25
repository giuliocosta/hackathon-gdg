package places;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;

import java.util.Map;

import constants.Constants;

@Api(name = "rest", version = "v1", clientIds = { Constants.WEB_CLIENT_ID,
		Constants.API_EXPLORER_CLIENT_ID })
public class PlacesEndpoint {
	private PlacesSearchService searchService = new PlacesSearchService();


	@ApiMethod(name = "getLocations", path = "locations", httpMethod = "get")
	public Map<String,Object> getLocationsNearby(@Named("lat") Float lat, @Named("lng") Float lng)
			throws InternalServerErrorException, BadRequestException, NotFoundException, OAuthRequestException {
		return searchService.getBestPlaces(lat, lng);
	}

}

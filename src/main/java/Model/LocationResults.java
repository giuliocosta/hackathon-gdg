package Model;

import java.util.List;

public class LocationResults {
	private List<LocationResult> listResults;

	public LocationResults(List<LocationResult> bestPlaces) {
		listResults = bestPlaces;
	}

	/**
	 * @return the listResults
	 */
	public List<LocationResult> getListResults() {
		return listResults;
	}

	/**
	 * @param listResults the listResults to set
	 */
	public void setListResults(List<LocationResult> listResults) {
		this.listResults = listResults;
	}
	
	
}

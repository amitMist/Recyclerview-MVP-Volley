package amit.recruitment.com.earthquake.data;

import java.util.List;

import amit.recruitment.com.earthquake.data.Earthquake;

/**
 * Created by amit on 3/23/18.
 */

public class EarthquakeResponse {

    private List<Earthquake> earthquakes;

    public EarthquakeResponse(List<Earthquake> earthquakes) {
        this.earthquakes = earthquakes;
    }

    public List<Earthquake> getEarthquakes() {
        return earthquakes;
    }

    public void setEarthquakes(List<Earthquake> earthquakes) {
        this.earthquakes = earthquakes;
    }
}

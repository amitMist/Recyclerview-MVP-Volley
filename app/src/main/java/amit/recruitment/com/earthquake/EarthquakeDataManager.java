package amit.recruitment.com.earthquake;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amit on 3/23/18.
 */

public class EarthquakeDataManager {

    private List<Earthquake> latestData;

    private static EarthquakeDataManager instance=null;

    private EarthquakeDataManager(){
        latestData = new ArrayList<Earthquake>();
    }
    public static EarthquakeDataManager getInstance(){

        synchronized (EarthquakeDataManager.class) {
            if(instance == null){
                instance = new EarthquakeDataManager();
            }
        }

        return instance;
    }

    public List<Earthquake> getLatestData() {
        return latestData;
    }

    public void setLatestData(List<Earthquake> latestData) {

        this.latestData = latestData;
    }
}

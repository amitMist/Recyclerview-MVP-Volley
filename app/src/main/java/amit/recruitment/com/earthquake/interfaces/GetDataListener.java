package amit.recruitment.com.earthquake.interfaces;

import java.util.List;

import amit.recruitment.com.earthquake.data.Earthquake;

/**
 * Created by amit on 3/23/18.
 */

public interface GetDataListener {

    void onSuccess(String message, List<Earthquake> list);

    void onFailure(String message);

}

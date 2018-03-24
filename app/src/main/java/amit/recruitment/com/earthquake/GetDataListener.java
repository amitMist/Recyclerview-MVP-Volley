package amit.recruitment.com.earthquake;

import java.util.List;

/**
 * Created by amit on 3/22/18.
 */

public interface GetDataListener {

    void onSuccess(String message, List<Earthquake> list);
    void onFailure(String message);

}

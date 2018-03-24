package amit.recruitment.com.earthquake.interfaces;

import java.util.List;

import amit.recruitment.com.earthquake.data.Earthquake;

/**
 * Created by amit on 3/23/18.
 */

public interface MainView  {
    void onGetDataSuccess(String message, List<Earthquake> list);
    void onGetDataFailure(String message);
    void showProgress();
    void hideProgress();
    void showMessage(String message);
}

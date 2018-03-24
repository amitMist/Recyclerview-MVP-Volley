package amit.recruitment.com.earthquake;

import android.content.Context;

import java.util.List;

/**
 * Created by amit on 3/22/18.
 */

public interface MainView  {
    void onGetDataSuccess(String message, List<Earthquake> list);
    void onGetDataFailure(String message);
    void showProgress();
    void hideProgress();
    void showMessage(String message);
}

package amit.recruitment.com.earthquake;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.List;

import amit.recruitment.com.earthquake.data.Earthquake;
import amit.recruitment.com.earthquake.data.EarthquakeDataManager;
import amit.recruitment.com.earthquake.interfaces.GetDataListener;
import amit.recruitment.com.earthquake.interfaces.MainInteractor;
import amit.recruitment.com.earthquake.interfaces.MainPresenter;
import amit.recruitment.com.earthquake.interfaces.MainView;

/**
 * Created by amit on 3/23/18.
 */

public class MainPresenterImpl implements MainPresenter, GetDataListener {

    private MainView mMainView;
    private MainInteractor mInteractor;


    public MainPresenterImpl(MainView mMainView) {
        this.mMainView = mMainView;
        this.mInteractor = new MainInteractorImpl(this);
    }

    @Override
    public void getDataForList(Context context, boolean isRestoring) {

        Boolean shouldLoadFromNetwork = false;
        if (isRestoring) {

            List<Earthquake> existingData = EarthquakeDataManager.getInstance().getLatestData();

            if (existingData != null && !existingData.isEmpty()) {
                // we have cached copy of data for restoring purpose
                shouldLoadFromNetwork = false;
                onSuccess("Restored Data", existingData);
            } else {
                shouldLoadFromNetwork = true;
            }
        } else {
            shouldLoadFromNetwork = true;
        }

        if (shouldLoadFromNetwork) {

            if (checkInternet(context)) {
                // get this done by the interactor
                mMainView.showProgress();
                mInteractor.initNetworkCall(context, Endpoints.EQ_URL);
            } else {

                onFailure("No internet connection.");
            }

        }
    }


    @Override
    public void onDestroy() {

        mInteractor.onDestroy();
        if (mMainView != null) {
            mMainView.hideProgress();
            mMainView = null;
        }
    }

    @Override
    public void onSuccess(String message, List<Earthquake> list) {

        // updating cach copy of data for restoring purpose
        EarthquakeDataManager.getInstance().setLatestData(list);

        if (mMainView != null) {
            mMainView.hideProgress();
            mMainView.onGetDataSuccess(message, list);
        }
    }

    @Override
    public void onFailure(String message) {
        if (mMainView != null) {
            mMainView.hideProgress();
            mMainView.onGetDataFailure(message);
        }

    }

    public Boolean checkInternet(Context context) {
        ConnectivityManager cn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cn.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected() == true) {
            return true;
        } else {
            return false;
        }
    }
}

package amit.recruitment.com.earthquake;

import android.content.Context;

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

    public MainView getMainView() {
        return mMainView;
    }

    @Override
    public void getDataForList(Context context, boolean isRestoring) {

        // get this done by the interactor
        mMainView.showProgress();
        mInteractor.provideData(context, isRestoring);

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

        // updating cache copy of data for restoring purpose
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

}
